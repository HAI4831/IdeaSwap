package nvh.run.ideaswap.api.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import nvh.run.ideaswap.api.service.intf.IComments;
import nvh.run.ideaswap.data.dto.CommentsDTO;
import nvh.run.ideaswap.data.entity.Comments;
import nvh.run.ideaswap.data.repository.CommentsRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class CommentsService implements IComments {
    CommentsRepository commentsRepository;

    @Override
    public ResponseEntity<Object> getAllComments() {
        List<Comments> comments = commentsRepository.findAll();
        return ResponseEntity.ok(
                Map.of(
                        "success", true,
                        "message", "Retrieve Comments successfully",
                        "comments", comments
                )
        );
    }

    @Override
    public ResponseEntity<Object> getCommentById(String id) {
        Comments comment = commentsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment not found"));
        return ResponseEntity.ok(
                Map.of(
                        "success", true,
                        "message", "Retrieve Comment successfully",
                        "comment", comment
                )
        );
    }

    @Override
    public ResponseEntity<Object> createComment(CommentsDTO commentsDTO) {
        Comments comment = commentsRepository.save(
                Comments.builder()
                        .content(commentsDTO.getContent())
                        .parentCommentID(commentsDTO.getParentCommentID())
                        .userID(commentsDTO.getUserID())
                        .referenceID(commentsDTO.getReferenceID())
                        .build()
        );
        return ResponseEntity.status(201).body(
                Map.of(
                        "success", true,
                        "message", "Comment created successfully",
                        "comment", comment
                )
        );
    }

    @Override
    public ResponseEntity<Object> updateComment(String id, CommentsDTO commentsDTO) {
        getCommentById(id);
        Comments updatedComment = commentsRepository.save(
                Comments.builder()
                        .id(id)
                        .content(commentsDTO.getContent())
                        .parentCommentID(commentsDTO.getParentCommentID())
                        .userID(commentsDTO.getUserID())
                        .referenceID(commentsDTO.getReferenceID())
                        .build()
        );
        return ResponseEntity.ok(
                Map.of(
                        "success", true,
                        "message", "Comment updated successfully",
                        "comment", updatedComment
                )
        );
    }

    @Override
    public ResponseEntity<Object> deleteComment(String id) {
        getCommentById(id);
        commentsRepository.deleteById(id);
        return ResponseEntity.ok(
                Map.of(
                        "success", true,
                        "message", "Comment deleted successfully"
                )
        );
    }
}
