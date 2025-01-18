package nvh.run.ideaswap.api.service;

import nvh.run.ideaswap.data.entity.Banners;
import nvh.run.ideaswap.data.repository.BannerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BannerService {

    @Autowired
    private BannerRepository bannerRepository;

    public Banners createBanner(Banners banner) {
        return bannerRepository.save(banner);
    }

    public List<Banners> getAllBanners() {
        return bannerRepository.findAll();
    }

    public Banners getBannerById(String id) {
        return bannerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Banner not found with ID: " + id));
    }

    public Banners updateBanner(String id, Banners updatedBanner) {
        Banners existingBanner = getBannerById(id);
        existingBanner.setName(updatedBanner.getName());
        existingBanner.setSite(updatedBanner.getSite());
        existingBanner.setImageUrl(updatedBanner.getImageUrl());
        existingBanner.setManager(updatedBanner.getManager());
        return bannerRepository.save(existingBanner);
    }

    public void deleteBanner(String id) {
        Banners banner = getBannerById(id);
        bannerRepository.delete(banner);
    }
}

