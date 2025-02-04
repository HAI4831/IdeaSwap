package nvh.run.ideaswap.common.configs;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

@Component
public class MailSenderLogger {
    private static final Logger logger = LoggerFactory.getLogger(MailSenderLogger.class);

    private final JavaMailSender mailSender;

    public MailSenderLogger(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @PostConstruct
    public void logMailSenderProperties() {
        if (mailSender instanceof JavaMailSenderImpl sender) {
            logger.info("MailSender Username: {}", sender.getUsername());
            logger.info("MailSender Password: {}", sender.getPassword()
//                    .replaceAll(".", "*")// Ẩn mật khẩu
            );
            logger.info("MailSender Host: {}", sender.getHost());
            logger.info("MailSender Port: {}", sender.getPort());
        } else {
            logger.warn("MailSender is not an instance of JavaMailSenderImpl");
        }
    }
}

