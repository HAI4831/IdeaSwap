package nvh.run.ideaswap.config;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("local")
public class DotenvConfig {
    private static final Logger logger = LoggerFactory.getLogger(DotenvConfig.class);

    @PostConstruct
    public void loadEnv() {
        Dotenv dotenv = Dotenv.load();
        if (dotenv != null) {
            logger.info("Dotenv loaded");
        }
        else {
            logger.info("Dotenv load failed");
        }
        //set tất cả biến trong env vào môi trường hệ thống(system_env)
        dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));

        logger.info("✅ [LOCAL] Dotenv loaded - Email: {} - Password: {}",
                dotenv.get("EMAIL"), dotenv.get("EMAIL_PASSWORD"));
    }
}
