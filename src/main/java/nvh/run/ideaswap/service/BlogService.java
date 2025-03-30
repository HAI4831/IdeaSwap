package nvh.run.ideaswap.service;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import nvh.run.ideaswap.data.dto.BlogRequest;
import nvh.run.ideaswap.data.dto.NotificationRequest;
import nvh.run.ideaswap.data.entity.Blog;
import nvh.run.ideaswap.data.entity.Censorship;
import nvh.run.ideaswap.data.entity.Status;
import nvh.run.ideaswap.data.entity.User;
import nvh.run.ideaswap.data.repository.BlogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class BlogService {
    BlogRepository blogRepository;
    CategoryService categoryService;
    UserService userService;
    NotificationService notificationService;
    CloudinaryService cloudinaryService;
    CensorshipsService censorshipsService;
    @Autowired
    public BlogService(
            BlogRepository blogRepository,
            CategoryService categoryService,
            UserService userService,
            NotificationService notificationService,
            CloudinaryService cloudinaryService,
            @Lazy CensorshipsService censorshipsService) {
        this.blogRepository = blogRepository;
        this.categoryService = categoryService;
        this.userService = userService;
        this.notificationService = notificationService;
        this.cloudinaryService = cloudinaryService;
        this.censorshipsService = censorshipsService;
    }


    private static final Logger logger = LoggerFactory.getLogger(BannerService.class);

    @Cacheable(value = "blogs",key = "'page:' + #page + ':size:' + #size")
    public List<Blog> getAllBlogs(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        List<Blog> blogList;
        try {
            blogList = blogRepository.findAll(pageable)
                    .stream()
                    .map(blog ->
                            Blog.builder()
                                    .id(blog.getId())
                                    .content(blog.getContent())
                                    .url(blog.getUrl())
                                    .userID(blog.getUserID())
                                    .categoryID(blog.getCategoryID())
                                    .createdAt(blog.getCreatedAt())
                                    .updatedAt(blog.getUpdatedAt())
                                    .build()).toList();
        } catch (Exception e) {
            throw new RuntimeException("Retrieve List Blogs failed",e);
        }
        return blogList;
    }

    @Cacheable(value = "blogs")
    public List<Blog> getAllBlogs() {
        List<Blog> blogList;
        try {
            blogList = blogRepository.findAll()
                    .stream()
                    .map(blog ->
                            Blog.builder()
                                    .id(blog.getId())
                                    .content(blog.getContent())
                                    .url(blog.getUrl())
                                    .userID(blog.getUserID())
                                    .categoryID(blog.getCategoryID())
                                    .createdAt(blog.getCreatedAt())
                                    .updatedAt(blog.getUpdatedAt())
                                    .build()).toList();
        } catch (Exception e) {
            throw new RuntimeException("Retrieve List Blogs failed",e);
        }
        return blogList;
    }

    @Cacheable(value = "blog",key="#id")
    public Blog getBlogById(String id) {
        Blog blog;
        try {
            blog= blogRepository.findById(id).orElseThrow(() -> new RuntimeException("Blog not found"));
        } catch (Exception e) {
            throw new RuntimeException("Get Blog By ID:"+id+" failed",e);
        }
        return blog;
    }

    @CacheEvict(value = "blogs", allEntries = true)
    @CachePut(value = "blog")
//    @CachePut(value = "blog",key = "#blogRequest.id", condition = "#blogRequest.id!=null")
    public Blog createBlog(BlogRequest blogRequest) {
//        Categories categories = categoryService.getCategoryById(blogRequest.getCategoryID());
        User user = userService.getUserById(blogRequest.getUserID());
        String imageUrl = cloudinaryService.uploadImage(blogRequest.getImageBase64(),null,"blog");
        Blog blog;
        try {
//            if(imageUrl.isEmpty()){
//                throw new RuntimeException("upload image failed");
//            }
            blog = blogRepository.save(
                    Blog.builder()
                            .id(blogRequest.getId())
                            .userID(user.getId())
//                            .categoryID(categories.getId())
                            .content(blogRequest.getContent())
                            .url(imageUrl==null||imageUrl.isEmpty()?null:imageUrl)
                            .createdAt(LocalDateTime.now())
                            .updatedAt(LocalDateTime.now())
                            .build()
            );
            censorshipsService.createCensorship(Censorship.builder()
                            .status(Status.pending)
                            .contentID(blog.getId())
                            .feedback("Blog is awaiting approval")
                    .build());


        }catch (Exception e){
            throw new RuntimeException("Create Blog failed",e);
        }
        notificationService.createNotification(
                NotificationRequest.builder()
                        .userIDs(List.of(blog.getUserID()))
                        .description("Blog is awaiting approval")
                        .imageUrl(blog.getUrl())
                        .build()
        );
        return blog;
    }

    @CachePut(value = "blog", key = "#id", condition = "#id!=null")
    public Blog updateBlog(String id, BlogRequest blogRequest) {
        // Lấy blog hiện tại từ database
        Blog existingBlog = getBlogById(id);
        if (existingBlog == null) {
            throw new RuntimeException("Blog not found");
        }

        // Lấy user từ ID
        User user = userService.getUserById(blogRequest.getUserID());

        // Upload ảnh nếu có, nếu không thì giữ nguyên ảnh cũ
        Optional<String> optionalImageUrl = Optional.ofNullable(blogRequest.getImageBase64())
                .map(base64 -> cloudinaryService.uploadImage(base64, null, "blog"));

        try {
            // Chỉ cập nhật các trường có giá trị mới, giữ nguyên nếu null
            existingBlog.setUserID(Optional.ofNullable(user.getId()).orElse(existingBlog.getUserID()));
            existingBlog.setContent(Optional.ofNullable(blogRequest.getContent()).orElse(existingBlog.getContent()));
            optionalImageUrl.ifPresent(existingBlog::setUrl); // Cập nhật ảnh nếu có ảnh mới
            existingBlog.setUpdatedAt(LocalDateTime.now()); // Chỉ cập nhật updatedAt

            // Lưu lại blog đã cập nhật
            return blogRepository.save(existingBlog);
        } catch (Exception e) {
            throw new RuntimeException("Update Blog failed", e);
        }
    }

    @CacheEvict(value = "blog",key = "#id", condition = "#id!=null")
    public Blog deleteBlog(String id) {
        Blog blog = getBlogById(id);
        try {
            cloudinaryService.deleteImage(blog.getUrl(),null);
            blogRepository.delete(blog);
        }catch (Exception e){
            throw new RuntimeException("Delete Blog failed",e);
        }
        return blog;
    }
}
