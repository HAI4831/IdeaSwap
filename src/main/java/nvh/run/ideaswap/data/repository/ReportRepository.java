package nvh.run.ideaswap.data.repository;

import nvh.run.ideaswap.data.entity.Reports;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ReportsRepository extends MongoRepository<Reports, String> {
    List<Reports> findByStatus(String status);
}
