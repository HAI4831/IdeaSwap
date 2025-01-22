package nvh.run.ideaswap.data.repository;

import nvh.run.ideaswap.data.entity.Reports;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ReportRepository extends MongoRepository<Reports, ObjectId> {
    List<Reports> findByStatus(String status);
}
