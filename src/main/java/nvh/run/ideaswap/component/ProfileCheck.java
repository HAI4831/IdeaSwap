package nvh.run.ideaswap.component;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import jakarta.annotation.PostConstruct;

@Component
public class ProfileCheck {
    private final Environment env;

    public ProfileCheck(Environment env) {
        this.env = env;
    }

    @PostConstruct
    public void checkProfile() {
        System.out.println("🔥 Active Profile: " + String.join(", ", env.getActiveProfiles()));
    }
}

