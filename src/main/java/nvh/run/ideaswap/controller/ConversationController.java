package nvh.run.ideaswap.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nvh.run.ideaswap.data.dto.ConversationRequest;
import nvh.run.ideaswap.service.ConversationsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/conversation")
@RequiredArgsConstructor
public class ConversationController {
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

    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<Object> getConversationById(@PathVariable String id) {
        return ResponseEntity.ok(
                Map.of(
                        "success", true,
                        "message", "Retrieve Conversation successfully",
                        "conversation", conversationsService.getConversationById(id)
                )
        );
    }
    @GetMapping("/{userId}")
    public ResponseEntity<?> getConversationByUserId(@PathVariable String userId) {
        return ResponseEntity.ok(
                Map.of(
                        "success", true,
                        "message", "Retrieve Conversation successfully",
                        "conversation", conversationsService.getConversationByUserId(userId)
                )
        );
    }

    @PostMapping("/add")
    public ResponseEntity<Object> createConversation(@Valid @RequestBody ConversationRequest conversationRequest) {
        return ResponseEntity.status(201).body(
                Map.of(
                        "success", true,
                        "message", "Conversation created successfully",
                        "conversation", conversationsService.createConversation(conversationRequest)
                )
        );
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateConversation(@PathVariable String id, @Valid @RequestBody ConversationRequest conversationRequest) {
        return ResponseEntity.ok(
                Map.of(
                        "success", true,
                        "message", "Conversation updated successfully",
                        "conversation", conversationsService.updateConversation(id, conversationRequest)
                )
        );
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteConversation(@PathVariable String id) {
        return ResponseEntity.ok(
                Map.of(
                        "success", true,
                        "message", "Conversation deleted successfully",
                        "conversation",conversationsService.deleteConversation(id)
                )
        );
    }
}

