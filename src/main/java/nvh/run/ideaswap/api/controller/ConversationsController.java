package nvh.run.ideaswap.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nvh.run.ideaswap.data.entity.Conversations;
import nvh.run.ideaswap.service.ConversationsService;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/conversation")
@RequiredArgsConstructor
public class ConversationsController {
    private final ConversationsService conversationsService;

    @GetMapping
    public ResponseEntity<Object> getAllConversations() {
        return ResponseEntity.ok(
                Map.of(
                        "success", true,
                        "message", "Retrieve Conversations successfully",
                        "conversations", conversationsService.getAllConversations()
                )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getConversationById(@PathVariable ObjectId id) {
        return ResponseEntity.ok(
                Map.of(
                        "success", true,
                        "message", "Retrieve Conversation successfully",
                        "conversation", conversationsService.getConversationById(id)
                )
        );
    }

    @PostMapping
    public ResponseEntity<Object> createConversation(@Valid @RequestBody Conversations conversation) {
        return ResponseEntity.status(201).body(
                Map.of(
                        "success", true,
                        "message", "Conversation created successfully",
                        "conversation", conversationsService.createConversation(conversation)
                )
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateConversation(@PathVariable ObjectId id, @Valid @RequestBody Conversations conversation) {
        return ResponseEntity.ok(
                Map.of(
                        "success", true,
                        "message", "Conversation updated successfully",
                        "conversation", conversationsService.updateConversation(id, conversation)
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteConversation(@PathVariable ObjectId id) {
        return ResponseEntity.ok(
                Map.of(
                        "success", true,
                        "message", "Conversation deleted successfully",
                        "conversation",conversationsService.deleteConversation(id)
                )
        );
    }
}

