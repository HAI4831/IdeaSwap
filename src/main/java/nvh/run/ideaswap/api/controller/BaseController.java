//package nvh.run.ideaswap.api.controller;
//
//import nvh.run.ideaswap.api.service.BaseService;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/v1")
//public abstract class BaseController<T, D> {
//    private BaseService<T, Long> baseService;
//    public BaseController(BaseService<T, Long> baseService){
//        this.baseService = baseService;
//    }
//    @GetMapping
//    public ResponseEntity<List<T>> findAll() {
//        List<T> entities = baseService.findAll();
//        return ResponseEntity.ok(entities);
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<T> findById(@PathVariable Long id) {
//        T entity = baseService.findById(id);
//        return ResponseEntity.ok(entity);
//    }
//
//    @PostMapping
//    public ResponseEntity<T> create(@RequestBody D dto) {
//        T entity = convertToEntity(dto);
//        T createdEntity = baseService.create(entity);  // Corrected from save to create
//        return ResponseEntity.ok(createdEntity);
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<T> update(@PathVariable Long id, @RequestBody D dto) {
//        T entity = convertToEntity(dto);
//        T updatedEntity = baseService.update(id, entity);
//        return ResponseEntity.ok(updatedEntity);
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> delete(@PathVariable Long id) {
//        baseService.deleteById(id);
//        return ResponseEntity.noContent().build();
//    }
//
//    protected abstract T convertToEntity(D dto);
//}
