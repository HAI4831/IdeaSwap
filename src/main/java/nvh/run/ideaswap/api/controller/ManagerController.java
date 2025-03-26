package nvh.run.ideaswap.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nvh.run.ideaswap.data.dto.ManagerRequest;
import nvh.run.ideaswap.service.ManagerService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/manager")
@RequiredArgsConstructor
public class ManagerController {
    private final ManagerService managerService;

//    @InitBinder
//    public void initBinder(WebDataBinder binder) {
//        binder.registerCustomEditor(String.class, "_id", new PropertyEditorSupport() {
//            @Override
//            public void setAsText(String text) {
//                setValue(text);
//            }
//        });
//    }

    @GetMapping
    public ResponseEntity<Object> getAllManagers() {
        return ResponseEntity.ok(
                Map.of(
                        "success", true,
                        "managers", managerService.getAllManagers()
                ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getManagerById(@PathVariable String id) {
        return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Retrieve Manager By ID successfully",
                "manager", managerService.getManagerById(id)
        ));
    }

    @PostMapping("/add")
    public ResponseEntity<Object> createManager(@RequestBody ManagerRequest managerRequest) {
        return ResponseEntity.status(201).body(
                Map.of(
                        "success", true,
                        "message", "Manager created successfully",
                        "manager", managerService.createManager(managerRequest)
                )
        );
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateManager(@PathVariable String id, @RequestBody @Valid ManagerRequest managerRequest) {
        return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Manager updated successfully",
                "manager", managerService.updateManager(id, managerRequest)
        ));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteManager(@PathVariable String id) {
        return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Manager deleted successfully",
                "manager", managerService.deleteManager(id)
        ));
    }
    @ExceptionHandler(BindException.class)
    public ResponseEntity<Map<String, String>> handleBindException(BindException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );
        return ResponseEntity.badRequest().body(errors);
    }
//    @ExceptionHandler(org.springframework.boot.context.properties.bind.BindException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public ResponseEntity<?> handleBindException(org.springframework.boot.context.properties.bind.BindException e) {
//        log.warn("Illegal argument exception occurred: {}", e.getMessage(), e);
//
//        return ResponseEntity
//                .status(HttpStatus.BAD_REQUEST)
//                .body(createResponseError(
//                        e.getMessage(),
//                        e.getClass().getName(),
//                        e.getCause(),
//                        e.getStackTrace(),
//                        HttpStatus.BAD_REQUEST
//                ));
//    }
}

