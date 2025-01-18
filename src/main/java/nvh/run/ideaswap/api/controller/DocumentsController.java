package nvh.run.ideaswap.api.controller;

import lombok.RequiredArgsConstructor;
import nvh.run.ideaswap.api.service.intf.IDocuments;
import nvh.run.ideaswap.data.dto.DocumentsDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/document")
@RequiredArgsConstructor
public class DocumentsController {
    private final IDocuments documentsService;

    @GetMapping
    public ResponseEntity<Object> getAllDocuments() {
        return documentsService.getAllDocuments();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getDocumentById(@PathVariable String id) {
        return documentsService.getDocumentById(id);
    }

    @PostMapping
    public ResponseEntity<Object> createDocument(@Valid @RequestBody DocumentsDTO documentsDTO) {
        return documentsService.createDocument(documentsDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateDocument(@PathVariable String id, @Valid @RequestBody DocumentsDTO documentsDTO) {
        return documentsService.updateDocument(id, documentsDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteDocument(@PathVariable String id) {
        return documentsService.deleteDocument(id);
    }

    @PatchMapping("/update/view/{id}")
    public ResponseEntity<Object> incrementView(@PathVariable String id) {
        return documentsService.incrementDownload(id);
    }

    @GetMapping("/search")
    public ResponseEntity<Object> searchDocuments(@RequestParam String keyword) {
        return documentsService.searchDocuments(keyword);
    }
}
