package nvh.run.ideaswap.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import nvh.run.ideaswap.data.dto.MessageRequest;
import nvh.run.ideaswap.data.entity.Conversations;
import nvh.run.ideaswap.data.entity.Messages;
import nvh.run.ideaswap.data.entity.Users;
import nvh.run.ideaswap.data.repository.MessageRepository;
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

    public List<Messages> getAllMessages() {
        List<Messages> messages;
        try {
            messages = messageRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Get all messages failed",e);
        }
        return messages;
    }

    public Messages getMessageById(String id) {
        Messages message ;
        try {
            message = messageRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Message not found"));
        } catch (Exception e) {
            throw new RuntimeException("Get message failed",e);
        }
        return message;
    }

    public Messages createMessage(MessageRequest messageRequest) {
        Conversations conversation = conversationsService.getConversationById(messageRequest.getConversationID());
        Users user = userService.getUserById(messageRequest.getSenderID());
        Messages message = Messages.builder()
                .id(messageRequest.getId())
                .senderID(user)
                .conversationID(conversation)
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

    public Messages updateMessage(String id, MessageRequest messageRequest) {
        getMessageById(id);
        Conversations conversation = conversationsService.getConversationById(messageRequest.getConversationID());
        Users user = userService.getUserById(messageRequest.getSenderID());
        Messages message = Messages.builder()
                .id(messageRequest.getId())
                .senderID(user)
                .conversationID(conversation)
                .content(messageRequest.getContent())
                .messageParentID(messageRequest.getMessageParentID())
                .fileUrl(messageRequest.getFileUrl())
                .type(messageRequest.getType())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        message.setId(id);
        Messages updatedMessage;
        try {
            updatedMessage = messageRepository.save(message);
        } catch (Exception e) {
            throw new RuntimeException("Update message failed",e);
        }

        return updatedMessage;
    }

    public Messages deleteMessage(String id) {
        Messages message = getMessageById(id);
        try {
            messageRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Delete message failed",e);
        }
        return message;
    }

    public List<Messages> getMessageByconversationId(String conversationId) {
        List<Messages> messageList ;
        try {
            messageList = messageRepository.findByConversationID(conversationId);
        } catch (Exception e) {
            throw new RuntimeException("Get message failed",e);
        }
        return messageList;
    }
}

