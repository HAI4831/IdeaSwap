package nvh.run.ideaswap.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import nvh.run.ideaswap.data.dto.CommentRequest;
import nvh.run.ideaswap.data.entity.Comment;
import nvh.run.ideaswap.data.entity.User;
import nvh.run.ideaswap.data.repository.CommentRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class CommentsService {
    CommentRepository commentRepository;
    UserService userService;

//    @Cacheable(value = "comments",key = "'page:' + #page + ':size:' + #size")
    public Page<Comment> getAllComments(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Comment> commentsPage;
        try {
            commentsPage = commentRepository.findAll(pageable);
        } catch (Exception e) {
            throw new RuntimeException("Get all comments failed",e);
        }
        return commentsPage;
    }
//    @Cacheable(value = "comments")
    public List<Comment> getAllComments() {
        List<Comment> comments;
        try {
            comments = commentRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Get all comments failed",e);
        }
        return comments;
    }
    @Cacheable(value="comment", key="#id",condition = "#id!=null")
    public Comment getCommentById(String id) {
        Comment comment;
        try {
            comment = commentRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Comment not found"));
        } catch (Exception e) {
            throw new RuntimeException("Get comment failed",e);
        }
        return comment;
    }
    @Cacheable(value="comment", key="#commentRequest.id",condition = "#commentRequest.id!=null")
    public Comment createComment(CommentRequest commentRequest) {
        User user = userService.getUserById(commentRequest.getUserID()) ;
        Comment comment;
        try {
            comment = commentRepository.save(
                    Comment.builder()
                            .id(commentRequest.getId())
                            .userID(user.getId())
                            .content(commentRequest.getContent())
                            .parentCommentID(commentRequest.getParentCommentID())
                            .referenceID(commentRequest.getReferenceID())
                            .createdDate(LocalDateTime.now())
                            .updatedDate(LocalDateTime.now())
                            .build()
            );
        } catch (Exception e) {
            throw new RuntimeException("Create comment failed",e);
        }
        return comment;
    }
    @CachePut(value = "comment", key = "#id", condition = "#id!=null")
    public Comment updateComment(String id, CommentRequest commentRequest) {
        // Lấy comment hiện tại từ database
        Comment existingComment = getCommentById(id);
        if (existingComment == null) {
            throw new RuntimeException("Comment not found");
        }

        try {
            // Lấy user nếu có userID mới, giữ nguyên nếu không có
            User user = Optional.ofNullable(commentRequest.getUserID())
                    .map(userService::getUserById)
                    .orElse(null);

            // Cập nhật các trường có giá trị mới, giữ nguyên nếu null
            existingComment.setUserID(Optional.ofNullable(user).map(User::getId).orElse(existingComment.getUserID()));
            existingComment.setContent(Optional.ofNullable(commentRequest.getContent()).orElse(existingComment.getContent()));
            existingComment.setParentCommentID(Optional.ofNullable(commentRequest.getParentCommentID()).orElse(existingComment.getParentCommentID()));
            existingComment.setReferenceID(Optional.ofNullable(commentRequest.getReferenceID()).orElse(existingComment.getReferenceID()));
            existingComment.setUpdatedDate(LocalDateTime.now()); // Cập nhật thời gian cập nhật

            // Lưu lại comment đã cập nhật
            return commentRepository.save(existingComment);
        } catch (Exception e) {
            throw new RuntimeException("Update comment failed", e);
        }
    }

    @CacheEvict(value="comment",key="#id",condition = "#id!=null")
    public Comment deleteComment(String id) {
        Comment comment = getCommentById(id);
        try {
            commentRepository.deleteById(id);
        }catch (Exception e) {
            throw new RuntimeException("Delete comment failed",e);
        }
        return comment;
    }
}
