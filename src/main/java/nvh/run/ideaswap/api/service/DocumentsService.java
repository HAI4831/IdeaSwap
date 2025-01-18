package nvh.run.ideaswap.api.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import nvh.run.ideaswap.api.service.intf.ICategory;
import nvh.run.ideaswap.api.service.intf.IDocuments;
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
public class DocumentsService implements IDocuments {
    DocumentsRepository documentsRepository;
    ICategory iCategory;

    @Override
    public ResponseEntity<Object> getAllDocuments() {
        List<Documents> documents = documentsRepository.findAll();
        return ResponseEntity.ok(
                Map.of("success", true, "message", "Retrieve documents successfully", "documents", documents)
        );
    }

    @Override
    public ResponseEntity<Object> getDocumentById(String id) {
        Documents document = documentsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Document not found"));
        return ResponseEntity.ok(
                Map.of("success", true, "message", "Retrieve document successfully", "document", document)
        );
    }

    @Override
    public ResponseEntity<Object> createDocument(DocumentsDTO documentsDTO) {
        Categories category = iCategory.getCategoryById(documentsDTO.getCategoryId());
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
        return ResponseEntity.status(201).body(
                Map.of("success", true, "message", "Document created successfully", "document", document)
        );
    }

    @Override
    public ResponseEntity<Object> updateDocument(String id, DocumentsDTO documentsDTO) {
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
        return ResponseEntity.ok(
                Map.of("success", true, "message", "Document updated successfully", "document", updatedDocument)
        );
    }

    @Override
    public ResponseEntity<Object> deleteDocument(String id) {
        getDocumentById(id);
        documentsRepository.deleteById(id);
        return ResponseEntity.ok(Map.of("success", true, "message", "Document deleted successfully"));
    }

    @Override
    public ResponseEntity<Object> incrementDownload(String id) {
        Documents document = documentsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Document not found"));
        document.setCountDownload(document.getCountDownload() + 1);
        documentsRepository.save(document);
        return ResponseEntity.ok(
                Map.of("success", true, "message", "Download count updated successfully", "document", document)
        );
    }

    @Override
    public ResponseEntity<Object> searchDocuments(String keyword) {
        List<Documents> documents = documentsRepository.findByTitleContainingIgnoreCase(keyword);
        return ResponseEntity.ok(
                Map.of("success", true, "message", "Search results retrieved successfully", "documents", documents)
        );
    }
}
