package nvh.run.ideaswap.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import nvh.run.ideaswap.data.entity.Category;
import nvh.run.ideaswap.data.repository.CategoryRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class CategoryService {
    CategoryRepository categoryRepository;

//    @Cacheable(value = "categories",key = "'page:' + #page + ':size:' + #size")
    public Page<Category> getAllCategories(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Category> categoryPage;
        try{
            categoryPage =categoryRepository.findAll(pageable);
        }catch (Exception e){
            throw new RuntimeException("Retrieve List Categories failed",e);
        }
        return categoryPage;
    }

//    @Cacheable(value = "categories")
    public List<Category> getAllCategories() {
        List<Category> categoryList;
        try{
            categoryList =categoryRepository.findAll();
        }catch (Exception e){
            throw new RuntimeException("Retrieve List Categories failed",e);
        }
        return categoryList;
    }

    @Cacheable(value = "category")
    public Category getCategoryById(String id) {
        Category category;
        try {
             category= categoryRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Category not found"));
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while retrieving the category", e);
        }
        return category;
    }

    @CachePut(value = "category", key = "#category.id")
    public Category createCategory(Category category) {
        try {
            category=categoryRepository.save(category);
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while creating the category", e);
        }
        return category;
    }

    @CachePut(value = "category", key = "#id", condition = "#id!=null")
    public Category updateCategory(String id, Category category) {
        // Lấy danh mục hiện tại từ database
        Category existingCategory = getCategoryById(id);
        if (existingCategory == null) {
            throw new RuntimeException("Category not found");
        }

        try {
            // Chỉ cập nhật các trường có giá trị mới, giữ nguyên nếu null
            existingCategory.setName(Optional.ofNullable(category.getName()).orElse(existingCategory.getName()));
            existingCategory.setDescription(Optional.ofNullable(category.getDescription()).orElse(existingCategory.getDescription()));
            existingCategory.setUpdatedAt(LocalDateTime.now()); // Cập nhật thời gian cập nhật

            // Lưu lại category đã cập nhật
            return categoryRepository.save(existingCategory);
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while updating the category", e);
        }
    }


    @CacheEvict(value = "category", key = "#id",condition = "#id!=null")
    public Category deleteCategory(String id) {
        Category category = getCategoryById(id);
        try {
            categoryRepository.deleteById(id);
        }catch (Exception e){
            throw new RuntimeException("An error occurred while deleting the category", e);
        }
        return category;
    }

}
