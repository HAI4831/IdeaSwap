package nvh.run.ideaswap.common.configs;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
@EnableMongoAuditing
public class MongoConfig
//        extends AbstractMongoClientConfiguration
{

    private final Dotenv dotenv = Dotenv.load();

    @Bean
    public MongoClient mongoClient() {
        String mongoUri = dotenv.get("MONGO_URI");
        System.out.println("Mongo URI from dotenv: " + mongoUri);
        return MongoClients.create(mongoUri);
    }

    @Bean
    public MongoTemplate mongoTemplate(MongoClient mongoClient) {
        return new MongoTemplate(mongoClient, "ideaswap");
    }

//    @Override
//    public MongoCustomConversions customConversions() {
//        List<Converter<?, ?>> converters = new ArrayList<>();
//        converters.add(new ObjectIdToRolesConverter());  // Thêm converter từ ObjectId sang Roles
//        return new MongoCustomConversions(converters);
//    }
//
//    @Override
//    protected String getDatabaseName() {
//        return "ideaswap";  // Đặt tên database của bạn ở đây
//    }
}
