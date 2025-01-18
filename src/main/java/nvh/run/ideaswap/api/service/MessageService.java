package nvh.run.ideaswap.api.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import nvh.run.ideaswap.api.service.intf.IMessage;
import nvh.run.ideaswap.data.dto.MessageDTO;
import nvh.run.ideaswap.data.entity.Messages;
import nvh.run.ideaswap.data.repository.MessageRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class MessageService implements IMessage {
    MessageRepository messageRepository;

    @Override
    public ResponseEntity<Object> getAllMessages() {
        List<Messages> messages;
        try {
            messages = messageRepository.findAll();
        } catch (Exception e) {
            return ResponseEntity.status(500).body(
                    Map.of("success", false, "message", "Retrieve List Messages failed", "error", e.getMessage())
            );
        }
        return ResponseEntity.ok(
                Map.of("success", true, "message", "Retrieve List Messages successfully", "data", messages)
        );
    }

    @Override
    public ResponseEntity<Object> getMessageById(String id) {
        Messages message = messageRepository.findById(id).orElseThrow(() -> new RuntimeException("Message not found"));
        return ResponseEntity.ok(
                Map.of("success", true, "message", "Retrieve Message By ID successfully", "data", message)
        );
    }

    @Override
    public ResponseEntity<Object> createMessage(MessageDTO messageDTO) {
        Messages savedMessage = messageRepository.save(
                Messages.builder().content(messageDTO.getContent()).sender(messageDTO.getSender()).build()
        );
        return ResponseEntity.status(201).body(
                Map.of("success", true, "message", "Create Message successfully", "data", savedMessage)
        );
    }

    @Override
    public ResponseEntity<Object> updateMessage(String id, MessageDTO messageDTO) {
        getMessageById(id);
        Messages updatedMessage = messageRepository.save(
                Messages.builder().id(id).content(messageDTO.getContent()).sender(messageDTO.getSender()).build()
        );
        return ResponseEntity.ok(
                Map.of("success", true, "message", "Update Message successfully", "data", updatedMessage)
        );
    }

    @Override
    public ResponseEntity<Object> deleteMessage(String id) {
        getMessageById(id);
        messageRepository.deleteById(id);
        return ResponseEntity.ok(Map.of("success", true, "message", "Delete Message successfully"));
    }
}

