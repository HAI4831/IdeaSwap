package nvh.run.ideaswap.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import nvh.run.ideaswap.data.dto.BannerDTO;
import nvh.run.ideaswap.data.dto.ManagerDTO;
import nvh.run.ideaswap.data.entity.Banners;
import nvh.run.ideaswap.data.entity.Managers;
import nvh.run.ideaswap.data.mapper.BannerMapper;
import nvh.run.ideaswap.data.mapper.ManagersMapper;
import nvh.run.ideaswap.data.repository.BannerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class BannerService {
    BannerRepository bannerRepository;
    ManagerService managerService;
    BannerMapper bannerMapper;
    ManagersMapper managersMapper;

    public BannerDTO createBanner(BannerDTO bannerDTO) {
        ManagerDTO managerDTO = managerService.getManagerById(String.valueOf(bannerDTO.getManagerID()));
        Managers manager = managersMapper.toEntity(managerDTO,null);
        Banners banners;
        try {
             banners = bannerRepository.save(Banners.builder()
                     .id(bannerDTO.getId())
                    .name(bannerDTO.getName())
                    .site(bannerDTO.getSite())
                    .imageUrl(bannerDTO.getImageUrl())
                    .manager(manager)
                    .build());
        } catch (Exception e) {
            throw new RuntimeException("Create Banner into db failed",e);
        }
        return bannerMapper.toDto(banners);
    }

    public List<BannerDTO> getAllBanners() {
        List<Banners> banners ;
        try {
            banners = bannerRepository.findAll();
        }
        catch (Exception e) {
            throw new RuntimeException("Get all banners failed",e);
        }
        return banners.stream().map(bannerMapper::toDto).toList();
    }

    public BannerDTO getBannerById(String id) {
        Banners banner;
        try {
            banner = bannerRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Banner not found with ID: " + id));
        } catch (Exception e) {
            throw new RuntimeException("Get banner by id failed",e);
        }
        ManagerDTO managerDTO = managerService.getManagerById(banner.getId());
        return BannerDTO.builder()
                .id(banner.getId())
                .name(banner.getName())
                .site(banner.getSite())
                .imageUrl(banner.getImageUrl())
                .managerID(banner.getManager().getId())
                .build();
    }

    public BannerDTO updateBanner(String id, BannerDTO bannerDTO) {
        BannerDTO existingBannerDTO= getBannerById(id);
        ManagerDTO managerDTO = managerService.getManagerById(existingBannerDTO.getManagerID());
        Managers manager = managersMapper.toEntity(managerDTO,null);
        Banners updatedBanner;
        try {
            updatedBanner = bannerRepository.save(bannerMapper.toEntity(existingBannerDTO,manager));
        } catch (Exception e) {
            throw new RuntimeException("Update banner failed",e);
        }
        return bannerMapper.toDto(updatedBanner);
    }

    public BannerDTO deleteBanner(String id) {
        BannerDTO bannerDTO = getBannerById(id);
        try {
            bannerRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Delete banner failed",e);
        }
        return bannerDTO;
    }
}

