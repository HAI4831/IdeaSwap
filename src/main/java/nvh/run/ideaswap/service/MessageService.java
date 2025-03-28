package nvh.run.ideaswap.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import nvh.run.ideaswap.data.dto.MessageRequest;
import nvh.run.ideaswap.data.entity.Conversation;
import nvh.run.ideaswap.data.entity.Message;
import nvh.run.ideaswap.data.entity.User;
import nvh.run.ideaswap.data.repository.MessageRepository;
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

@Service
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class MessageService {
    MessageRepository messageRepository;
    ConversationsService conversationsService;
    UserService userService;

//    @Cacheable(value = "messages",key = "'page:' + #page + ':size:' + #size")
    public Page<Message> getAllMessages(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Message> messages;
        try {
            messages = messageRepository.findAll(pageable);
        } catch (Exception e) {
            throw new RuntimeException("Get all messages failed",e);
        }
        return messages;
    }
//    @Cacheable(value="messages")
    public List<Message> getAllMessages() {
        List<Message> messages;
        try {
            messages = messageRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Get all messages failed",e);
        }
        return messages;
    }

    @Cacheable(value="message",key="#id",condition = "#id!=null")
    public Message getMessageById(String id) {
        Message message ;
        try {
            message = messageRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Message not found"));
        } catch (Exception e) {
            throw new RuntimeException("Get message failed",e);
        }
        return message;
    }

    @CachePut(value="message",key="#messageRequest.id",condition = "#messageRequest.id!=null")
    public Message createMessage(MessageRequest messageRequest) {
        Conversation conversation = conversationsService.getConversationById(messageRequest.getConversationID());
        User user = userService.getUserById(messageRequest.getSenderID());
        Message message = Message.builder()
                .id(messageRequest.getId())
                .senderID(user.getId())
                .conversationID(conversation.getId())
                .content(messageRequest.getContent())
                .messageParentID(messageRequest.getMessageParentID())
                .fileUrl(messageRequest.getFileUrl())
                .type(messageRequest.getType())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
       try {
            message = messageRepository.save(message);
       } catch (Exception e) {
           throw new RuntimeException("Create message failed",e);
       }
        return message;
    }

    @CachePut(value="message",key="#id",condition = "#id!=null")
    public Message updateMessage(String id, MessageRequest messageRequest) {
        getMessageById(id);
        Conversation conversation = conversationsService.getConversationById(messageRequest.getConversationID());
        User user = userService.getUserById(messageRequest.getSenderID());
        Message message = Message.builder()
                .id(messageRequest.getId())
                .senderID(user.getId())
                .conversationID(conversation.getId())
                .content(messageRequest.getContent())
                .messageParentID(messageRequest.getMessageParentID())
                .fileUrl(messageRequest.getFileUrl())
                .type(messageRequest.getType())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        message.setId(id);
        Message updatedMessage;
        try {
            updatedMessage = messageRepository.save(message);
        } catch (Exception e) {
            throw new RuntimeException("Update message failed",e);
        }

        return updatedMessage;
    }

    @CacheEvict(value="message",key="#id",condition = "#id!=null")
    public Message deleteMessage(String id) {
        Message message = getMessageById(id);
        try {
            messageRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Delete message failed",e);
        }
        return message;
    }

    @Cacheable(value="message",key="#conversationId",condition = "#conversationId!=null")
    public List<Message> getMessageByconversationId(String conversationId) {
        List<Message> messageList ;
        try {
            messageList = messageRepository.findByConversationID(conversationId);
        } catch (Exception e) {
            throw new RuntimeException("Get message failed",e);
        }
        return messageList;
    }
}

