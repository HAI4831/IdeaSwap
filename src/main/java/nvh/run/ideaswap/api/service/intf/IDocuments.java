package nvh.run.ideaswap.api.service.intf;

import nvh.run.ideaswap.data.dto.DocumentsDTO;
import org.springframework.http.ResponseEntity;

public interface IDocuments {
    ResponseEntity<Object> getAllDocuments();
    ResponseEntity<Object> getDocumentById(String id);
    ResponseEntity<Object> createDocument(DocumentsDTO documentsDTO);
    ResponseEntity<Object> updateDocument(String id, DocumentsDTO documentsDTO);
    ResponseEntity<Object> deleteDocument(String id);
    ResponseEntity<Object> incrementDownload(String id);
    ResponseEntity<Object> searchDocuments(String keyword);
}
