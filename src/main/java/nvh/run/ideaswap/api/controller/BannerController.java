package nvh.run.ideaswap.api.controller;

import nvh.run.ideaswap.data.entity.Banners;
import nvh.run.ideaswap.api.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/banners")
public class BannerController {

    @Autowired
    private BannerService bannerService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Banners> createBanner(@RequestBody Banners banner) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bannerService.createBanner(banner));
    }

    @GetMapping
    public ResponseEntity<List<Banners>> getAllBanners() {
        return ResponseEntity.ok(bannerService.getAllBanners());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Banners> getBannerById(@PathVariable String id) {
        return ResponseEntity.ok(bannerService.getBannerById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Banners> updateBanner(@PathVariable String id, @RequestBody Banners banner) {
        return ResponseEntity.ok(bannerService.updateBanner(id, banner));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBanner(@PathVariable String id) {
        bannerService.deleteBanner(id);
    }
}

