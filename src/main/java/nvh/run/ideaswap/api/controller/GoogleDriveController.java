package nvh.run.ideaswap.api.controller;

import com.google.api.services.drive.model.File;
import nvh.run.ideaswap.service.GoogleDriveService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/drive")
public class GoogleDriveController {

    private final GoogleDriveService googleDriveService;

    public GoogleDriveController(GoogleDriveService googleDriveService) {
        this.googleDriveService = googleDriveService;
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        Path tempFile = Files.createTempFile("upload_", "_" + file.getOriginalFilename());
        try (OutputStream os = Files.newOutputStream(tempFile)) {
            os.write(file.getBytes());
        }

        // Upload file lên Google Drive
        String uploadedFileUrl = googleDriveService.uploadFile(tempFile.toString(), file.getContentType());

        // Xóa file tạm sau khi upload
        Files.deleteIfExists(tempFile);

        return uploadedFileUrl;
    }

    @DeleteMapping("/delete/{fileId}")
    public String deleteFile(@PathVariable String fileId) throws IOException {
        googleDriveService.deleteFile(fileId);
        return "Deleted file with ID: " + fileId;
    }

    @GetMapping("/search")
    public List<String> searchFiles(@RequestBody String query) throws IOException {
        return googleDriveService.searchFiles(query).stream()
                .map(file -> file.getName() + " (" + file.getId() + ")")
                .collect(Collectors.toList());
    }
    @GetMapping("/file/{fileId}")
    public String getFileInfo(@PathVariable String fileId) throws IOException {
        File file = googleDriveService.getFileInfo(fileId);
        String parentFolderId = file.getParents() != null ? file.getParents().get(0) : "Root";

        return "File Name: " + file.getName() +
                "\nFile ID: " + file.getId() +
                "\nParent Folder ID: " + parentFolderId +
                "\nWeb View Link: " + file.getWebViewLink() +
                "\nWeb Content Link: " + file.getWebContentLink();
    }

}

