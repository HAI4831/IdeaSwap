package nvh.run.ideaswap.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import nvh.run.ideaswap.data.dto.ConversationRequest;
import nvh.run.ideaswap.data.entity.Conversation;
import nvh.run.ideaswap.data.entity.User;
import nvh.run.ideaswap.data.repository.ConversationRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

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
        User user = userService.getUserById(userId);
        List<Conversation> conversationList;
        try {
            conversationList = conversationRepository.findByMemberIDsIn(List.of(user.getId())).orElseThrow(() -> new RuntimeException("Conversation not found"));
        } catch (Exception e) {
            throw new RuntimeException("Get conversation failed",e);
        }
        return conversationList;
    }

    @Cacheable(value="conversation",key="#conversationRequest.id",condition = "#conversationRequest.id!=null")
    public Conversation createConversation(ConversationRequest conversationRequest) {
        Conversation conversation;
        List<User> userList = conversationRequest.getMembers().stream().map(userService::getUserById).toList();
        try {
            conversation = conversationRepository.save(
                    Conversation.builder()
                            .id(conversationRequest.getId())
                            .memberIDs(userList.stream().map(User::getId).toList())
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

    @Cacheable(value="conversation",key="#id",condition = "#id!=null")
    public Conversation updateConversation(String id, ConversationRequest conversationRequest) {
        getConversationById(id);
        Conversation conversation;
        List<User> userList = conversationRequest.getMembers().stream().map(userService::getUserById).toList();
        try {
            conversation = conversationRepository.save(
                    Conversation.builder()
                            .id(conversationRequest.getId())
                            .memberIDs(userList.stream().map(User::getId).toList())
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
