package nvh.run.ideaswap.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import nvh.run.ideaswap.data.entity.Users;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

@Service
@Transactional
//@Validated
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@Log4j2
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmailService {
    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);
    JavaMailSender mailSender;

    public void sendSimpleEmail(String to, String subject, String text) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, false, "UTF-8");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text, false); // false = text thuần, true = HTML

            mailSender.send(message);
            System.out.println("Email sent successfully!");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public boolean sendVerificationCode(String email,int code, Users user) {
        try {
            // Prepare the email content
//            String htmlTemplate = new String(Files.readAllBytes(Paths.get("src/template/verification_email_template.html")));
            ClassPathResource resource = new ClassPathResource("templates/verification_email_template.html");
            Path path = resource.getFile().toPath();
            String htmlTemplate =  Files.readString(path, StandardCharsets.UTF_8);
            htmlTemplate = htmlTemplate.replace("{{code}}", String.valueOf(code))
                    .replace("{{fullName}}", user.getFirstName() + " " + user.getLastName());

            // Prepare the email message
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            if (mailSender instanceof JavaMailSenderImpl sender) {
                helper.setFrom(Objects.requireNonNull(sender.getUsername()));
            }

            helper.setTo(email);
            helper.setSubject("Your Verification Code");
            helper.setText(htmlTemplate, true); // HTML content

            // Send the email
            mailSender.send(message);
            logger.info("Verification email sent to: {}", email);
            return true;
        } catch (IOException | MessagingException | MailException e) {
            logger.error("Error occurred while sending email: {}", e.getMessage());
            return false;
        }
    }

}

