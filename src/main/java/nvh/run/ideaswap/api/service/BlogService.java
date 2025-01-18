
package nvh.run.ideaswap.api.service;

import jakarta.validation.Valid;
import nvh.run.ideaswap.data.dto.BlogDTO;
import nvh.run.ideaswap.data.entity.Blogs;
import nvh.run.ideaswap.data.entity.Users;
import nvh.run.ideaswap.data.repository.BlogRepository;
import nvh.run.ideaswap.errors.BlogNotFoundException;
import nvh.run.ideaswap.errors.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
@Validated
public class BlogServiceImpl implements BlogService {
    private final BlogRepository blogRepository;
    @Autowired
    public BlogServiceImpl(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }
    @Override
public List<BlogDTO> getAllBlogs() {

return blogRepository.findAll().stream()
        .map(blog -> new BlogDTO(blog.getId(), blog.getContent(), blog.getUrl(), Optional.ofNullable(blog.getUserID()).map(Users::getId).orElse(null)))
        .collect(Collectors.toList());
    }
    @Override
    public BlogDTO getBlogById(Long id) {
        Blogs blog = blogRepository.findById(id.toString())
                .orElseThrow(() -> new BlogNotFoundException("Blog not found"));
        return new BlogDTO(blog.getId(), blog.getContent(), blog.getUrl(), Optional.ofNullable(blog.getUserID()).map(Users::getId).orElse(null));
    }
    @Override
    public BlogDTO getBlogById(String id) {
        Blogs blog = blogRepository.findById(id).orElseThrow(() -> new BlogNotFoundException("Blog not found"));
        return new BlogDTO(blog.getId(), blog.getContent(), blog.getUrl(), Optional.ofNullable(blog.getUserID()).map(Users::getId).orElse(null));
    }
    @Override
    public BlogDTO createBlog(@Valid BlogDTO blogDTO) throws UserNotFoundException {
        validateBlogDTO(blogDTO);
        Users user = userRepository.findById(blogDTO.getUserID())
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        Blogs blog = Blogs.builder()
                .content(blogDTO.getContent())
                .url(blogDTO.getUrl())
                .userID(user)
                .build();
        Blogs savedBlog = blogRepository.save(blog);
        return new BlogDTO(savedBlog.getId(), savedBlog.getContent(), savedBlog.getUrl(), savedBlog.getUserID().getId());
    }
    @Override
    Blogs blog = blogRepository.findById(id.toString())
            .orElseThrow(() -> new BlogNotFoundException("Blog not found"));
blog.setContent(blogDTO.getContent());
blog.setUrl(blogDTO.getUrl());
Users user = userRepository.findById(blogDTO.getUserID())
        .orElseThrow(() -> new UserNotFoundException("User not found"));
blog.setUserID(user);
Blogs updatedBlog = blogRepository.save(blog);
return new BlogDTO(updatedBlog.getId(), updatedBlog.getContent(), updatedBlog.getUrl(), updatedBlog.getUserID().getId());
}

validateBlogDTO(blogDTO);
blog.setContent(blogDTO.getContent());
blog.setUrl(blogDTO.getUrl());

    public ResponseEntity<String> deleteBlog(Long id) {
        Blogs blog = blogRepository.findById(id.toString())
                .orElseThrow(() -> new BlogNotFoundException("Blog not found"));
        blogRepository.delete(blog);
        return ResponseEntity.ok("Blog deleted successfully");
    }
    @Override
    public BlogDTO updateBlog(String id, BlogDTO blogDTO) {
        Blogs blog = blogRepository.findById(id)
                .orElseThrow(() -> new BlogNotFoundException("Blog not found"));
        validateBlogDTO(blogDTO);
        Users user = userRepository.findById(blogDTO.getUserID())
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        blog.setContent(blogDTO.getContent());
        blog.setUrl(blogDTO.getUrl());
        blog.setUserID(user);
        Blogs updatedBlog = blogRepository.save(blog);
        return new BlogDTO(updatedBlog.getId(), updatedBlog.getContent(), updatedBlog.getUrl(), Optional.ofNullable(updatedBlog.getUserID()).map(Users::getId).orElse(null));
    }
public ResponseEntity<String> deleteBlog(String id) {
    public ResponseEntity<String> deleteBlog(String id) {
        Blogs blog = blogRepository.findById(id)
                .orElseThrow(() -> new BlogNotFoundException("Blog not found"));
        blogRepository.delete(blog);
        return ResponseEntity.ok("Blog deleted successfully");
    }
}