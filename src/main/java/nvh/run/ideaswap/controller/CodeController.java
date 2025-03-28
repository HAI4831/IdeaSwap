package nvh.run.ideaswap.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import nvh.run.ideaswap.data.dto.SendCodeRequest;
import nvh.run.ideaswap.data.dto.VerifyCodeRequest;
import nvh.run.ideaswap.data.entity.Code;
import nvh.run.ideaswap.service.CodeService;
import nvh.run.ideaswap.service.VerificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/code")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CodeController {
    VerificationService verificationService;
    CodeService codeService;

    @PostMapping("/send")
    public ResponseEntity<?> sendCode(@RequestBody SendCodeRequest request) {
        Code code = verificationService.sendVerificationCode(request.getEmail());
        boolean success = code!=null;
        return success
                ? ResponseEntity.ok(new ResponseMessage(true,"An email has been sent. success sent with verification code",code))
                : ResponseEntity.ok(new ResponseMessage(false,"An email has not been sent, sent failed.",code));
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyCode(@RequestBody VerifyCodeRequest request) {
        Code code = codeService.verifyCode(request.getEmail(), request.getCode());
        boolean isValid = code!=null;
        return isValid
                ? ResponseEntity.ok(new ResponseMessage(true, "Correct verification code",code))
                : ResponseEntity.badRequest().body(new ResponseMessage(false, "Verification code is incorrect",code));
    }

    private record ResponseMessage(boolean success, String message, Code code) {}
}
