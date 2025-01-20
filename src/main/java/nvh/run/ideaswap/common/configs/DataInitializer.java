package nvh.run.ideaswap.common.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.RequiredArgsConstructor;
import nvh.run.ideaswap.data.entity.Categories;
import nvh.run.ideaswap.data.repository.CategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final ApplicationContext applicationContext;

    @Override
    public void run(String... args) throws Exception {
        // Xuất dữ liệu hiện tại thành file JSON
        exportDataToFile();

        // Xóa tất cả dữ liệu trong cơ sở dữ liệu
//        clearAllData();

        // Tải lại dữ liệu từ file JSON
        importDataFromFile();

        // Thêm dữ liệu mới nếu cần
        initializeSampleData();
    }

    private void exportDataToFile() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        Map<String, CrudRepository> repositories = applicationContext.getBeansOfType(CrudRepository.class);

        for (Map.Entry<String, CrudRepository> entry : repositories.entrySet()) {
            String repositoryName = entry.getKey();
            CrudRepository repository = entry.getValue();

            if (isFindAllSupported(repository)) {
                Iterable<?> entities = repository.findAll();
                File outputFile = new File(repositoryName + ".json");

                objectMapper.writeValue(outputFile, entities);
                System.out.println("Exported data for repository: " + repositoryName + " to " + outputFile.getName());
            }
        }
    }

    private void importDataFromFile() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        Map<String, CrudRepository> repositories = applicationContext.getBeansOfType(CrudRepository.class);

        for (Map.Entry<String, CrudRepository> entry : repositories.entrySet()) {
            String repositoryName = entry.getKey();
            CrudRepository repository = entry.getValue();

            File inputFile = new File(repositoryName + ".json");

            if (inputFile.exists() && isFindAllSupported(repository)) {
                Object[] entities = objectMapper.readValue(inputFile, Object[].class);
                for (Object entity : entities) {
                    repository.save(entity);
                }
                System.out.println("Imported data for repository: " + repositoryName + " from " + inputFile.getName());
            }
        }
    }

    private void clearAllData() {
        Map<String, CrudRepository> repositories = applicationContext.getBeansOfType(CrudRepository.class);

        for (CrudRepository repository : repositories.values()) {
            repository.deleteAll();
        }
        System.out.println("Cleared all data from all repositories.");
    }

    private void initializeSampleData() {
        // Ví dụ thêm dữ liệu mới
        CategoryRepository categoryRepository = applicationContext.getBean(CategoryRepository.class);

        Categories category = categoryRepository.save(
                Categories.builder()
                        .name("Book")
                        .build()
        );

        System.out.println("Sample data initialized.");
    }

    private boolean isFindAllSupported(CrudRepository repository) {
        return CrudRepository.class.isAssignableFrom(repository.getClass());
    }
}
