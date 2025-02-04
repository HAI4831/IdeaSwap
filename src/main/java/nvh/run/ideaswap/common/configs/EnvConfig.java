package nvh.run.ideaswap.common.configs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Profile;

@Profile("Production")
@Configuration
public class EnvConfig {
    private static final Logger logger = LoggerFactory.getLogger(EnvConfig.class);

    @PostConstruct
    public void loadEnv() {
        String email = System.getenv("EMAIL");
        String emailPassword = System.getenv("EMAIL_PASSWORD");

        logger.info("Loaded environment variables: EMAIL={}, EMAIL_PASSWORD={}", email, emailPassword);
    }
}

