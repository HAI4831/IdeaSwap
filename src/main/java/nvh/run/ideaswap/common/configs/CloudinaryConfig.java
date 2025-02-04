package nvh.run.ideaswap.common.configs;

import com.cloudinary.Cloudinary;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {

    @Bean
    public Cloudinary cloudinary() {
        Dotenv dotenv = Dotenv.load(); // Load biến môi trường từ .env
        String cloudinaryUrl = dotenv.get("CLOUDINARY_URL");

        Cloudinary cloudinary = new Cloudinary(cloudinaryUrl);

        System.out.println("Cloudinary Cloud Name: " + cloudinary.config.cloudName); // Debug

        return cloudinary;
    }
//    @Value("${cloudinary.cloud-name}")
//    private String cloudName;
//
//    @Value("${cloudinary.api-key}")
//    private String apiKey;
//
//    @Value("${cloudinary.api-secret}")
//    private String apiSecret;
//
//    @Bean
//    public Cloudinary cloudinary() {
//        return new Cloudinary(ObjectUtils.asMap(
//                "cloud_name", cloudName,
//                "api_key", apiKey,
//                "api_secret", apiSecret
//        ));
//    }
}
