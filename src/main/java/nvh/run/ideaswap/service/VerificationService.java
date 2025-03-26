package nvh.run.ideaswap.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import nvh.run.ideaswap.data.entity.Codes;
import nvh.run.ideaswap.data.entity.Users;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Date;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class VerificationService {
    EmailService emailService;
    UserService userService;
    CodeService codeService;
    private static final Random RANDOM = new Random();

    public Codes sendVerificationCode(String email) {
        Users user = userService.findUserByEmail(email);
        int code = 100000 + RANDOM.nextInt(900000); // Random 6-digit number
        Date expirationDate = Date.from(Instant.now().plusSeconds(3600));

        Codes verificationCode = Codes.builder()
                .code(code)
                .codeExpiration(expirationDate)
                .userEmail(email)
                .build();
        try {
            if(!emailService.sendVerificationCode(email, code, user))
                throw new RuntimeException("Failed to send verification code");
            codeService.saveCode(verificationCode);
        } catch (Exception e) {
            throw new RuntimeException("Failed to save verification code",e);
        }
        return verificationCode;
    }

}
