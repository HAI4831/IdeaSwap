package nvh.run.ideaswap.service;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//https://www.youtube.com/watch?v=rANfiSmyMTQ
@Service
public class GoogleDriveService {

    private static final String APPLICATION_NAME = "Google Drive API Java";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
//    @Value("${google.drive.service-account-key:ideaswap-70d2c30d8571.json}")
    private static final String SERVICE_ACCOUNT_KEY_PATH = "ideaswap-70d2c30d8571.json"; // Chỉ cần tên file, Spring sẽ tìm trong resources
//    @Value("${google.drive.folder-id:1zHatmu2Zyt00G9wjNJ3I_uh5MkSvDHSp}")
    private static final String FOLDER_ID = "1zHatmu2Zyt00G9wjNJ3I_uh5MkSvDHSp"; // ID thư mục đích

    private final Drive driveService;

    public GoogleDriveService() throws GeneralSecurityException, IOException {
        this.driveService = getDriveService();
    }

    private Drive getDriveService() throws GeneralSecurityException, IOException {
        NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();

        // Load từ classpath
        try (InputStream serviceAccountStream = new ClassPathResource(SERVICE_ACCOUNT_KEY_PATH).getInputStream()) {
            GoogleCredentials credentials = ServiceAccountCredentials
                    .fromStream(serviceAccountStream)
                    .createScoped(Collections.singleton(DriveScopes.DRIVE));

            return new Drive.Builder(httpTransport, JSON_FACTORY, new HttpCredentialsAdapter(credentials))
                    .setApplicationName(APPLICATION_NAME)
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("getDrive credential document to drive failed",e);
        }
    }

    /**
     * Upload file to Google Drive
     */
    public String uploadFile(String filePath, String mimeType) throws IOException {
//        id folder google cloud muốn lưu vào đây có thể là folder khác tài khoản từ đó lưu doc của tài khoản service account drive vào tài khoản cá nhân
        File fileMetadata = new File();
        String fileName = new java.io.File(filePath).getName();
        fileMetadata.setName(fileName);
        fileMetadata.setParents(Collections.singletonList(FOLDER_ID));

        try (InputStream fileInputStream = new FileInputStream(filePath)) {
            com.google.api.client.http.AbstractInputStreamContent content =
                    new com.google.api.client.http.InputStreamContent(mimeType, fileInputStream);

            File file = driveService.files().create(fileMetadata, content)
                    .setFields("id")
                    .execute();

            return file.getId();
        } catch (Exception e) {
            throw new RuntimeException("upload file to drive failed folder_id:"+FOLDER_ID+";fileName:"+fileName+";tmpFileLocal:"+filePath,e);
        }
    }

    /**
     * Delete file from Google Drive
     */
    public void deleteFile(String fileUrl , String fileId) throws IOException {
        try {
            if(fileId==null) fileId=extractFileIdFromUrl(fileUrl);
            driveService.files().delete(fileId).execute();
        } catch (Exception e){
            throw new RuntimeException("delete file_id("+fileId+")"+" failed",e);
        }
    }

    /**
     * Search for files in Google Drive
     */
    public List<File> searchFiles(String query) throws IOException {
        FileList result;
        try {
            result = driveService.files().list()
                    .setQ(query)
                    .setFields("files(id, name, mimeType)")
                    .execute();
        } catch (Exception e) {
            throw new RuntimeException("search files failed with query("+query+")",e);
        }

        return result.getFiles();
    }
    public File getFileInfo(String fileId) throws IOException {
        try {
            return driveService.files().get(fileId)
                    .setFields("id, name, parents, mimeType, webViewLink, webContentLink")
                    .execute();
        } catch (Exception e){
            throw new RuntimeException("getFileInfo failed with fileId("+fileId+")",e);
        }
    }
    private String extractFileIdFromUrl(String fileUrl) {
        if (fileUrl == null || fileUrl.isEmpty()) {
            return null;
        }
        Pattern pattern = Pattern.compile("/d/([a-zA-Z0-9_-]+)|id=([a-zA-Z0-9_-]+)");
        Matcher matcher = pattern.matcher(fileUrl);
        if (matcher.find()) {
            return matcher.group(1) != null ? matcher.group(1) : matcher.group(2);
        }
        return null;
    }

}
