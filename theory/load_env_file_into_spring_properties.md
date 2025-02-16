```java
package nvh.run.ideaswap.common.configs;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Configuration
//@Profile("local")
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
```
```properties
spring.application.name=IdeaSwap
spring.profiles.active=local
server.port=${PORT:8080}
jwt.password=abCD@1234
jwt.ACCESS_TOKEN_VALID_DURATION=3600000
jwt.REFRESH_TOKEN_VALID_DURATION=86400000
## JWT Configuration ##
#jwt.secret = 2b44b0b00fd822d8ce753e54dac3dc4e06c2725f7db930f3b9924468b53194dbccdbe23d7baa5ef5fbc414ca4b2e64700bad60c5a7c45eaba56880985582fba4
#jwt.expiration = 36000000

spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=${EMAIL:default_email.com}
spring.mail.password=${EMAIL_PASSWORD:default_password}
spring.google-cloud.driver.client-id=${CLIENT_ID:default-secret}
spring.google-cloud.driver.client-secret=${CLIENT_SECRET:default-secret}
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```
với code DotenvConfig nó load tất cả biến env vào môi trường hệ thống 
khi đó spring dùng application.properties với dùng ${} nó sẽ tham chiếu nhận giá trị từ biến môi trường