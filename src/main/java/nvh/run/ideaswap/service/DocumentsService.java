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
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

//    @Cacheable(value = "documents",key = "'page:' + #page + ':size:' + #size")
    public Page<Documents> getAllDocuments(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Documents> documentsPage ;
        try {
            documentsPage = documentsRepository.findAll(pageable);
        } catch (Exception e) {
            throw new RuntimeException("Get all documents failed",e);
        }
        return documentsPage;
    }

//    @Cacheable(value="documents")
    public List<Documents> getAllDocuments() {
        List<Documents> documents ;
        try {
            documents = documentsRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Get all documents failed",e);
        }
        return documents;
    }

    @Cacheable(value="document",key="#id",condition = "#id!=null")
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

    @CachePut(value="document",key="documentRequest.id",condition = "#documentRequest.id!=null")
    public Documents createDocument(DocumentRequest documentRequest) {
        Users user=userService.getUserById(documentRequest.getUserID());
        Categories category = categoryService.getCategoryById(documentRequest.getCategoryID());
        Documents document;
        try {
            String imageUrl = cloudinaryService.uploadImage(documentRequest.getImageUrl(),null);
//            if(imageUrl == null) {
//                throw new RuntimeException("Document image upload failed");
//            }
            document = documentsRepository.save(
                    Documents.builder()
                            .id(documentRequest.getId())
                            .userID(user.getId())
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
                        .userIDs(List.of(document.getUserID()))
                        .build()
        );
        return document;
    }

    @Cacheable(value="document",key="#id",condition = "#id!=null")
    public Documents updateDocument(String id, DocumentRequest documentRequest) {
        getDocumentById(id);
        Users user=userService.getUserById(documentRequest.getUserID());
        Categories category = categoryService.getCategoryById(documentRequest.getCategoryID());
        Documents document;
        try {
            String imageUrl = cloudinaryService.uploadImage(documentRequest.getImageUrl(),null);
//            if(imageUrl == null) {
//                throw new RuntimeException("Document image upload failed");
//            }
            document = documentsRepository.save(
                    Documents.builder()
                            .id(documentRequest.getId())
                            .userID(user.getId())
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

    @CacheEvict(value="document",key="#id",condition = "#id!=null")
    public Documents deleteDocument(String id) {
        Documents document = getDocumentById(id);
        try {
            documentsRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Delete document failed",e);
        }
        return document;
    }

    @Cacheable(value="document",key="#id",condition = "#id!=null")
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

    @Cacheable(value="document",key="#keyword",condition = "#keyword!=null")
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
