package nvh.run.ideaswap.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nvh.run.ideaswap.data.dto.UserRequest;
import nvh.run.ideaswap.data.dto.auth.request.RegisterRequest;
import nvh.run.ideaswap.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<Object> getAllUsers() {
        return ResponseEntity.status(200).body(
                Map.of(
                        "success", true,
                        "message", "Retrieve List Users successfully",
                        "users", userService.getAllUsers())
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable String id) {
        return ResponseEntity.status(200).body(
                Map.of(
                        "success", true,
                        "message", "Retrieve User By ID successfully",
                        "user", userService.getUserById(id)
                )
        );
    }

    @PostMapping("/add")
    public ResponseEntity<Object> createUser(@RequestBody RegisterRequest registerRequest) {
        return ResponseEntity.status(201).body(
                Map.of(
                        "success", true,
                        "message", "User created successfully",
                        "user", userService.createUser(registerRequest)
                )
        );
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable String id, @Valid @RequestBody UserRequest userRequest) {
        return ResponseEntity.status(200).body(
                Map.of(
                        "success", true,
                        "message", "User updated successfully",
                        "user", userService.updateUser(id, userRequest)
                )
        );
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable String id) {
        return ResponseEntity.status(200).body(
                Map.of(
                        "success", true,
                        "message", "User deleted successfully",
                        "user",userService.deleteUser(id)
                )
        );
    }
    @PostMapping("/forget-password")
    public ResponseEntity<?> forgetPassword(@RequestParam String email,@RequestParam String username) {
        return ResponseEntity.status(200).body(
                Map.of(
                        "success", true,
                        "message", "User deleted successfully",
                        "user",userService.forgetPassword(email,username)
                )
        );
    }
    @PostMapping("/reset-password/{id}")
    public ResponseEntity<?> resetPassword(@PathVariable String id, @RequestParam String email, @RequestParam String newPassword, @RequestParam int code) {
        return ResponseEntity.status(200).body(
                Map.of(
                        "success", true,
                        "message", "User deleted successfully",
                        "user",userService.resetPassword(id,email,newPassword,code)
                )
        );
    }
}
