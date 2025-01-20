package nvh.run.ideaswap.data.mapper;

import nvh.run.ideaswap.data.dto.BlogDTO;
import nvh.run.ideaswap.data.entity.Blogs;
import nvh.run.ideaswap.data.entity.Categories;
import nvh.run.ideaswap.data.entity.Users;
import org.springframework.stereotype.Component;

@Component
public class BlogMapper {

    public BlogDTO toDto(Blogs blog) {
        if (blog == null) {
            return null;
        }

        return BlogDTO.builder()
                .id(blog.getId())
                .content(blog.getContent())
                .url(blog.getUrl())
                .userID(blog.getUserID() != null ? blog.getUserID().getId() : null)
                .categoryID(blog.getCategoryID() != null ? blog.getCategoryID().getId() : null)
                .build();
    }

    public Blogs toEntity(BlogDTO blogDTO, Users user, Categories category) {
        if (blogDTO == null) {
            return null;
        }

        return Blogs.builder()
                .id(blogDTO.getId())
                .content(blogDTO.getContent())
                .url(blogDTO.getUrl())
                .userID(user) // Tham chiếu đến entity Users
                .categoryID(category) // Tham chiếu đến entity Categories
                .build();
    }
}
