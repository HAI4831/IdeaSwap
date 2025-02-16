//package nvh.run.ideaswap.service;
//
//import com.google.api.client.auth.oauth2.Credential;
//import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
//import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
//import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
//import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
//import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
//import com.google.api.client.http.InputStreamContent;
//import com.google.api.client.json.JsonFactory;
//import com.google.api.client.json.gson.GsonFactory;
//import com.google.api.client.util.store.FileDataStoreFactory;
//import com.google.api.services.drive.Drive;
//import com.google.api.services.drive.DriveScopes;
//import com.google.api.services.drive.model.File;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.security.GeneralSecurityException;
//import java.util.Collections;
//import java.util.List;
//
//@Service
//public class GoogleDriveService2 {
//
//    @Value("${google.cloud.drive.client-id}")
//    private String clientId;
//
//    @Value("${google.cloud.drive.client-secret}")
//    private String clientSecret;
//
//    private static final String APPLICATION_NAME = "Your Application Name";
//    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
//    private static final List<String> SCOPES = Collections.singletonList(DriveScopes.DRIVE_FILE);
//    private static final String TOKENS_DIRECTORY_PATH = "tokens";
//
//    private Credential getCredentials() throws IOException, GeneralSecurityException {
//        // Tạo thông tin client secrets từ clientId và clientSecret
//        GoogleClientSecrets clientSecrets = new GoogleClientSecrets()
//                .setInstalled(new GoogleClientSecrets.Details()
//                        .setClientId(clientId)
//                        .setClientSecret(clientSecret));
//
//        // Xây dựng luồng ủy quyền và kích hoạt yêu cầu ủy quyền người dùng
//        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
//                GoogleNetHttpTransport.newTrustedTransport(), JSON_FACTORY, clientSecrets, SCOPES)
//                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
//                .setAccessType("offline")
//                .build();
//
//        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
//        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
//    }
//
//    public Drive getDriveService() throws IOException, GeneralSecurityException {
//        return new Drive.Builder(GoogleNetHttpTransport.newTrustedTransport(), JSON_FACTORY, getCredentials())
//                .setApplicationName(APPLICATION_NAME)
//                .build();
//    }
//
//    public String uploadFile(MultipartFile file) throws IOException, GeneralSecurityException {
//        File fileMetadata = new File();
//        fileMetadata.setName(file.getOriginalFilename());
//
//        InputStreamContent mediaContent = new InputStreamContent(file.getContentType(), file.getInputStream());
//
//        File uploadedFile = getDriveService().files().create(fileMetadata, mediaContent)
//                .setFields("id, webViewLink")
//                .execute();
//
//        return uploadedFile.getWebViewLink();
//    }
//}
//
////import com.google.api.client.auth.oauth2.Credential;
////import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
////import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
////import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
////import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
////import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
////import com.google.api.client.http.InputStreamContent;
////import com.google.api.client.json.JsonFactory;
////import com.google.api.client.json.jackson2.JacksonFactory;
////import com.google.api.client.util.Value;
////import com.google.api.client.util.store.FileDataStoreFactory;
////import com.google.api.services.drive.Drive;
////import com.google.api.services.drive.DriveScopes;
////import com.google.api.services.drive.model.File;
////import org.springframework.stereotype.Service;
////import org.springframework.web.multipart.MultipartFile;
////
////import java.io.IOException;
////import java.io.InputStream;
////import java.io.InputStreamReader;
////import java.security.GeneralSecurityException;
////import java.util.Collections;
////import java.util.List;
////
////@Service
////public class GoogleDriveService {
////    @Value("spring.google-cloud.driver.client-id")
////    private String clientId;
////    @Value("spring.google-cloud.driver.client-secret")
////    private String clientSecret;
////    private static final String APPLICATION_NAME = "Your Application Name";
////    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
////    private static final List<String> SCOPES = Collections.singletonList(DriveScopes.DRIVE_FILE);
////    private static final String CREDENTIALS_FILE_PATH = "/path/to/credentials.json";
////
////    private Credential getCredentials() throws IOException, GeneralSecurityException {
////        InputStream in = GoogleDriveService.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
////        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
////
////        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
////                GoogleNetHttpTransport.newTrustedTransport(), JSON_FACTORY, clientSecrets, SCOPES)
////                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File("tokens")))
////                .setAccessType("offline")
////                .build();
////
////        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
////        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
////    }
////
////    public Drive getDriveService() throws IOException, GeneralSecurityException {
////        return new Drive.Builder(GoogleNetHttpTransport.newTrustedTransport(), JSON_FACTORY, getCredentials())
////                .setApplicationName(APPLICATION_NAME)
////                .build();
////    }
////
////    public String uploadFile(MultipartFile file) throws IOException, GeneralSecurityException {
////        File fileMetadata = new File();
////        fileMetadata.setName(file.getOriginalFilename());
////
////        InputStreamContent mediaContent = new InputStreamContent(file.getContentType(), file.getInputStream());
////
////        File uploadedFile = getDriveService().files().create(fileMetadata, mediaContent)
////                .setFields("id, webViewLink")
////                .execute();
////
////        return uploadedFile.getWebViewLink();
////    }
////}
