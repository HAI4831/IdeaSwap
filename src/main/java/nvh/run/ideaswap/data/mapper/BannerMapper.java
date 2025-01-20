package nvh.run.ideaswap.data.mapper;

import nvh.run.ideaswap.data.dto.BannerDTO;
import nvh.run.ideaswap.data.entity.Banners;
import nvh.run.ideaswap.data.entity.Managers;
import org.springframework.stereotype.Component;

@Component
public class BannerMapper {

    public BannerDTO toDto(Banners banner) {
        if (banner == null) {
            return null;
        }

        return BannerDTO.builder()
                .managerID(banner.getManager() != null ? banner.getManager().getId() : null)
                .name(banner.getName())
                .site(banner.getSite())
                .imageUrl(banner.getImageUrl())
                .build();
    }

    public Banners toEntity(BannerDTO bannerDTO, Managers manager) {
        if (bannerDTO == null) {
            return null;
        }

        return Banners.builder()
                .manager(manager) // Tham chiếu đến entity Managers
                .name(bannerDTO.getName())
                .site(bannerDTO.getSite())
                .imageUrl(bannerDTO.getImageUrl())
                .build();
    }
}
