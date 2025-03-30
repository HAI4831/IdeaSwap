package nvh.run.ideaswap.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import nvh.run.ideaswap.data.dto.ConversationRequest;
import nvh.run.ideaswap.data.entity.Conversation;
import nvh.run.ideaswap.data.entity.Member;
import nvh.run.ideaswap.data.repository.ConversationRepository;
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
public class ConversationsService {
    ConversationRepository conversationRepository;
    UserService userService;

//    @Cacheable(value = "conversations",key = "'page:' + #page + ':size:' + #size")
    public Page<Conversation> getAllConversations(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Conversation> conversationsPage;
        try {
            conversationsPage = conversationRepository.findAll(pageable);
        } catch (Exception e) {
            throw new RuntimeException("Get all conversations failed",e);
        }
        return conversationsPage;
    }
//    @Cacheable(value="conversations")
    public List<Conversation> getAllConversations() {
        List<Conversation> conversations;
        try {
            conversations = conversationRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Get all conversations failed",e);
        }
        return conversations;
    }

    @Cacheable(value="conversation",key="#id",condition = "#id!=null")
    public Conversation getConversationById(String id) {
        Conversation conversation;
        try {
            conversation = conversationRepository.findById(id).orElseThrow(() -> new RuntimeException("Conversation not found"));
        } catch (Exception e) {
            throw new RuntimeException("Get conversation failed",e);
        }
        return conversation;
    }
    @Cacheable(value="conversation",key="#userId",condition = "#userId!=null")
    public List<Conversation> getConversationByUserId(String userId) {
        try {
            return conversationRepository.findByMemberList_UserId(userId);
        } catch (Exception e) {
            throw new RuntimeException("Get conversation failed",e);
        }
    }

    @Cacheable(value="conversation",key="#conversationRequest.id",condition = "#conversationRequest.id!=null")
    public Conversation createConversation(ConversationRequest conversationRequest) {
        Conversation conversation;
        List<Member> memberList = conversationRequest.getMembers();
        try {
            conversation = conversationRepository.save(
                    Conversation.builder()
                            .memberList(memberList)
                            .wallpaperUrl(conversationRequest.getWallpaperUrl())
                            .createdDate(LocalDateTime.now())
                            .updatedDate(LocalDateTime.now())
                            .build()
            );
        } catch (Exception e) {
            throw new RuntimeException("Create conversation failed",e);
        }
        return conversation;
    }

    @CachePut(value = "conversation", key = "#id", condition = "#id!=null")
    public Conversation updateConversation(String id, ConversationRequest conversationRequest) {
        // Lấy Conversation hiện tại từ DB
        Conversation existingConversation = conversationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Conversation not found with id: " + id));

        try {
            // Cập nhật danh sách thành viên nếu có dữ liệu mới
            Optional.ofNullable(conversationRequest.getMembers()).ifPresent(
                    existingConversation::setMemberList
            );

            // Cập nhật hình nền nếu có giá trị mới
            Optional.ofNullable(conversationRequest.getWallpaperUrl())
                    .ifPresent(existingConversation::setWallpaperUrl);

            // Cập nhật thời gian cập nhật
            existingConversation.setUpdatedDate(LocalDateTime.now());

            // Lưu lại Conversation đã chỉnh sửa
            return conversationRepository.save(existingConversation);
        } catch (Exception e) {
            throw new RuntimeException("Update conversation failed", e);
        }
    }


//    @Cacheable(value = "conversation", key = "#id", condition = "#id!=null")
//    public Conversation updateConversation(String id, ConversationRequest conversationRequest) {
//        // Lấy Conversation hiện tại từ DB
//        Conversation conversation = conversationRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Conversation not found with id: " + id));
//
//        // Cập nhật các giá trị mới từ conversationRequest
//        List<String> userIds = conversationRequest.getMembers().stream()
//                .map(MemberRequest::userId)
//                .toList();
//
//        conversation.setMemberIDs(userIds);
//        conversation.setWallpaperUrl(conversationRequest.getWallpaperUrl());
//        conversation.setUpdatedDate(LocalDateTime.now()); // Chỉ cập nhật updatedDate
//
//        try {
//            // Lưu lại Conversation đã chỉnh sửa
//            return conversationRepository.save(conversation);
//        } catch (Exception e) {
//            throw new RuntimeException("Update conversation failed", e);
//        }
//    }

    @CacheEvict(value="conversation",key="#id",condition = "#id!=null")
    public Conversation deleteConversation(String id) {
        Conversation conversation = getConversationById(id);
        try {
            conversationRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Delete conversation failed",e);
        }
        return conversation;
    }
}
