package nvh.run.ideaswap.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import nvh.run.ideaswap.data.entity.Documents;
import nvh.run.ideaswap.data.repository.DocumentsRepository;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class DocumentsService {
    DocumentsRepository documentsRepository;
    CategoryService categoryService;

    public List<Documents> getAllDocuments() {
        List<Documents> documents ;
        try {
            documents = documentsRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Get all documents failed",e);
        }
        return documents;
    }

    public Documents getDocumentById(ObjectId id) {
        Documents document ;
        try {
            document = documentsRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Document not found"));
        } catch (Exception e) {
            throw new RuntimeException("Get document failed",e);
        }
        return document;
    }

    public Documents createDocument(Documents document) {
        try {
            document = documentsRepository.save(document);
        } catch (Exception e) {
            throw new RuntimeException("Create document failed",e);
        }
        return document;
    }

    public Documents updateDocument(ObjectId id, Documents document) {
        getDocumentById(id);
        Documents updatedDocument ;
        try {
            updatedDocument = documentsRepository.save(document);
        } catch (Exception e) {
            throw new RuntimeException("Update document failed",e);
        }
        return updatedDocument;
    }

    public Documents deleteDocument(ObjectId id) {
        Documents document = getDocumentById(id);
        try {
            documentsRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Delete document failed",e);
        }
        return document;
    }

    public Documents incrementDownload(ObjectId id) {
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
