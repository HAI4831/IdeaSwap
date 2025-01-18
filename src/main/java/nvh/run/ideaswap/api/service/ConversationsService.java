package nvh.run.ideaswap.api.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import nvh.run.ideaswap.api.service.intf.IConversations;
import nvh.run.ideaswap.data.dto.ConversationsDTO;
import nvh.run.ideaswap.data.entity.Conversations;
import nvh.run.ideaswap.data.repository.ConversationsRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ConversationsService implements IConversations {
    ConversationsRepository conversationsRepository;

    @Override
    public ResponseEntity<Object> getAllConversations() {
        List<Conversations> conversations = conversationsRepository.findAll();
        return ResponseEntity.ok(
                Map.of(
                        "success", true,
                        "message", "Retrieve Conversations successfully",
                        "conversations", conversations
                )
        );
    }

    @Override
    public ResponseEntity<Object> getConversationById(String id) {
        Conversations conversation = conversationsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Conversation not found"));
        return ResponseEntity.ok(
                Map.of(
                        "success", true,
                        "message", "Retrieve Conversation successfully",
                        "conversation", conversation
                )
        );
    }

    @Override
    public ResponseEntity<Object> createConversation(ConversationsDTO conversationsDTO) {
        Conversations conversation = conversationsRepository.save(
                Conversations.builder()
                        .members(conversationsDTO.getMembers())
                        .wallpaperUrl(conversationsDTO.getWallpaperUrl())
                        .build()
        );
        return ResponseEntity.status(201).body(
                Map.of(
                        "success", true,
                        "message", "Conversation created successfully",
                        "conversation", conversation
                )
        );
    }

    @Override
    public ResponseEntity<Object> updateConversation(String id, ConversationsDTO conversationsDTO) {
        getConversationById(id);
        Conversations updatedConversation = conversationsRepository.save(
                Conversations.builder()
                        .id(id)
                        .members(conversationsDTO.getMembers())
                        .wallpaperUrl(conversationsDTO.getWallpaperUrl())
                        .build()
        );
        return ResponseEntity.ok(
                Map.of(
                        "success", true,
                        "message", "Conversation updated successfully",
                        "conversation", updatedConversation
                )
        );
    }

    @Override
    public ResponseEntity<Object> deleteConversation(String id) {
        getConversationById(id);
        conversationsRepository.deleteById(id);
        return ResponseEntity.ok(
                Map.of(
                        "success", true,
                        "message", "Conversation deleted successfully"
                )
        );
    }
}
