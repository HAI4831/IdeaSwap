package nvh.run.ideaswap.api.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import nvh.run.ideaswap.data.entity.Categories;
import nvh.run.ideaswap.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/categories")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class CategoryController {
    CategoryService categoryService;

    @GetMapping
    public ResponseEntity<Object> getAllCategories() {
        return ResponseEntity.status(200).body(
            Map.of(
                    "succes",true,
                    "message", "Retrieve List Categories successfully",
                    "categories",categoryService.getAllCategories()
            )
    );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getCategoryById(@PathVariable String id) {
        return ResponseEntity.status(200).body(
            Map.of(
                    "success",true,
                    "message","Retrieve Category By ID successfully",
                    "category",categoryService.getCategoryById(id)
            )
    );
    }

    @PostMapping
    public ResponseEntity<Object> createCategory(@RequestBody Categories category) {
        return ResponseEntity.status(201).body(
            Map.of(
                    "success",true,
                    "message","Retrieve Category By ID successfully",
                    "category",categoryService.createCategory(category)
            )
    );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateCategory(@PathVariable String id, @RequestBody Categories category) {
        return ResponseEntity.status(200).body(
                Map.of(
                        "success",true,
                        "message","Update Category successfully",
                        "category",categoryService.updateCategory(id, category)
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCategory(@PathVariable String id) {
        return ResponseEntity.status(200).body(
                Map.of(
                        "success", true,
                        "message", String.format("Deleted category with id: %s", id),
                        "categories", categoryService.deleteCategory(id)
                )
        );
    }
}
