package nvh.run.ideaswap.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nvh.run.ideaswap.data.entity.Documents;
import nvh.run.ideaswap.service.DocumentsService;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/document")
@RequiredArgsConstructor
public class DocumentsController {
    private final DocumentsService documentsService;

    @GetMapping
    public ResponseEntity<Object> getAllDocuments() {
        return ResponseEntity.ok(
                Map.of(
                        "success", true,
                        "message", "Retrieve documents successfully",
                        "documents", documentsService.getAllDocuments()
                )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getDocumentById(@PathVariable ObjectId id) {
        return ResponseEntity.ok(
                Map.of(
                        "success", true,
                        "message", "Retrieve document successfully",
                        "document", documentsService.getDocumentById(id)
                )
        );
    }

    @PostMapping
    public ResponseEntity<Object> createDocument(@Valid @RequestBody Documents document) {
        return ResponseEntity.status(201).body(
                Map.of(
                        "success", true,
                        "message", "Document created successfully",
                        "document", documentsService.createDocument(document)
                )
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateDocument(@PathVariable ObjectId id, @Valid @RequestBody Documents document) {
        return ResponseEntity.ok(
                Map.of(
                        "success", true,
                        "message", "Document updated successfully",
                        "document", documentsService.updateDocument(id, document)
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteDocument(@PathVariable ObjectId id) {
        return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Document deleted successfully",
                "document",documentsService.deleteDocument(id)
        ));

    }

    @PatchMapping("/update/view/{id}")
    public ResponseEntity<Object> incrementView(@PathVariable ObjectId id) {
        return ResponseEntity.ok(
                Map.of(
                        "success", true,
                        "message", "Download count updated successfully",
                        "document", documentsService.incrementDownload(id)
                )
        );
    }

    @GetMapping("/search")
    public ResponseEntity<Object> searchDocuments(@RequestParam String keyword) {
        return ResponseEntity.ok(
                Map.of(
                        "success", true,
                        "message", "Search results retrieved successfully",
                        "documents", documentsService.searchDocuments(keyword)
                )
        );
    }
}
