package nvh.run.ideaswap.api.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import nvh.run.ideaswap.data.dto.VideoRequest;
import nvh.run.ideaswap.service.VideoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/video")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class VideoController {
    VideoService videoService;
    @GetMapping
    public ResponseEntity<Object> findAll() {
        return ResponseEntity.status(200).body(
                Map.of(
                        "success", true,
                        "message", "Retrieve List Users successfully",
                        "videos", videoService.getAll())
        );
    }
    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable String id) {
        return ResponseEntity.status(200).body(
                Map.of(
                        "success", true,
                        "message", "Retrieve User by Id successfully",
                        "video", videoService.getById(id))
        );
    }
    @PostMapping("/add")
    public ResponseEntity<Object> add(@RequestBody VideoRequest videoRequest) {
        return ResponseEntity.status(200).body(
                Map.of(
                        "success", true,
                        "message", "Add Video successfully",
                        "video", videoService.save(videoRequest))
        );
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<Object> update(@PathVariable String id, @RequestBody VideoRequest videoRequest){
        return ResponseEntity.status(200).body(
                Map.of(
                        "success", true,
                        "message", "Update Video successfully",
                        "video", videoService.update(id,videoRequest))
        );
    }
    @PutMapping("/update/view/{id}")
    public ResponseEntity<Object> updateView(@PathVariable String id, @RequestBody VideoRequest videoRequest){
        return ResponseEntity.status(200).body(
                Map.of(
                        "success", true,
                        "message", "Update Video successfully",
                        "video", videoService.updateView(id,videoRequest))
        );
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable String id) {
        return ResponseEntity.status(200).body(
                Map.of(
                        "success", true,
                        "message", "Delete Video successfully",
                        "video", videoService.delete(id))
        );
    }
}
