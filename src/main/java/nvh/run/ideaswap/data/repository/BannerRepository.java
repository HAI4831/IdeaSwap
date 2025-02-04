package nvh.run.ideaswap.data.repository;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import nvh.run.ideaswap.data.entity.Banners;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BannerRepository extends MongoRepository<Banners, String> {
    Banners findBannerBySite(@NotBlank(message = "URL không được để trống") @Size(max = 30, message = "Site không được quá 30 ký tự") String site);
}

