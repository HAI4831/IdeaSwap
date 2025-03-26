package nvh.run.ideaswap.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nvh.run.ideaswap.data.dto.CommentRequest;
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

    @PostMapping("/add")
    public ResponseEntity<Object> createComment(@Valid @RequestBody CommentRequest commentRequest) {
        return ResponseEntity.status(201).body(
                Map.of(
                        "success", true,
                        "message", "Comment created successfully",
                        "comment", commentsService.createComment(commentRequest)
                )
        );
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateComment(@PathVariable String id, @Valid @RequestBody CommentRequest commentRequest) {
        return ResponseEntity.ok(
                Map.of(
                        "success", true,
                        "message", "Comment updated successfully",
                        "comment", commentsService.updateComment(id, commentRequest)
                )
        );
    }

    @DeleteMapping("/delete/{id}")
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
