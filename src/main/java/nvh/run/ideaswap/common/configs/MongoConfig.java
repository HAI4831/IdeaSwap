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

}
