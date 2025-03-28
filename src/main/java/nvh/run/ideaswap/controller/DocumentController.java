package nvh.run.ideaswap.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nvh.run.ideaswap.data.dto.DocumentRequest;
import nvh.run.ideaswap.service.DocumentsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/document")
@RequiredArgsConstructor
public class DocumentController {
    private static final Logger logger = LoggerFactory.getLogger(DocumentController.class.getName());
    private final DocumentsService documentsService;
    private final MessageSource messageSource;

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
    public ResponseEntity<Object> getDocumentById(@PathVariable String id) {
        return ResponseEntity.ok(
                Map.of(
                        "success", true,
                        "message", "Retrieve document successfully",
                        "document", documentsService.getDocumentById(id)
                )
        );
    }

    @PostMapping("/add")
    public ResponseEntity<Object> createDocument(@Valid @ModelAttribute DocumentRequest documentRequest) {

        return ResponseEntity.status(201).body(
                Map.of(
                        "success", true,
                        "message", "Document created successfully",
                        "document", documentsService.createDocument(documentRequest)
                )
        );
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateDocument(@PathVariable String id, @Valid @ModelAttribute DocumentRequest documentRequest) {
        return ResponseEntity.ok(
                Map.of(
                        "success", true,
                        "message", "Document updated successfully",
                        "document", documentsService.updateDocument(id, documentRequest)
                )
        );
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteDocument(@PathVariable String id) {
        return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Document deleted successfully",
                "document",documentsService.deleteDocument(id)
        ));

    }

    @PatchMapping("/update/view/{id}")
    public ResponseEntity<Object> incrementView(@PathVariable String id) {
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
//    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class})
//    public ResponseEntity<?> handleMethodArgumentNotValid(BindException ex, Locale locale, MessageSource messageSource){
//        final ObjectError error = ex.getBindingResult().getAllErrors().get(0);
//        final String message = messageSource.getMessage(error,locale);
////        logger.log(level.SEVERE, ex.getMessage(),ex);
//        return ResponseEntity.badRequest().body(
//                ErrorResponse.builder()
//                        .success(false)
//                        .message(message)
//                        .errorClass(this.getClass().getSimpleName())
//                        .timestamp(LocalDateTime.now())
//                        .error(error.getDefaultMessage())
//                        .path("/api/v1/role/add RoleController.createRole")
//                        .stackTrace(ex.getStackTrace())
//                        .build()
//        );
//    }
}
