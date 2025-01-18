package nvh.run.ideaswap.api.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import nvh.run.ideaswap.api.service.intf.ICategory;
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
public class CategoryService implements ICategory {
    CategoryRepository categoryRepository;
    @Override
    public List<Categories> getAllCategories() {
        List<Categories> categories;
        try{
            categories=categoryRepository.findAll();
        }catch (Exception e){
            throw new RuntimeException("Retrieve List Categories failed",e);
//            return ResponseEntity.status(500).body(
//                    Map.of(
//                            "success",false,
//                            "message","Retrieve List Categories failed",
//                            "error",e.getMessage()
//                    )
//            );
        }
        return categories;
//        return ResponseEntity.status(200).body(
//                Map.of(
//                        "succes",true,
//                        "message", "Retrieve List Categories successfully",
//                        "categories",categories
//                )
//        );
    }

    @Override
    public Categories getCategoryById(String id) {
        Categories category;
        try {
             category= categoryRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Category not found"));
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while retrieving the category", e);
        }
        return category;
//        return ResponseEntity.status(200).body(
//                Map.of(
//                        "success",true,
//                        "message","Retrieve Category By ID successfully",
//                        "category",category
//                )
//        );
    }

    @Override
    public Categories createCategory(CategoryDTO categoryDTO) {
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
        return categorySaved;
//        return ResponseEntity.status(201).body(
//                Map.of(
//                        "success",true,
//                        "message","Retrieve Category By ID successfully",
//                        "category",categorySaved
//                )
//        );
    }

    @Override
    public Categories updateCategory(String id, CategoryDTO categoryDTO) {
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
       return categoryUpdated;
//        return ResponseEntity.status(200).body(
//                Map.of(
//                        "success",true,
//                        "message","Update Category successfully",
//                        "category",categoryUpdated
//                )
//        );
    }

    @Override
    public Categories deleteCategory(String id) {
        Categories categories = getCategoryById(id);
//        ResponseEntity<Object> response = getCategoryById(id);
//        Map<String, Object> responseBody =  new HashMap<>((Map<String, Object>) response.getBody());
//        responseBody.put("message", "Delete Category successfully");

        try {
            categoryRepository.deleteById(id);
        }catch (Exception e){
            throw new RuntimeException("An error occurred while deleting the category", e);
        }
        return categories;
//        return ResponseEntity.status(200).body(
//                responseBody
//        );
    }

}
