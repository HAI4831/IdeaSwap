package nvh.run.ideaswap.data.repository;

import nvh.run.ideaswap.data.entity.Censorships;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CensorshipsRepository extends MongoRepository<Censorships, String> {
}
