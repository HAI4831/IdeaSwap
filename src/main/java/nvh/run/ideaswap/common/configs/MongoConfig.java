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
    @Bean
    public MongoClient mongoClient() {
        String mongoUri=null;
        String activeProfile = System.getProperty("spring.profiles.active", "local");
        if(activeProfile.equals("local")) {
            Dotenv dotenv = Dotenv.load();
            mongoUri = dotenv.get("MONGO_URI");
            System.out.println("Mongo URI from dot_env: " + mongoUri);
        }
        else {
            mongoUri = System.getenv("MONGO_URI");
            System.out.println("Mongo URI from system_env: " + mongoUri);
        }

        return MongoClients.create(mongoUri);
    }

    @Bean
    public MongoTemplate mongoTemplate(MongoClient mongoClient) {
        return new MongoTemplate(mongoClient, "ideaswap");
    }

}
