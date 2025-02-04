package nvh.run.ideaswap.api.controller;

import nvh.run.ideaswap.service.CloudinaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/cloudinary")
public class CloudinaryController {

    @Autowired
    private CloudinaryService cloudinaryService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
            String imageUrl = cloudinaryService.uploadImage(file);
            return ResponseEntity.ok(imageUrl);
    }

    @DeleteMapping("/delete/{publicId}")
    public ResponseEntity<String> deleteImage(@PathVariable String publicId) {
            String result = cloudinaryService.deleteImage(null,publicId);
            return ResponseEntity.ok(result);
    }
}
