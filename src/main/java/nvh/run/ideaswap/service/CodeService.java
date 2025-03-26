package nvh.run.ideaswap.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import nvh.run.ideaswap.data.entity.Codes;
import nvh.run.ideaswap.data.repository.CodeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Date;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CodeService {
    private static final Logger logger = Logger.getLogger(CodeService.class.getName());
    CodeRepository codeRepository;

    public Codes saveCode(Codes codes) {
        Codes saved ;
        try {
            saved= codeRepository.save(codes);
        } catch (Exception e) {
            throw new RuntimeException("Save code failed ",e);
        }
        return saved;
    }
    public Codes verifyCode(String userEmail,int code) {
        Codes codeEntity;
        Date dateNow = Date.from(Instant.now());
        try {
            codeEntity = codeRepository.findValidCode(code,userEmail,dateNow).orElseThrow(() -> new RuntimeException("Verification code not found or expired for email: "+ userEmail +" with code: "+code));
        } catch (Exception e) {
            throw new RuntimeException("Verify code failed",e);
        }
        return codeEntity;
    }
}

