package nvh.run.ideaswap.api.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import nvh.run.ideaswap.api.service.intf.IBlogService;
import nvh.run.ideaswap.data.dto.BlogDTO;
import nvh.run.ideaswap.data.entity.Blogs;
import nvh.run.ideaswap.data.entity.Users;
import nvh.run.ideaswap.data.repository.BlogRepository;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class BlogService implements IBlogService {
    BlogRepository blogRepository;
    UserService userService;
    @Autowired
    private MongoTemplate mongoTemplate;

    private static final Logger logger = LoggerFactory.getLogger(BannerService.class);

//    public void logRawDocuments() {
//        // Query raw data from MongoDB
//        List<Documents> rawData = mongoTemplate.findAll(Documents.class, "banners");
//
//        // Log raw data as JSON
//        rawData.forEach(document -> logger.info("Raw Document: {}", document.toJson()));
//    }
//    public List<Banners> getAllBanners() {
//        // Query raw data for logging purposes
//        List<Documents> rawData = mongoTemplate.findAll(Documents.class, "banners");
//
//        // Log raw data
//        rawData.forEach(document -> logger.info("Raw Data: {}", document.toJson()));
//
//        // Map to Banners entity
//        return mongoTemplate.findAll(Banners.class);
//    }

//    public List<Blogs> getBlogsWithLogs() {
//        Aggregation aggregation = Aggregation.newAggregation(
//                Aggregation.match(Criteria.where("userID").exists(true))//Điều kiện lọc các tài liệu có trường name tồn tại.
//        );
//
//        AggregationResults<Blogs> rawResults = mongoTemplate.aggregate(aggregation, "blogs", Blogs.class);
//
//        rawResults.getMappedResults().forEach(blog -> logger.info("Raw Aggregated Data: {}", blog.toJson()));
//
//        AggregationResults<Blogs> mappedResults = mongoTemplate.aggregate(aggregation, "blogs", Blogs.class);
//        return mappedResults.getMappedResults();
//    }


    public List<Blogs> getBlogsWithLogs() {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("userID").exists(true))
        );

        // Fetch raw aggregated data
        AggregationResults<Document> rawResults = mongoTemplate.aggregate(aggregation, "blogs", Document.class);

        // Log raw aggregated data
        rawResults.getMappedResults().forEach(doc -> logger.info("Raw Aggregated Data: {}", doc.toJson()));

        // Map to Banners entity
        AggregationResults<Blogs> mappedResults = mongoTemplate.aggregate(aggregation, "blogs", Blogs.class);
        return mappedResults.getMappedResults();
    }
    @Override
    public ResponseEntity<Object> getAllBlogs() {
        getBlogsWithLogs();
        List<Blogs> blogs;
        try {
            blogs = blogRepository.findAll();
        } catch (Exception e) {
//            return GlobalExceptionHandler.createResponseError("blog search exception", e.getClass().getName(), e.getCause(), e.getStackTrace(), HttpStatus.INTERNAL_SERVER_ERROR);
//            throw new RuntimeException("blog search exception",e);
            return ResponseEntity.status(500).body(
                    Map.of(
                            "success", false,
                            "message", "Retrieve List Blogs failed",
                            "error", e.getMessage()
                    )
            );
        }
        return ResponseEntity.status(200).body(
                Map.of(
                        "success", true,
                        "message", "Retrieve List Blogs successfully",
                        "blogs", blogs
                )
        );
    }

    @Override
    public ResponseEntity<Object> getBlogById(String id) {
        Blogs blog = blogRepository.findById(id).orElseThrow(() -> new RuntimeException("Blog not found"));
        return ResponseEntity.status(200).body(
                Map.of(
                        "success", true,
                        "message", "Retrieve Blog By ID successfully",
                        "blog", blog
                )
        );
    }

    @Override
    public ResponseEntity<Object> createBlog(BlogDTO blogDTO) {
        log.info("user find for blog : {}",userService.findById(blogDTO.getUserID()));
        Users user = userService.findById(blogDTO.getUserID());
        Blogs blog = Blogs.builder()
                .content(blogDTO.getContent())
                .url(blogDTO.getUrl())
                .userID(user) // Assumes that userID is properly populated in the DTO
                .build();
        Blogs savedBlog ;
        savedBlog = blogRepository.save(blog);
//        try {
//
//        } catch (Exception e) {
//            return ResponseEntity.status(500).body(
//                    Map.of(
//                            "success", false,
//                            "message", "Create Blog failed",
//                            "error", e.getMessage()
//                    )
//            );
//        }
        return ResponseEntity.status(201).body(
                Map.of(
                        "success", true,
                        "message", "Create Blog successfully",
                        "blog", savedBlog
                )
        );
    }

    @Override
    public ResponseEntity<Object> updateBlog(String id, BlogDTO blogDTO) {
        Blogs existingBlog = blogRepository.findById(id).orElseThrow(() -> new RuntimeException("Blog not found"));

        existingBlog.setContent(blogDTO.getContent());
        existingBlog.setUrl(blogDTO.getUrl());
//        existingBlog.setUserID(blogDTO.getUserID());

        Blogs updatedBlog = blogRepository.save(existingBlog);
        return ResponseEntity.status(200).body(
                Map.of(
                        "success", true,
                        "message", "Update Blog successfully",
                        "blog", updatedBlog
                )
        );
    }

    @Override
    public ResponseEntity<Object> deleteBlog(String id) {
        Blogs blog = blogRepository.findById(id).orElseThrow(() -> new RuntimeException("Blog not found"));
        blogRepository.delete(blog);
        return ResponseEntity.status(200).body(
                Map.of(
                        "success", true,
                        "message", "Delete Blog successfully"
                )
        );
    }
}
