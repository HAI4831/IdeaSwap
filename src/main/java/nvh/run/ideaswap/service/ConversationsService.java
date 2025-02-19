package nvh.run.ideaswap.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import nvh.run.ideaswap.data.dto.ConversationRequest;
import nvh.run.ideaswap.data.entity.Conversations;
import nvh.run.ideaswap.data.entity.Users;
import nvh.run.ideaswap.data.repository.ConversationsRepository;
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
    ConversationsRepository conversationsRepository;
    UserService userService;

//    @Cacheable(value = "conversations",key = "'page:' + #page + ':size:' + #size")
    public Page<Conversations> getAllConversations(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Conversations> conversationsPage;
        try {
            conversationsPage = conversationsRepository.findAll(pageable);
        } catch (Exception e) {
            throw new RuntimeException("Get all conversations failed",e);
        }
        return conversationsPage;
    }
//    @Cacheable(value="conversations")
    public List<Conversations> getAllConversations() {
        List<Conversations> conversations;
        try {
            conversations = conversationsRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Get all conversations failed",e);
        }
        return conversations;
    }

    @Cacheable(value="conversation",key="#id",condition = "#id!=null")
    public Conversations getConversationById(String id) {
        Conversations conversation;
        try {
            conversation = conversationsRepository.findById(id).orElseThrow(() -> new RuntimeException("Conversation not found"));
        } catch (Exception e) {
            throw new RuntimeException("Get conversation failed",e);
        }
        return conversation;
    }
    @Cacheable(value="conversation",key="#userId",condition = "#userId!=null")
    public List<Conversations> getConversationByUserId(String userId) {
        Users user = userService.getUserById(userId);
        List<Conversations> conversationsList;
        try {
            conversationsList = conversationsRepository.findByMemberIDsIn(List.of(user.getId())).orElseThrow(() -> new RuntimeException("Conversation not found"));
        } catch (Exception e) {
            throw new RuntimeException("Get conversation failed",e);
        }
        return conversationsList;
    }

    @Cacheable(value="conversation",key="#conversationRequest.id",condition = "#conversationRequest.id!=null")
    public Conversations createConversation(ConversationRequest conversationRequest) {
        Conversations conversation;
        List<Users> userList = conversationRequest.getMembers().stream().map(userService::getUserById).toList();
        try {
            conversation = conversationsRepository.save(
                    Conversations.builder()
                            .id(conversationRequest.getId())
                            .memberIDs(userList.stream().map(Users::getId).toList())
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
    public Conversations updateConversation(String id, ConversationRequest conversationRequest) {
        getConversationById(id);
        Conversations conversation;
        List<Users> userList = conversationRequest.getMembers().stream().map(userService::getUserById).toList();
        try {
            conversation = conversationsRepository.save(
                    Conversations.builder()
                            .id(conversationRequest.getId())
                            .memberIDs(userList.stream().map(Users::getId).toList())
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
    public Conversations deleteConversation(String id) {
        Conversations conversation = getConversationById(id);
        try {
            conversationsRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Delete conversation failed",e);
        }
        return conversation;
    }
}
