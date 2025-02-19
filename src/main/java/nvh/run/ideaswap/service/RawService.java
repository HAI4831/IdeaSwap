package nvh.run.ideaswap.service;

public class RawService {
//    @Autowired
//    private MongoTemplate mongoTemplate;
//    public List<Blogs> getBlogsWithLogs() {
//        Aggregation aggregation = Aggregation.newAggregation(
//                Aggregation.match(Criteria.where("userID").exists(true))
//        );
//
//        // Fetch raw aggregated data
//        AggregationResults<Document> rawResults = mongoTemplate.aggregate(aggregation, "blogs", Document.class);
//
//        // Log raw aggregated data
//        rawResults.getMappedResults().forEach(doc -> logger.info("Raw Aggregated Data: {}", doc.toJson()));
//
//        // Map to Banners entity
//        AggregationResults<Blogs> mappedResults = mongoTemplate.aggregate(aggregation, "blogs", Blogs.class);
//        return mappedResults.getMappedResults();
//    }
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
}
