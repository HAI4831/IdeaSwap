package nvh.run.ideaswap.common.configs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig {
    DotenvConfig dotenvConfig;
    private static final Logger logger = LoggerFactory.getLogger(MailConfig.class);
//cần hiểu thứ tự khởi động bean :nó sẽ tạo các bean cơ bản "configuration,service,component" như một instant thông qua contructor (nếu có) sau đó nó thêm các dependency là các phụ thuộc cần inject(Spring sẽ inject các dependencies cho bean bằng cách gọi các setter (nếu có) hoặc thông qua các trường được annotate bằng @Autowired (hoặc @Value đối với các thuộc tính).) , sau đó nó gọi phương thức preprocess xử lí rồi postconstruct rồi sẵn sàng sử dụng
    @Bean
    public JavaMailSender javaMailSender(DotenvConfig dotenvConfig,
            @Value("${spring.mail.username}") String mailUsername,
            @Value("${spring.mail.password}") String mailPassword
    ) {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);

        mailSender.setUsername(mailUsername);
        mailSender.setPassword(mailPassword);
        logger.info("@ mailSender username: {}", mailUsername);
        logger.info("@ mailSender password: {}", mailPassword);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }
}

//package nvh.run.ideaswap.common.configs;
//
//import jakarta.annotation.PostConstruct;
//import lombok.AccessLevel;
//import lombok.RequiredArgsConstructor;
//import lombok.experimental.FieldDefaults;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.JavaMailSenderImpl;
//import org.springframework.stereotype.Component;
//
//import java.util.Properties;
//
//@Component
//@FieldDefaults(level = AccessLevel.PRIVATE)
//@RequiredArgsConstructor
//public class MailConfig {
//    final Logger logger = LoggerFactory.getLogger(MailConfig.class);
//    @Value("${spring.mail.username}")
//    String mailUsername;
//
//    @Value("${spring.mail.password}")
//    String mailPassword;
//
//    @Bean
//    public JavaMailSender javaMailSender() {
//        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
//        mailSender.setHost("smtp.gmail.com");
//        mailSender.setPort(587);
//        logger.info("mailSender username: {}", mailUsername);
//        mailSender.setUsername(mailUsername);
//        logger.info("mailSender password: {}", mailPassword);
//        mailSender.setPassword(mailPassword);
//
//        Properties props = mailSender.getJavaMailProperties();
//        props.put("mail.transport.protocol", "smtp");
//        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.starttls.enable", "true");
//        props.put("mail.debug", "true");
//
//        return mailSender;
//    }
//    @PostConstruct
//    public void logMailProperties() {
//        logger.info("Mail config Mail Username: {}", mailUsername);
//        logger.info("Mail config Mail Password: {}", mailPassword
////                .replaceAll(".", "*")// Ẩn mật khẩu
//        );
//    }
//}
////    Environment environment;
////    @Bean
////    public JavaMailSender javaMailSender() {
////        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
////        mailSender.setHost("smtp.gmail.com");
////        mailSender.setPort(587);
////        logger.info("mailSender username: " + environment.getProperty("spring.mail.username"));
////        String mailUsername = environment.getProperty(
////                "spring.mail.username"
//////                , "default_email.com"
////        );
////        mailSender.setUsername(mailUsername);
////        logger.info("mailSender password: " + environment.getProperty("spring.mail.password"));
////        mailSender.setPassword(environment.getProperty(
////                "spring.mail.password"
//////                , "default_password"
////                )
////        );
////
////        Properties props = mailSender.getJavaMailProperties();
////        props.put("mail.transport.protocol", "smtp");
////        props.put("mail.smtp.auth", "true");
////        props.put("mail.smtp.starttls.enable", "true");
////        props.put("mail.debug", "true");
////
////        return mailSender;
////    }
////}
