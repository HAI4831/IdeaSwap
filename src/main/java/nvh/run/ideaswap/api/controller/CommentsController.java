package nvh.run.ideaswap.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nvh.run.ideaswap.data.dto.CommentsDTO;
import nvh.run.ideaswap.service.CommentsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/comment")
@RequiredArgsConstructor
public class CommentsController {
    private final CommentsService commentsService;

    @GetMapping
    public ResponseEntity<Object> getAllComments() {
        return ResponseEntity.ok(
                Map.of(
                        "success", true,
                        "message", "Retrieve Comments successfully",
                        "comments", commentsService.getAllComments()
                )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getCommentById(@PathVariable String id) {
        return ResponseEntity.ok(
                Map.of(
                        "success", true,
                        "message", "Retrieve Comment successfully",
                        "comment", commentsService.getCommentById(id)
                )
        );
    }

    @PostMapping
    public ResponseEntity<Object> createComment(@Valid @RequestBody CommentsDTO commentsDTO) {
        return ResponseEntity.status(201).body(
                Map.of(
                        "success", true,
                        "message", "Comment created successfully",
                        "comment", commentsService.createComment(commentsDTO)
                )
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateComment(@PathVariable String id, @Valid @RequestBody CommentsDTO commentsDTO) {
        return ResponseEntity.ok(
                Map.of(
                        "success", true,
                        "message", "Comment updated successfully",
                        "comment", commentsService.updateComment(id, commentsDTO)
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteComment(@PathVariable String id) {
        return ResponseEntity.ok(
                Map.of(
                        "success", true,
                        "message", "Comment deleted successfully",
                        "comment",commentsService.deleteComment(id)
                )
        );
    }
}
