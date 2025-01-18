package nvh.run.ideaswap.api.service.intf;

import nvh.run.ideaswap.data.dto.MessageDTO;
import org.springframework.http.ResponseEntity;

public interface IMessage {
    ResponseEntity<Object> getAllMessages();
    ResponseEntity<Object> getMessageById(String id);
    ResponseEntity<Object> createMessage(MessageDTO messageDTO);
    ResponseEntity<Object> updateMessage(String id, MessageDTO messageDTO);
    ResponseEntity<Object> deleteMessage(String id);
}

