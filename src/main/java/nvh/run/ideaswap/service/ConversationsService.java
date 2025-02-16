package nvh.run.ideaswap.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import nvh.run.ideaswap.data.dto.ConversationRequest;
import nvh.run.ideaswap.data.entity.Conversations;
import nvh.run.ideaswap.data.entity.Users;
import nvh.run.ideaswap.data.repository.ConversationsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ConversationsService {
    ConversationsRepository conversationsRepository;
    UserService userService;

    public List<Conversations> getAllConversations() {
        List<Conversations> conversations;
        try {
            conversations = conversationsRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Get all conversations failed",e);
        }
        return conversations;
    }

    public Conversations getConversationById(String id) {
        Conversations conversation;
        try {
            conversation = conversationsRepository.findById(id).orElseThrow(() -> new RuntimeException("Conversation not found"));
        } catch (Exception e) {
            throw new RuntimeException("Get conversation failed",e);
        }
        return conversation;
    }

    public Conversations createConversation(ConversationRequest conversationRequest) {
        Conversations conversation;
        List<Users> userList = conversationRequest.getMembers().stream().map(userService::getUserById).toList();
        try {
            conversation = conversationsRepository.save(
                    Conversations.builder()
                            .id(conversationRequest.getId())
                            .members(userList)
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

    public Conversations updateConversation(String id, ConversationRequest conversationRequest) {
        getConversationById(id);
        Conversations conversation;
        List<Users> userList = conversationRequest.getMembers().stream().map(userService::getUserById).toList();
        try {
            conversation = conversationsRepository.save(
                    Conversations.builder()
                            .id(conversationRequest.getId())
                            .members(userList)
                            .wallpaperUrl(conversationRequest.getWallpaperUrl())
                            .createdDate(LocalDateTime.now())
                            .updatedDate(LocalDateTime.now())
                            .build()
            );
        } catch (Exception e) {
            throw new RuntimeException("Update conversation failed",e);
        }
        return conversation;
    }

    public Conversations deleteConversation(String id) {
        Conversations conversation = getConversationById(id);
        try {
            conversationsRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Delete conversation failed",e);
        }
        return conversation;
    }

    public Conversations getConversationByUserId(String userId) {
        Users user = userService.getUserById(userId);
        Conversations conversation;
        try {
            conversation = conversationsRepository.findByMembersIn(List.of(user)).orElseThrow(() -> new RuntimeException("Conversation not found"));
        } catch (Exception e) {
            throw new RuntimeException("Get conversation failed",e);
        }
        return conversation;
    }
}
