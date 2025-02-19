package nvh.run.ideaswap.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import nvh.run.ideaswap.common.exceptions.exception.global.ErrorResponse;
import nvh.run.ideaswap.data.dto.CreateGroup;
import nvh.run.ideaswap.data.dto.RoleRequest;
import nvh.run.ideaswap.service.RoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/role")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@SecurityRequirement(name = "bearerAuth")
public class RoleController {

    RoleService roleService;

    @GetMapping
    public ResponseEntity<Object> getAllRoles() {
        return ResponseEntity.status(200).body(
                Map.of(
                        "success", true,
                        "message", "Retrieve List Roles successfully",
                        "roles", roleService.getAllRoles()
                )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getRoleById(@PathVariable("id") String id) {
        return ResponseEntity.status(200).body(
                Map.of(
                        "success", true,
                        "message", "Retrieve Role By ID successfully",
                        "role", roleService.getRoleById(id)
                )
        );
    }

    @PostMapping("/add")
    public ResponseEntity<Object> createRole
            (
                @Validated(CreateGroup.class)
                @RequestBody RoleRequest roleRequest,
                BindingResult result
            )
    {
        if (result.hasErrors()) {
            // Lấy tất cả các lỗi và trả về cho người dùng
            String errorMessage = result.getAllErrors()
                    .stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.joining(", "));
            return ResponseEntity.badRequest().body(
                    ErrorResponse.builder()
                            .message("Invalid content request")
                            .path("/api/v1/role/add RoleController.createRole")
                            .error(errorMessage)
                            .success(false)
                            .errorClass(this.getClass().getSimpleName())
                            .timestamp(LocalDateTime.now())
                    .build());
        }
        return ResponseEntity.status(201).body(
                Map.of(
                        "success", true,
                        "message", "Create Role successfully",
                        "role", roleService.createRole(roleRequest)
                )
        );
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateRole(@PathVariable("id") String id, @Validated @RequestBody RoleRequest roleRequest) {
        return ResponseEntity.status(200).body(
                Map.of(
                        "success", true,
                        "message", "Update Role successfully",
                        "role", roleService.updateRole(id, roleRequest)
                )
        );
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteRole(@PathVariable("id") String id) {
        return ResponseEntity.status(200).body(
                Map.of(
                        "success", true,
                        "message", "Delete Role successfully",
                        "role", roleService.deleteRole(id)
                )
        );
    }
}

