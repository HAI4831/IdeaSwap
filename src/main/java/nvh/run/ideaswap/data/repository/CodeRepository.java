package nvh.run.ideaswap.data.repository;

import jakarta.validation.constraints.NotBlank;
import nvh.run.ideaswap.data.entity.Code;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.Optional;

public interface CodeRepository extends MongoRepository<Code,String> {
    Code findCodesByCode(@NotBlank(message = "Code cannot be empty") int code);
    @Query("{ 'code' : ?0, 'userEmail' : ?1, 'codeExpiration' : { $gt: ?2 } }")
    Optional<Code> findValidCode(@Param("code") int code, @Param("userEmail") String userEmail, @Param("now") Date now);
//    @Query("SELECT c FROM Code c WHERE c.code = :code AND c.userEmail = :userEmail AND c.codeExpiration > :now")
//    Optional<Code> findValidCode(@Param("code") String code, @Param("userEmail") String userEmail, @Param("now") LocalDateTime now);
}
