package nvh.run.ideaswap.api.controller;

import nvh.run.ideaswap.api.service.intf.IBlogService;
import nvh.run.ideaswap.data.dto.BlogDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/blog")
public class BlogController {
    @Autowired
    private IBlogService blogService;

    @GetMapping
    public ResponseEntity<Object> getAllBlogs() {
        return blogService.getAllBlogs();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getBlogById(@PathVariable String id) {
        return blogService.getBlogById(id);
    }

    @PostMapping
    public ResponseEntity<Object> createBlog(@RequestBody BlogDTO blogDTO) {
        return blogService.createBlog(blogDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateBlog(@PathVariable String id, @RequestBody BlogDTO blogDTO) {
        return blogService.updateBlog(id, blogDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteBlog(@PathVariable String id) {
        return blogService.deleteBlog(id);
    }
}
