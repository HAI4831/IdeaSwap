package nvh.run.ideaswap.api.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import nvh.run.ideaswap.data.dto.ShareRequest;
import nvh.run.ideaswap.service.ShareService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/share")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ShareController {
    ShareService shareService;

    @GetMapping
    public ResponseEntity<Object> findAll() {
        return ResponseEntity.status(200).body(
                Map.of(
                        "success", true,
                        "message", "Retrieve List Shares successfully",
                        "shares", shareService.getAll())
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable String id) {
        return ResponseEntity.status(200).body(
                Map.of(
                        "success", true,
                        "message", "Retrieve Share by Id successfully",
                        "share", shareService.getById(id))
        );
    }

    @PostMapping("/add")
    public ResponseEntity<Object> add(@RequestBody ShareRequest shareRequest) {
        return ResponseEntity.status(200).body(
                Map.of(
                        "success", true,
                        "message", "Add Share successfully",
                        "share", shareService.save(shareRequest))
        );
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable String id) {
        return ResponseEntity.status(200).body(
                Map.of(
                        "success", true,
                        "message", "Delete Share successfully",
                        "share", shareService.delete(id))
        );
    }
}

