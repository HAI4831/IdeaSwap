package nvh.run.ideaswap.common.configs;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class LoggerConfig {
    private static final Logger logger = LoggerFactory.getLogger(LoggerConfig.class);

    @Value("${spring.mail.username}")
    private String mailUsername;

    @Value("${spring.mail.password}")
    private String mailPassword;
//    public LoggerConfig(
//            @Value("${spring.mail.username}") String mailUsername,
//            @Value("${spring.mail.password}") String mailPassword
//    ){
//        logger.info("Mail Username: {}", mailUsername);
//        logger.info("Mail Password: {}", mailPassword);
//    }

    @PostConstruct
    public void logMailProperties() {
        logger.info("Mail Username: {}", mailUsername);
        logger.info("Mail Password: {}", mailPassword
//                .replaceAll(".", "*")// Ẩn mật khẩu
        );
    }
}
