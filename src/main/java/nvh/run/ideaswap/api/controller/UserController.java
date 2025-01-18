package nvh.run.ideaswap.api.controller;

import lombok.RequiredArgsConstructor;
import nvh.run.ideaswap.api.service.intf.IUserService;
import nvh.run.ideaswap.data.dto.UserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final IUserService iUserService;

    @GetMapping
    public ResponseEntity<Object> getAllUsers() {
        return iUserService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable String id) {
        return iUserService.getUserById(id);
    }

    @PostMapping
    public ResponseEntity<Object> createUser(@Validated @RequestBody UserDTO userDTO) {
        return iUserService.createUser(userDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable String id, @Validated @RequestBody UserDTO userDTO) {
        return iUserService.updateUser(id, userDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable String id) {
        return iUserService.deleteUser(id);
    }
}
