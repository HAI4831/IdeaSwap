package nvh.run.ideaswap.api.controller;

import lombok.RequiredArgsConstructor;
import nvh.run.ideaswap.data.dto.UserRequest;
import nvh.run.ideaswap.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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
    public ResponseEntity<Object> createUser(@Validated @RequestBody UserRequest userRequest) {
        return ResponseEntity.status(201).body(
                Map.of(
                        "success", true,
                        "message", "User created successfully",
                        "user", userService.createUser(userRequest)
                )
        );
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable String id, @Validated @RequestBody UserRequest userRequest) {
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
}
