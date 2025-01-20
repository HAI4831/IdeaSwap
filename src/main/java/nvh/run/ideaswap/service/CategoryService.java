package nvh.run.ideaswap.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import nvh.run.ideaswap.data.dto.CategoryDTO;
import nvh.run.ideaswap.data.entity.Categories;
import nvh.run.ideaswap.data.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class CategoryService {
    CategoryRepository categoryRepository;
    public List<CategoryDTO> getAllCategories() {
        List<CategoryDTO> categoryDTOList;
        try{
            categoryDTOList =categoryRepository.findAll().stream().map(category -> CategoryDTO.builder().build()).toList();
        }catch (Exception e){
            throw new RuntimeException("Retrieve List Categories failed",e);
        }
        return categoryDTOList;
    }

    public CategoryDTO getCategoryById(String id) {
        Categories category;
        try {
             category= categoryRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Category not found"));
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while retrieving the category", e);
        }
        return CategoryDTO.builder().build();
    }

    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Categories categorySaved;
        try {
            categorySaved=categoryRepository.save(
                    Categories.builder()
                            .name(categoryDTO.getName())
                            .description(categoryDTO.getDescription())
                            .build()
            );
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while creating the category", e);
        }
        return CategoryDTO.builder().build();
    }

    public CategoryDTO updateCategory(String id, CategoryDTO categoryDTO) {
        getCategoryById(id);
        Categories categoryUpdated;
       try {
           categoryUpdated=categoryRepository.save(
                   Categories.builder()
                           .id(id)
                           .name(categoryDTO.getName())
                           .description(categoryDTO.getDescription())
                           .build()
           );
       } catch (Exception e) {
           throw new RuntimeException("An error occurred while updating the category", e);
       }
       return CategoryDTO.builder().build();
    }

    public CategoryDTO deleteCategory(String id) {
        Categories categories = getCategoryById(id);
//        ResponseEntity<Object> response = getCategoryById(id);
//        Map<String, Object> responseBody =  new HashMap<>((Map<String, Object>) response.getBody());
//        responseBody.put("message", "Delete Category successfully");

        try {
            categoryRepository.deleteById(id);
        }catch (Exception e){
            throw new RuntimeException("An error occurred while deleting the category", e);
        }
        return CategoryDTO.builder().build();
    }

}
