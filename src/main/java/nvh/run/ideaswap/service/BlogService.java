package nvh.run.ideaswap.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import nvh.run.ideaswap.data.dto.BlogDTO;
import nvh.run.ideaswap.data.dto.CategoryDTO;
import nvh.run.ideaswap.data.dto.ManagerDTO;
import nvh.run.ideaswap.data.dto.UserDTO;
import nvh.run.ideaswap.data.entity.Blogs;
import nvh.run.ideaswap.data.entity.Categories;
import nvh.run.ideaswap.data.entity.Users;
import nvh.run.ideaswap.data.mapper.BlogMapper;
import nvh.run.ideaswap.data.mapper.CategoryMapper;
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
    BlogMapper blogMapper;
    UserService userService;
    CategoryService categoryService;
    CategoryMapper categoryMapper;

    private static final Logger logger = LoggerFactory.getLogger(BannerService.class);

    public List<BlogDTO> getAllBlogs() {
        List<BlogDTO> blogDTOList;
        try {
            blogDTOList = blogRepository.findAll().stream().map(blog -> BlogDTO.builder().build()).toList();
        } catch (Exception e) {
            throw new RuntimeException("Retrieve List Blogs failed",e);
        }
        return blogDTOList;
    }

    public BlogDTO getBlogById(String id) {
        Blogs blog;
        try {
            blog= blogRepository.findById(id).orElseThrow(() -> new RuntimeException("Blog not found"));
        } catch (Exception e) {
            throw new RuntimeException("Get Blog By ID failed",e);
        }
        return BlogDTO.builder().build();
    }

    public BlogDTO createBlog(BlogDTO blogDTO) {
        log.info("user find for blog : {}",userService.findById(blogDTO.getUserID()));
//        Users user = userService.findById(blogDTO.getUserID());
        CategoryDTO category = categoryService.getCategoryById(blogDTO.getId());
        Categories categories = categoryMapper.toEntity(category);
        Blogs blog = Blogs.builder()
                .id(blogDTO.getId())
                .categoryID(categories)
                .content(blogDTO.getContent())
                .url(blogDTO.getUrl())
                .userID()
                .build();
        Blogs savedBlog ;
        savedBlog = blogRepository.save(blog);

        return BlogDTO.builder().build();
    }

    public BlogDTO updateBlog(String id, BlogDTO blogDTO) {
        Blogs existingBlog = blogRepository.findById(id).orElseThrow(() -> new RuntimeException("Blog not found"));
        BlogDTO existingBlogDTO = blogMapper.toDto(existingBlog);
        UserDTO userDTO = userService.findById(blogDTO.getUserID());
        CategoryDTO categoryDTO = categoryService.getCategoryById(blogDTO.getId());
        existingBlogDTO.setUserID(blogDTO.getUserID());
        existingBlogDTO.setCategoryID(blogDTO.getCategoryID());
        Blogs updatedBlog = blogRepository.save(blogMapper.toEntity(existingBlogDTO,));
        return BlogDTO.builder().build();
    }

    public BlogDTO deleteBlog(String id) {
        Blogs blog = blogRepository.findById(id).orElseThrow(() -> new RuntimeException("Blog not found"));
        blogRepository.delete(blog);
        return BlogDTO.builder().build();
    }
}
