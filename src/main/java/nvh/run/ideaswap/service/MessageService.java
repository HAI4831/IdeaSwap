package nvh.run.ideaswap.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import nvh.run.ideaswap.data.entity.Messages;
import nvh.run.ideaswap.data.repository.MessageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class MessageService {
    MessageRepository messageRepository;

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

    public Messages createMessage(Messages message) {
       try {
            message = messageRepository.save(message);
       } catch (Exception e) {
           throw new RuntimeException("Create message failed",e);
       }
        return message;
    }

    public Messages updateMessage(String id, Messages message) {
        getMessageById(id);
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
}

