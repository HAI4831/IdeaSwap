package nvh.run.ideaswap.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import nvh.run.ideaswap.data.entity.Categories;
import nvh.run.ideaswap.data.repository.CategoryRepository;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class CategoryService {
    CategoryRepository categoryRepository;
    public List<Categories> getAllCategories() {
        List<Categories> categoryList;
        try{
            categoryList =categoryRepository.findAll();
        }catch (Exception e){
            throw new RuntimeException("Retrieve List Categories failed",e);
        }
        return categoryList;
    }

    public Categories getCategoryById(ObjectId id) {
        Categories category;
        try {
             category= categoryRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Category not found"));
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while retrieving the category", e);
        }
        return category;
    }

    public Categories createCategory(Categories category) {
        Categories categorySaved;
        try {
            categorySaved=categoryRepository.save(
                    category
            );
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while creating the category", e);
        }
        return categorySaved;
    }

    public Categories updateCategory(ObjectId id, Categories category) {
        getCategoryById(id);
        Categories categoryUpdated;
        category.setId(id);
       try {
           categoryUpdated=categoryRepository.save(category);
       } catch (Exception e) {
           throw new RuntimeException("An error occurred while updating the category", e);
       }
       return categoryUpdated;
    }

    public Categories deleteCategory(ObjectId id) {
        Categories categories = getCategoryById(id);
        try {
            categoryRepository.deleteById(id);
        }catch (Exception e){
            throw new RuntimeException("An error occurred while deleting the category", e);
        }
        return categories;
    }

}
