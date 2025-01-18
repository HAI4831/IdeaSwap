//package nvh.run.ideaswap.api.service;
//
//import org.springframework.beans.BeanUtils;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//@Service
//public abstract class GenericService<T, ID> implements BaseService<T, ID> {
//    private final JpaRepository<T, ID> repository;
//
//    public GenericService(JpaRepository<T, ID> repository) {
//        this.repository = repository;
//    }
//
//    @Override
//    public T create(T dto) {
//        return repository.save(dto);
//    }
//
//    @Override
//    public T update(ID id, T dto) {
//        Optional<T> entity = repository.findById(id);
//        if (entity.isPresent()) {
//            BeanUtils.copyProperties(dto, entity.get());
//            return repository.save(entity.get());
//        }
//        throw new ResourceNotFoundException("Resource not found");
//    }
//
//    @Override
//    public void deleteById(ID id) {
//        repository.deleteById(id);
//    }
//
//    @Override
//    public T findById(ID id) {
//        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Resource not found"));
//    }
//
//    @Override
//    public List<T> findAll() {
//        return repository.findAll();
//    }
//}
