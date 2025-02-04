package nvh.run.ideaswap.api.controller;

import nvh.run.ideaswap.data.dto.BannerRequest;
import nvh.run.ideaswap.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/banner")
public class BannerController {

    @Autowired
    private BannerService bannerService;

    @GetMapping
    public ResponseEntity<Object> getAllBanners() {
        return  ResponseEntity.status(201).body(
                Map.of(
                        "success", true,
                        "message", "Retrieve List Banners successfully",
                        "banners",bannerService.getAllBanners()
                )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getBannerById(@PathVariable String id) {
        return ResponseEntity.status(201).body(
                Map.of(
                        "success", true,
                        "message", "Retrieve Banner By ID successfully",
                        "banner",bannerService.getBannerById(id)
                )
        );
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> createBanner(@ModelAttribute BannerRequest bannerRequest) {
        if (bannerRequest.getImageBase64() == null || bannerRequest.getImageBase64().isEmpty()) {
            return ResponseEntity.badRequest().body("Ảnh không được để trống");
        }
        return ResponseEntity.status(201).body(
                Map.of(
                        "success", true,
                        "message","Create Banner successfully",
                        "banner",bannerService.createBanner(bannerRequest)
                )
        );
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateBanner(@PathVariable String id, @ModelAttribute BannerRequest bannerRequest) {
        return ResponseEntity.status(201).body(
                Map.of(
                        "success", true,
                        "message", "Update Banner successfully",
                        "banner", bannerService.updateBanner(id,bannerRequest)
                )
        );
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Object> deleteBanner(@PathVariable String id) {
        return ResponseEntity.status(201).body(
                Map.of(
                        "success", true,
                        "message" ,"Delete Banner successfully",
                        "banner", bannerService.deleteBanner(id)
                )
        );
    }
}

