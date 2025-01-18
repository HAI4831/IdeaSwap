package nvh.run.ideaswap.api.controller;

import lombok.RequiredArgsConstructor;
import nvh.run.ideaswap.api.service.intf.IConversations;
import nvh.run.ideaswap.data.dto.ConversationsDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/conversations")
@RequiredArgsConstructor
public class ConversationsController {
    private final IConversations conversationsService;

    @GetMapping
    public ResponseEntity<Object> getAllConversations() {
        return conversationsService.getAllConversations();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getConversationById(@PathVariable String id) {
        return conversationsService.getConversationById(id);
    }

    @PostMapping
    public ResponseEntity<Object> createConversation(@Valid @RequestBody ConversationsDTO conversationsDTO) {
        return conversationsService.createConversation(conversationsDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateConversation(@PathVariable String id, @Valid @RequestBody ConversationsDTO conversationsDTO) {
        return conversationsService.updateConversation(id, conversationsDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteConversation(@PathVariable String id) {
        return conversationsService.deleteConversation(id);
    }
}

