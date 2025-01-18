package nvh.run.ideaswap.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/blog")
public class BlogController {

    private final BlogService blogService;

    @Autowired
    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    @GetMapping
    public List<BlogDTO> getAllBlogs() {
        return blogService.getAllBlogs();
    }

    @GetMapping("/{id}")
    public BlogDTO getBlogById(@PathVariable Long id) {
        return blogService.getBlogById(id);
    }

    @PostMapping
    public BlogDTO createBlog(@RequestBody BlogDTO blogDTO) {
        return blogService.createBlog(blogDTO);
    }

    @PutMapping("/{id}")
    public BlogDTO updateBlog(@PathVariable Long id, @RequestBody BlogDTO blogDTO) {
        return blogService.updateBlog(id, blogDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteBlog(@PathVariable Long id) {
        blogService.deleteBlog(id);
    }
}

