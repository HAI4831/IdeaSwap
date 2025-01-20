package nvh.run.ideaswap.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import nvh.run.ideaswap.data.dto.CommentsDTO;
import nvh.run.ideaswap.data.dto.DocumentsDTO;
import nvh.run.ideaswap.data.entity.Categories;
import nvh.run.ideaswap.data.entity.Documents;
import nvh.run.ideaswap.data.repository.DocumentsRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class DocumentsService {
    DocumentsRepository documentsRepository;
    CategoryService categoryService;

    public DocumentsDTO getAllDocuments() {
        List<Documents> documents = documentsRepository.findAll();
        return DocumentsDTO.builder().build();
    }

    public DocumentsDTO getDocumentById(String id) {
        Documents document = documentsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Document not found"));
        return DocumentsDTO.builder().build();
    }

    public DocumentsDTO createDocument(DocumentsDTO documentsDTO) {
        Categories category = categoryService.getCategoryById(documentsDTO.getCategoryId());
        Documents document = documentsRepository.save(
                Documents.builder()
                        .userID(documentsDTO.getUserId())
                        .title(documentsDTO.getTitle())
                        .description(documentsDTO.getDescription())
                        .fileUrl(documentsDTO.getFileUrl())
                        .imageUrl(documentsDTO.getImageUrl())
                        .countDownload(documentsDTO.getCountDownload())
                        .categoryID(category)
                        .build()
        );
        return DocumentsDTO.builder().build();
    }

    public CommentsDTO updateDocument(String id, DocumentsDTO documentsDTO) {
        getDocumentById(id);
        Categories category = iCategory.getCategoryById(documentsDTO.getCategoryId());
        Documents updatedDocument = documentsRepository.save(
                Documents.builder()
                        .id(id)
                        .userID(documentsDTO.getUserId())
                        .title(documentsDTO.getTitle())
                        .description(documentsDTO.getDescription())
                        .fileUrl(documentsDTO.getFileUrl())
                        .imageUrl(documentsDTO.getImageUrl())
                        .countDownload(documentsDTO.getCountDownload())
                        .categoryID(category)
                        .build()
        );
        return CommentsDTO.builder().build();
    }

    public DocumentsDTO deleteDocument(String id) {
        getDocumentById(id);
        documentsRepository.deleteById(id);
        return DocumentsDTO.builder().build();

    }

    public DocumentsDTO incrementDownload(String id) {
        Documents document = documentsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Document not found"));
        document.setCountDownload(document.getCountDownload() + 1);
        documentsRepository.save(document);
        return DocumentsDTO.builder().build();
    }

    public DocumentsDTO searchDocuments(String keyword) {
        List<Documents> documents = documentsRepository.findByTitleContainingIgnoreCase(keyword);
        return DocumentsDTO.builder().build();
    }
}
