package nvh.run.ideaswap.api.controller;

import lombok.RequiredArgsConstructor;
import nvh.run.ideaswap.api.service.intf.IComments;
import nvh.run.ideaswap.data.dto.CommentsDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentsController {
    private final IComments commentsService;

    @GetMapping
    public ResponseEntity<Object> getAllComments() {
        return commentsService.getAllComments();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getCommentById(@PathVariable String id) {
        return commentsService.getCommentById(id);
    }

    @PostMapping
    public ResponseEntity<Object> createComment(@Valid @RequestBody CommentsDTO commentsDTO) {
        return commentsService.createComment(commentsDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateComment(@PathVariable String id, @Valid @RequestBody CommentsDTO commentsDTO) {
        return commentsService.updateComment(id, commentsDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteComment(@PathVariable String id) {
        return commentsService.deleteComment(id);
    }
}
