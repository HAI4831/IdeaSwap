package nvh.run.ideaswap.api.service.intf;

import nvh.run.ideaswap.data.dto.CategoryDTO;
import nvh.run.ideaswap.data.entity.Categories;

import java.util.List;

public interface ICategory {
    List<Categories> getAllCategories();
    Categories getCategoryById(String id);
    Categories createCategory(CategoryDTO categoryDTO);
   Categories updateCategory(String id, CategoryDTO categoryDTO);
    Categories deleteCategory(String id);

}
