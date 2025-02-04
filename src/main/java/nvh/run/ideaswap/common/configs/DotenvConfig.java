package nvh.run.ideaswap.common.configs;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

//implementation 'io.github.cdimascio:dotenv-java:2.2.4'
@Configuration
public class DotenvConfig {
    Logger logger = LoggerFactory.getLogger(DotenvConfig.class);
    @PostConstruct
    public void loadEnv() {
        Dotenv dotenv = Dotenv.load();
        dotenv.entries().forEach(entry ->
                System.setProperty(entry.getKey(), entry.getValue())
        );
        logger.info("dotenv loaded mail sender have username "+dotenv.get("EMAIL")+" password "+dotenv.get("EMAIL_PASSWORD"));
    }
}

