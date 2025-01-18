package nvh.run.ideaswap.api.service;


import nvh.run.ideaswap.data.dto.BlogDTO;
import org.springframework.http.ResponseEntity;

public interface IBlogService {
    ResponseEntity<Object> getAllBlogs();
    ResponseEntity<Object> getBlogById(Long id);
    ResponseEntity<Object> createBlog(BlogDTO blogDTO);
    ResponseEntity<Object> updateBlog(Long id, BlogDTO blogDTO);
    ResponseEntity<Object> deleteBlog(Long id);
}

