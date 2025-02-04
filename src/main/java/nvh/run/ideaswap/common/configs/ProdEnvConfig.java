package nvh.run.ideaswap.common.configs;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("prod")
public class ProdEnvConfig {
    private static final Logger logger = LoggerFactory.getLogger(ProdEnvConfig.class);

    @PostConstruct
    public void loadEnv() {
        String email = System.getenv("EMAIL");
        String password = System.getenv("EMAIL_PASSWORD");

        if (email == null || password == null) {
            logger.error("⚠️ [PROD] EMAIL or EMAIL_PASSWORD is missing in environment variables!");
        } else {
            logger.info("✅ [PROD] Environment loaded - Email: {} - Password: {}", email, password);
        }
    }
}
