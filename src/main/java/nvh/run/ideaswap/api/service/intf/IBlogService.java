package nvh.run.ideaswap.api.service.intf;


import nvh.run.ideaswap.data.dto.BlogDTO;
import org.springframework.http.ResponseEntity;

public interface IBlogService {
    ResponseEntity<Object> getAllBlogs();
    ResponseEntity<Object> getBlogById(String id);
    ResponseEntity<Object> createBlog(BlogDTO blogDTO);
    ResponseEntity<Object> updateBlog(String id, BlogDTO blogDTO);
    ResponseEntity<Object> deleteBlog(String id);
}

