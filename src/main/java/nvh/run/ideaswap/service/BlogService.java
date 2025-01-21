package nvh.run.ideaswap.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import nvh.run.ideaswap.data.entity.Blogs;
import nvh.run.ideaswap.data.repository.BlogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class BlogService {
    BlogRepository blogRepository;

    private static final Logger logger = LoggerFactory.getLogger(BannerService.class);

    public List<Blogs> getAllBlogs() {
        List<Blogs> blogList;
        try {
            blogList = blogRepository.findAll().stream().map(blog -> Blogs.builder().build()).toList();
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

    public Blogs createBlog(Blogs blog) {

        Blogs savedBlog ;
        try {
            savedBlog = blogRepository.save(blog);
        }catch (Exception e){
            throw new RuntimeException("Create Blog failed",e);
        }

        return savedBlog;
    }

    public Blogs updateBlog(String id, Blogs blog) {
        blog.setId(id);
        Blogs updatedBlog;
        try {
            updatedBlog= blogRepository.save(blog);
        } catch (Exception e) {
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
