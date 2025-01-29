package nvh.run.ideaswap.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import nvh.run.ideaswap.data.dto.BlogRequest;
import nvh.run.ideaswap.data.entity.Blogs;
import nvh.run.ideaswap.data.entity.Categories;
import nvh.run.ideaswap.data.entity.Users;
import nvh.run.ideaswap.data.repository.BlogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class BlogService {
    BlogRepository blogRepository;
    CategoryService categoryService;
    UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(BannerService.class);

    public List<Blogs> getAllBlogs() {
        List<Blogs> blogList;
        try {
            blogList = blogRepository.findAll()
                    .stream()
                    .map(blog ->
                            Blogs.builder()
                                    .id(blog.getId())
                                    .content(blog.getContent())
                                    .url(blog.getUrl())
                                    .userID(blog.getUserID())
                                    .categoryID(blog.getCategoryID())
                                    .createdDate(blog.getCreatedDate())
                                    .updatedDate(blog.getUpdatedDate())
                                    .build()).toList();
        } catch (Exception e) {
            throw new RuntimeException("Retrieve List Blogs failed",e);
        }
        return blogList;
    }

    public Blogs getBlogById(String id) {
        Blogs blog;
        try {
            blog= blogRepository.findById(id).orElseThrow(() -> new RuntimeException("Blog not found"));
        } catch (Exception e) {
            throw new RuntimeException("Get Blog By ID failed",e);
        }
        return blog;
    }

    public Blogs createBlog(BlogRequest blogRequest) {
        Categories categories = categoryService.getCategoryById(blogRequest.getCategoryID());
        Users user = userService.getUserById(blogRequest.getUserID());
        Blogs blog;
        try {
            blog = blogRepository.save(
                    Blogs.builder()
                            .id(blogRequest.getId())
                            .userID(user)
                            .categoryID(categories)
                            .content(blogRequest.getContent())
                            .url(blogRequest.getUrl())
                            .createdDate(LocalDateTime.now())
                            .updatedDate(LocalDateTime.now())
                            .build()
            );
        }catch (Exception e){
            throw new RuntimeException("Create Blog failed",e);
        }

        return blog;
    }

    public Blogs updateBlog(String id, BlogRequest blogRequest) {
        getBlogById(id);
        Categories categories = categoryService.getCategoryById(blogRequest.getCategoryID());
        Users user = userService.getUserById(blogRequest.getUserID());
        Blogs updatedBlog;
        try {
            updatedBlog = blogRepository.save(
                    Blogs.builder()
                            .id(id)
                            .userID(user)
                            .categoryID(categories)
                            .content(blogRequest.getContent())
                            .url(blogRequest.getUrl())
                            .createdDate(LocalDateTime.now())
                            .updatedDate(LocalDateTime.now())
                            .build()
            );
        }catch (Exception e){
            throw new RuntimeException("Update Blog failed",e);
        }
        return updatedBlog;
    }

    public Blogs deleteBlog(String id) {
        Blogs blog = getBlogById(id);
        try {
            blogRepository.delete(blog);
        }catch (Exception e){
            throw new RuntimeException("Delete Blog failed",e);
        }
        return blog;
    }
}
