package nvh.run.ideaswap.api.controller;

import lombok.RequiredArgsConstructor;
import nvh.run.ideaswap.data.dto.SendCodeRequest;
import nvh.run.ideaswap.data.dto.VerifyCodeRequest;
import nvh.run.ideaswap.data.entity.Codes;
import nvh.run.ideaswap.service.CodeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/code")
@RequiredArgsConstructor
public class CodeController {

    private final CodeService codeService;

    @PostMapping("/send")
    public ResponseEntity<?> sendCode(@RequestBody SendCodeRequest request) {
        Codes code = codeService.sendVerificationCode(request.getEmail());
        boolean success = code!=null;
        return success
                ? ResponseEntity.ok(new ResponseMessage(true,"An email has been sent. success sent with verification code",code))
                : ResponseEntity.ok(new ResponseMessage(false,"An email has not been sent, sent failed.",code));
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyCode(@RequestBody VerifyCodeRequest request) {
        Codes code = codeService.verifyCode(request.getEmail(), request.getCode());
        boolean isValid = code!=null;
        return isValid
                ? ResponseEntity.ok(new ResponseMessage(true, "Correct verification code",code))
                : ResponseEntity.badRequest().body(new ResponseMessage(false, "Verification code is incorrect",code));
    }

    private record ResponseMessage(boolean success, String message, Codes code) {}
}
