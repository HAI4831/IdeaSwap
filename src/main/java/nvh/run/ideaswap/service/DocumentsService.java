package nvh.run.ideaswap.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import nvh.run.ideaswap.data.dto.DocumentRequest;
import nvh.run.ideaswap.data.dto.NotificationRequest;
import nvh.run.ideaswap.data.entity.Categories;
import nvh.run.ideaswap.data.entity.Documents;
import nvh.run.ideaswap.data.entity.Users;
import nvh.run.ideaswap.data.repository.DocumentsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class DocumentsService {
    DocumentsRepository documentsRepository;
    CategoryService categoryService;
    UserService userService;
    private final CloudinaryService cloudinaryService;
    private final NotificationService notificationService;

    public List<Documents> getAllDocuments() {
        List<Documents> documents ;
        try {
            documents = documentsRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Get all documents failed",e);
        }
        return documents;
    }

    public Documents getDocumentById(String id) {
        Documents document ;
        try {
            document = documentsRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Document not found"));
        } catch (Exception e) {
            throw new RuntimeException("Get document failed",e);
        }
        return document;
    }

    public Documents createDocument(DocumentRequest documentRequest) {
        Users user=userService.getUserById(documentRequest.getUserID());
        Categories category = categoryService.getCategoryById(documentRequest.getCategoryID());
        Documents document;
        try {
            String imageUrl = cloudinaryService.uploadImage(documentRequest.getImageUrl());
            if(imageUrl == null) {
                throw new RuntimeException("Document image upload failed");
            }
            document = documentsRepository.save(
                    Documents.builder()
                            .id(documentRequest.getId())
                            .userID(user)
                            .categoryID(category)
                            .title(documentRequest.getTitle())
                            .description(documentRequest.getDescription())
                            .fileUrl(documentRequest.getFileUrl())
                            .countDownload(documentRequest.getCountDownload())
                            .imageUrl(imageUrl)
                            .status(documentRequest.getStatus())
                            .score(documentRequest.getScore())
                            .createdAt(LocalDateTime.now())
                            .updatedAt(LocalDateTime.now())
                            .build()
            );
        } catch (Exception e) {
            throw new RuntimeException("Create document failed",e);
        }
        notificationService.createNotification(
                NotificationRequest.builder()
                        .description("Document is awaiting approval")
                        .imageUrl(document.getImageUrl())
                        .userIDs(List.of(document.getUserID().getId()))
                        .build()
        );
        return document;
    }

    public Documents updateDocument(String id, DocumentRequest documentRequest) {
        getDocumentById(id);
        Users user=userService.getUserById(documentRequest.getUserID());
        Categories category = categoryService.getCategoryById(documentRequest.getCategoryID());
        Documents document;
        try {
            String imageUrl = cloudinaryService.uploadImage(documentRequest.getImageUrl());
            if(imageUrl == null) {
                throw new RuntimeException("Document image upload failed");
            }
            document = documentsRepository.save(
                    Documents.builder()
                            .id(documentRequest.getId())
                            .userID(user)
                            .categoryID(category)
                            .title(documentRequest.getTitle())
                            .description(documentRequest.getDescription())
                            .fileUrl(documentRequest.getFileUrl())
                            .countDownload(documentRequest.getCountDownload())
                            .imageUrl(imageUrl)
                            .status(documentRequest.getStatus())
                            .score(documentRequest.getScore())
                            .createdAt(LocalDateTime.now())
                            .updatedAt(LocalDateTime.now())
                            .build()
            );
        } catch (Exception e) {
            throw new RuntimeException("Update document failed",e);
        }
        return document;
    }

    public Documents deleteDocument(String id) {
        Documents document = getDocumentById(id);
        try {
            documentsRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Delete document failed",e);
        }
        return document;
    }

    public Documents incrementDownload(String id) {
        Documents document = getDocumentById(id);
        document.setCountDownload(document.getCountDownload() + 1);
        try {
            documentsRepository.save(document);
        } catch (Exception e) {
            throw new RuntimeException("Increment download failed",e);
        }
        return document;
    }

    public List<Documents> searchDocuments(String keyword) {
        List<Documents> documents ;
        try {
            documents = documentsRepository.findByTitleContainingIgnoreCase(keyword);
        } catch (Exception e) {
            throw new RuntimeException("Search documents failed",e);
        }
        return documents;
    }
}
