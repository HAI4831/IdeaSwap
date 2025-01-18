package nvh.run.ideaswap.api.service.intf;

import nvh.run.ideaswap.data.dto.CommentsDTO;
import org.springframework.http.ResponseEntity;

public interface IComments {
    ResponseEntity<Object> getAllComments();

    ResponseEntity<Object> getCommentById(String id);

    ResponseEntity<Object> createComment(CommentsDTO commentsDTO);

    ResponseEntity<Object> updateComment(String id, CommentsDTO commentsDTO);

    ResponseEntity<Object> deleteComment(String id);
}
