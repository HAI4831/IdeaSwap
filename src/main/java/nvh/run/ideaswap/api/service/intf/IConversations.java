package nvh.run.ideaswap.api.service.intf;

import nvh.run.ideaswap.data.dto.ConversationsDTO;
import org.springframework.http.ResponseEntity;

public interface IConversations {
    ResponseEntity<Object> getAllConversations();

    ResponseEntity<Object> getConversationById(String id);

    ResponseEntity<Object> createConversation(ConversationsDTO conversationsDTO);

    ResponseEntity<Object> updateConversation(String id, ConversationsDTO conversationsDTO);

    ResponseEntity<Object> deleteConversation(String id);
}
