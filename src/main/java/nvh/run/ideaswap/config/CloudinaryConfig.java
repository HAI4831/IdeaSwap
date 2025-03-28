package nvh.run.ideaswap.config;

import com.cloudinary.Cloudinary;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {

    @Bean
    public Cloudinary cloudinary() {
        String cloudinaryUrl;

        String profile = System.getProperty("spring.profiles.active");
        if ("local".equals(profile)) {
            // Chạy môi trường local: đọc từ .env
            Dotenv dotenv = Dotenv.load();
            cloudinaryUrl = dotenv.get("CLOUDINARY_URL");
        } else {
            // Chạy môi trường production: đọc từ biến môi trường hệ thống
            cloudinaryUrl = System.getenv("CLOUDINARY_URL");
        }

        Cloudinary cloudinary = new Cloudinary(cloudinaryUrl);

        System.out.println("Cloudinary Cloud Name: " + cloudinary.config.cloudName); // Debug

        return cloudinary;
    }
}
