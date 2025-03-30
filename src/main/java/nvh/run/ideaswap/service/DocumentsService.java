package nvh.run.ideaswap.service;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import nvh.run.ideaswap.data.dto.DocumentRequest;
import nvh.run.ideaswap.data.dto.NotificationRequest;
import nvh.run.ideaswap.data.entity.Censorship;
import nvh.run.ideaswap.data.entity.Document;
import nvh.run.ideaswap.data.entity.Status;
import nvh.run.ideaswap.data.entity.User;
import nvh.run.ideaswap.data.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DocumentsService {
    DocumentRepository documentRepository;
    CategoryService categoryService;
    UserService userService;
    CloudinaryService cloudinaryService;
    NotificationService notificationService;
    CensorshipsService censorshipsService;
    GoogleDriveService googleDriveService;
    @Autowired
    public DocumentsService(
            DocumentRepository documentRepository,
            CategoryService categoryService,
            UserService userService,
            CloudinaryService cloudinaryService,
            NotificationService notificationService,
            GoogleDriveService googleDriveService,
            @Lazy CensorshipsService censorshipsService) { // Lazy loading
        this.documentRepository = documentRepository;
        this.categoryService = categoryService;
        this.userService = userService;
        this.cloudinaryService = cloudinaryService;
        this.notificationService = notificationService;
        this.googleDriveService = googleDriveService;
        this.censorshipsService = censorshipsService;
    }

//    @Cacheable(value = "documents",key = "'page:' + #page + ':size:' + #size")
    public Page<Document> getAllDocuments(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Document> documentsPage ;
        try {
            documentsPage = documentRepository.findAll(pageable);
        } catch (Exception e) {
            throw new RuntimeException("Get all documents failed",e);
        }
        return documentsPage;
    }

//    @Cacheable(value="documents")
    public List<Document> getAllDocuments() {
        List<Document> documents ;
        try {
            documents = documentRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Get all documents failed",e);
        }
        return documents;
    }

    @Cacheable(value="document",key="#id",condition = "#id!=null")
    public Document getDocumentById(String id) {
        Document document ;
        try {
            document = documentRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Document not found"));
        } catch (Exception e) {
            throw new RuntimeException("Get document failed",e);
        }
        return document;
    }

    @CachePut(value="document",key="documentRequest.id",condition = "#documentRequest.id!=null")
    public Document createDocument(DocumentRequest documentRequest) {
        User user=userService.getUserById(documentRequest.getUserID());
//        Category category = categoryService.getCategoryById(documentRequest.getCategoryID());
        Document document;
        try {
            // Upload lên Google Drive
            String fileId = googleDriveService.uploadFile(documentRequest.getFile());

            // Lấy URL tải file trực tiếp
            String fileUrl =googleDriveService.getFileUrlByFileId(fileId);


            String imageUrl = cloudinaryService.uploadImage(documentRequest.getImageBase64(),null,"document");

            document = documentRepository.save(
                    Document.builder()
                            .userID(documentRequest.getUserID())
//                    .categoryID(documentRequest.getCategoryID())
                            .title(documentRequest.getTitle())
                            .description(documentRequest.getDescription())
                            .imageUrl(imageUrl)
                            .fileUrl(fileUrl)
                            .build()
            );
        } catch (Exception e) {
            throw new RuntimeException("Create document failed",e);
        }
        notificationService.createNotification(
                NotificationRequest.builder()
                        .description("Document is awaiting approval")
                        .imageUrl(document.getImageUrl())
                        .userIDs(List.of(user.getId()))
                        .build()
        );
        try {
            censorshipsService.createCensorship(Censorship.builder()
                            .status(Status.pending)
                            .contentID(document.getId())
                            .feedback("Document is awaiting approval")
                    .build());
        } catch (Exception e) {
            throw new RuntimeException("Create document failed",e);
        }
        return document;
    }

    @Cacheable(value = "document", key = "#id", condition = "#id != null")
    public Document updateDocument(String id, DocumentRequest documentRequest) {
        Document document = documentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Document not found"));

        //        Category category = categoryService.getCategoryById(documentRequest.getCategoryID());
        try {
            // Cập nhật thông tin tài liệu nếu có
            Optional.ofNullable(documentRequest.getUserID()).ifPresent(document::setUserID);
            Optional.ofNullable(documentRequest.getTitle()).ifPresent(document::setTitle);
            Optional.ofNullable(documentRequest.getDescription()).ifPresent(document::setDescription);

            // Xử lý file nếu có
            Optional.ofNullable(documentRequest.getFile())
                    .filter(file -> !file.isEmpty())
                    .ifPresent(file -> {
                        try {
                            String fileId = googleDriveService.uploadFile(documentRequest.getFile());
                            // Cập nhật file URL
                            document.setFileUrl(googleDriveService.getFileUrlByFileId(fileId));
                        } catch (IOException e) {
                            throw new RuntimeException("File upload failed", e);
                        }
                    });

            // Xử lý image nếu có
            Optional.ofNullable(documentRequest.getImageBase64())
                    .filter(imageBase64 -> !imageBase64.isEmpty())
                    .ifPresent(imageBase64 -> {
                        String imageUrl = cloudinaryService.uploadImage(imageBase64, null, "document");
                        document.setImageUrl(imageUrl);
                    });

            return documentRepository.save(document);
        } catch (Exception e) {
            throw new RuntimeException("Update document failed", e);
        }
    }


    @CacheEvict(value="document",key="#id",condition = "#id!=null")
    public Document deleteDocument(String id) {
        Document document = getDocumentById(id);
        try {
            // Xóa ảnh từ Cloudinary (nếu có)
            Optional.ofNullable(document.getImageUrl())
                    .ifPresent(imageUrl -> cloudinaryService.deleteImage(imageUrl, null));

            // Xóa file từ Google Drive (nếu có)
            Optional.ofNullable(document.getFileUrl())
                    .ifPresent(fileUrl -> {
                        if (fileUrl  != null) {
                            try {
                                googleDriveService.deleteFile(fileUrl,null);
                            } catch (IOException e) {
//                                throw new RuntimeException(e);
                            }
                        }
                    });

            documentRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Delete document failed",e);
        }
        return document;
    }

    @Cacheable(value="document",key="#id",condition = "#id!=null")
    public Document incrementDownload(String id) {
        Document document = getDocumentById(id);
        document.setCountDownload(document.getCountDownload() + 1);
        try {
            documentRepository.save(document);
        } catch (Exception e) {
            throw new RuntimeException("Increment download failed",e);
        }
        return document;
    }

    @Cacheable(value="document",key="#keyword",condition = "#keyword!=null")
    public List<Document> searchDocuments(String keyword) {
        List<Document> documents ;
        try {
            documents = documentRepository.findByTitleContainingIgnoreCase(keyword);
        } catch (Exception e) {
            throw new RuntimeException("Search documents failed",e);
        }
        return documents;
    }
}
