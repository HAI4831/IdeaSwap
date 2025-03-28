package nvh.run.ideaswap.data.repository;

import nvh.run.ideaswap.data.entity.Code;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ICodeR extends MongoRepository<Code,String> {
}
