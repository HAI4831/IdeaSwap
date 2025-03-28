package nvh.run.ideaswap.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import nvh.run.ideaswap.data.dto.BannerRequest;
import nvh.run.ideaswap.data.entity.Banner;
import nvh.run.ideaswap.data.entity.Manager;
import nvh.run.ideaswap.data.repository.BannerRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Transactional
public class BannerService {
    BannerRepository bannerRepository;
    ManagerService managerService;
    CloudinaryService cloudinaryService;

//    @Cacheable(value = "banners", key = "'page:' + #page + ':size:' + #size", condition = "#page != null && #size!=null")
    public Page<Banner> getAllBanners(int page, int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            return bannerRepository.findAll(pageable);
        } catch (Exception e) {
            throw new RuntimeException("Get all banners failed", e);
        }
    }
//    @Cacheable(value = "banners")
    public List<Banner> getAllBanners() {
        List<Banner> banners ;
        try {
            banners = bannerRepository.findAll();
        }
        catch (Exception e) {
            throw new RuntimeException("Get all banners failed",e);
        }
        return banners;
    }

    @Cacheable(value = "banner", key = "#id")
    public Banner getBannerById(String id) {
        Banner banner;
        try {
            banner = bannerRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Banner not found with ID: " + id));
        } catch (Exception e) {
            throw new RuntimeException("Get banner by id failed",e);
        }
        return banner;
    }
    @Cacheable(value = "banner", key = "#site")
    public Banner getBannerBySite(String site) {
        Banner banner;
        try {
            banner = bannerRepository.findBannerBySite(site);
        } catch (Exception e) {
            throw new RuntimeException("Get banner by site failed",e);
        }
        return banner;
    }
    @CachePut(value = "banner", key = "#bannerRequest.id")
    public Banner createBanner(BannerRequest bannerRequest) {
        Manager manager = managerService.getManagerById(bannerRequest.getManagerID());
        Banner banner = getBannerBySite(bannerRequest.getSite());
        String imageUrl = cloudinaryService.uploadImage(bannerRequest.getImageBase64(),null,"banner");
        try {
            if(imageUrl.isEmpty()) {
                throw new RuntimeException("Image url upload failed");
            }
            if(banner != null) {
                throw new RuntimeException("Banner already exists");
            }
            banner = bannerRepository.save(
                    Banner.builder()
                            .id(bannerRequest.getId())
                            .managerID(manager.getId())
                            .name(bannerRequest.getName())
                            .site(bannerRequest.getSite())
                            .imageUrl(imageUrl)
                            .createdDate(LocalDateTime.now())
                            .updatedDate(LocalDateTime.now())
                            .build()
            );
        } catch (Exception e) {
            throw new RuntimeException("Create Banner into db failed",e);
        }
        return banner;
    }

    @CachePut(value = "banner", key = "#id")
    public Banner updateBanner(String id, BannerRequest bannerRequest) {
        Manager manager = managerService.getManagerById(bannerRequest.getManagerID());
        Banner banner = getBannerById(id);
        String imageUrl = cloudinaryService.uploadImage(bannerRequest.getImageBase64(),null,"banner");
        try {
            if(imageUrl == null){
                throw new RuntimeException("Banner image upload failed");
            }
            if(banner == null) {
                throw new RuntimeException("Banner cannot be found");
            }
            banner = bannerRepository.save(
                    Banner.builder()
                            .id(bannerRequest.getId())
                            .managerID(manager.getId())
                            .name(bannerRequest.getName())
                            .site(bannerRequest.getSite())
                            .imageUrl(imageUrl)
                            .createdDate(bannerRequest.getCreatedDate())
                            .updatedDate(LocalDateTime.now())
                            .build()
            );
        } catch (Exception e) {
            throw new RuntimeException("Update banner failed",e);
        }
        return banner;
    }

    @CacheEvict(value = "banner", key = "#id")
//    @CacheEvict(value = "banners", allEntries = true)
    public Banner deleteBanner(String id) {
        Banner banner = getBannerById(id);
        try {
            String result = cloudinaryService.deleteImage(banner.getImageUrl(),null);
            if(result == null) {
                throw new RuntimeException("Delete image for banner failed");
            }
            bannerRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Delete banner failed",e);
        }
        return banner;
    }
}

