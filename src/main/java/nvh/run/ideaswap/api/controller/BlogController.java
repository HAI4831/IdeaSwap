package nvh.run.ideaswap.api.controller;

import nvh.run.ideaswap.data.dto.BlogRequest;
import nvh.run.ideaswap.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/blog")
public class BlogController {
    @Autowired
    private BlogService blogService;

    @GetMapping
    public ResponseEntity<Object> getAllBlogs() {
        return ResponseEntity.status(200).body(
                Map.of(
                        "success", true,
                        "message", "Retrieve List Blogs successfully",
                        "blogs", blogService.getAllBlogs()
                )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getBlogById(@PathVariable String id) {
        return ResponseEntity.status(200).body(
                Map.of(
                        "success", true,
                        "message", "Retrieve Blog By ID successfully",
                        "blog", blogService.getBlogById(id)
                )
        );
    }

    @PostMapping("/add")
    public ResponseEntity<Object> createBlog(@RequestBody BlogRequest blogRequest) {
        return ResponseEntity.status(201).body(
                Map.of(
                        "success", true,
                        "message", "Create Blog successfully",
                        "blog", blogService.createBlog(blogRequest)
                )
        );
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateBlog(@PathVariable String id, @RequestBody BlogRequest blogRequest) {
        return ResponseEntity.status(200).body(
                Map.of(
                        "success", true,
                        "message", "Update Blog successfully",
                        "blog",blogService.updateBlog(id, blogRequest)
                )
        );
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteBlog(@PathVariable String id) {
        return ResponseEntity.status(200).body(
                Map.of(
                        "success", true,
                        "message", "Delete Blog successfully",
                        "blog",blogService.deleteBlog(id)
                )
        );
    }
}
