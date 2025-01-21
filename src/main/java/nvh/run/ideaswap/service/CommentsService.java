package nvh.run.ideaswap.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import nvh.run.ideaswap.data.entity.Comments;
import nvh.run.ideaswap.data.repository.CommentsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class CommentsService {
    CommentsRepository commentsRepository;
    public List<Comments> getAllComments() {
        List<Comments> comments;
        try {
            comments = commentsRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Get all comments failed",e);
        }
        return comments;
    }
    public Comments getCommentById(String id) {
        Comments comment;
        try {
            comment = commentsRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Comment not found"));
        } catch (Exception e) {
            throw new RuntimeException("Get comment failed",e);
        }
        return comment;
    }
    public Comments createComment(Comments comment) {
        try {
            comment = commentsRepository.save(comment);
        } catch (Exception e) {
            throw new RuntimeException("Create comment failed",e);
        }
        return comment;
    }
    public Comments updateComment(String id, Comments comment) {
        getCommentById(id);
        comment.setId(id);
        Comments updatedComment;
        try {
            updatedComment = commentsRepository.save(comment);
        } catch (Exception e) {
            throw new RuntimeException("Update comment failed",e);
        }
        return updatedComment;
    }
    public Comments deleteComment(String id) {
        Comments comment = getCommentById(id);
        try {
            commentsRepository.deleteById(id);
        }catch (Exception e) {
            throw new RuntimeException("Delete comment failed",e);
        }
        return comment;
    }
}
