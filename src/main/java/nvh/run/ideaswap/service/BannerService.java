package nvh.run.ideaswap.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import nvh.run.ideaswap.data.dto.BannerRequest;
import nvh.run.ideaswap.data.entity.Banners;
import nvh.run.ideaswap.data.entity.Managers;
import nvh.run.ideaswap.data.repository.BannerRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class BannerService {
    BannerRepository bannerRepository;
    ManagerService managerService;

    public List<Banners> getAllBanners() {
        List<Banners> banners ;
        try {
            banners = bannerRepository.findAll();
        }
        catch (Exception e) {
            throw new RuntimeException("Get all banners failed",e);
        }
        return banners;
    }

    public Banners getBannerById(String id) {
        Banners banner;
        try {
            banner = bannerRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Banner not found with ID: " + id));
        } catch (Exception e) {
            throw new RuntimeException("Get banner by id failed",e);
        }
        return banner;
    }

    public Banners createBanner(BannerRequest bannerRequest) {
        Managers manager = managerService.getManagerById(bannerRequest.getManagerID());
        Banners banner;
        try {
            banner = bannerRepository.save(
                    Banners.builder()
                            .id(bannerRequest.getId())
                            .managerID(manager)
                            .name(bannerRequest.getName())
                            .site(bannerRequest.getSite())
                            .imageUrl(bannerRequest.getImageUrl())
                            .createdDate(LocalDateTime.now())
                            .updatedDate(LocalDateTime.now())
                            .build()
            );
        } catch (Exception e) {
            throw new RuntimeException("Create Banner into db failed",e);
        }
        return banner;
    }

    public Banners updateBanner(String id, BannerRequest bannerRequest) {
        Managers manager = managerService.getManagerById(bannerRequest.getManagerID());
        Banners banner;
        try {
            banner = bannerRepository.save(
                    Banners.builder()
                            .id(bannerRequest.getId())
                            .managerID(manager)
                            .name(bannerRequest.getName())
                            .site(bannerRequest.getSite())
                            .imageUrl(bannerRequest.getImageUrl())
                            .createdDate(bannerRequest.getCreatedDate())
                            .updatedDate(LocalDateTime.now())
                            .build()
            );
        } catch (Exception e) {
            throw new RuntimeException("Update banner failed",e);
        }
        return banner;
    }

    public Banners deleteBanner(String id) {
        Banners banner = getBannerById(id);
        try {
            bannerRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Delete banner failed",e);
        }
        return banner;
    }
}

