package nvh.run.ideaswap.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
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
public class CommentsService {
    CommentsRepository commentsRepository;
    public CommentsDTO getAllComments() {
        List<Comments> comments = commentsRepository.findAll();
        return CommentsDTO.builder().build();
    }
    public CommentsDTO getCommentById(String id) {
        Comments comment = commentsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment not found"));
        return CommentsDTO.builder().build();
    }
    public CommentsDTO createComment(CommentsDTO commentsDTO) {
        Comments comment = commentsRepository.save(
                Comments.builder()
                        .content(commentsDTO.getContent())
                        .parentCommentID(commentsDTO.getParentCommentID())
                        .userID(commentsDTO.getUserID())
                        .referenceID(commentsDTO.getReferenceID())
                        .build()
        );
        return CommentsDTO.builder().build();
    }
    public CommentsDTO updateComment(String id, CommentsDTO commentsDTO) {
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
        return CommentsDTO.builder().build();
    }
    public CommentsDTO deleteComment(String id) {
        getCommentById(id);
        commentsRepository.deleteById(id);
        return CommentsDTO.builder().build();
    }
}
