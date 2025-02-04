package nvh.run.ideaswap.api.controller;

import lombok.RequiredArgsConstructor;
import nvh.run.ideaswap.data.dto.MessageRequest;
import nvh.run.ideaswap.service.MessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @GetMapping
    public ResponseEntity<Object> getAllMessages() {
        return ResponseEntity.ok(
                Map.of(
                        "success", true,
                        "message", "Retrieve List Messages successfully",
                        "messages", messageService.getAllMessages()
                )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getMessageById(@PathVariable String id) {
        return ResponseEntity.ok(
                Map.of(
                        "success", true,
                        "message", "Retrieve Message By ID successfully"
                        , "Message", messageService.getMessageById(id))
        );
    }

    @PostMapping("/add")
    public ResponseEntity<Object> createMessage(@RequestBody MessageRequest messageRequest) {
        return ResponseEntity.ok(
                Map.of(
                        "success", true,
                        "message", "Create Message successfully"
                        , "Message", messageService.createMessage(messageRequest)
                )
        );
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateMessage(@PathVariable String id, @RequestBody MessageRequest messageRequest) {
        return ResponseEntity.ok(
                Map.of(
                        "success", true,
                        "message", "Update Message By ID successfully"
                        , "Message", messageService.updateMessage(id, messageRequest)
                )
        );
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteMessage(@PathVariable String id) {
        return ResponseEntity.ok(
                Map.of(
                        "success", true,
                        "message", "Delete Message By ID successfully",
                        "Message", messageService.deleteMessage(id)
            )
        );
    }
}

