package nvh.run.ideaswap.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import nvh.run.ideaswap.data.dto.ShareRequest;
import nvh.run.ideaswap.data.entity.Share;
import nvh.run.ideaswap.data.entity.User;
import nvh.run.ideaswap.data.repository.ShareRepository;
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
import java.util.Optional;

@Service
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ShareService {
    ShareRepository shareRepository;
    UserService userService;

//    @Cacheable(value = "shares",key = "'page:' + #page + ':size:' + #size")
    public Page<Share> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Share> sharesList;
        try {
            sharesList = shareRepository.findAll(pageable);
        } catch (Exception e) {
            throw new RuntimeException("Find all shares failed", e);
        }
        return sharesList;
    }
//    @Cacheable(value="shares")
    public List<Share> getAll() {
        List<Share> shareList;
        try {
            shareList = shareRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Find all shares failed", e);
        }
        return shareList;
    }

    @Cacheable(value="share",key="#id",condition = "#id!=null")
    public Share getById(String id) {
        Share share;
        try {
            share = shareRepository.findById(id).orElse(null);
        } catch (Exception e) {
            throw new RuntimeException("Find share failed with id: " + id, e);
        }
        return share;
    }

    @CachePut(value="share",key="#shareRequest.id",condition = "#shareRequest.id!=null")
    public Share save(ShareRequest shareRequest) {
        Share share;
        User user = userService.getUserById(shareRequest.getUserID());
        try {
            share = shareRepository.save(
                    Share.builder()
                            .userID(user.getId())
                            .referenceID(shareRequest.getReferenceID())
                            .createdAt(LocalDateTime.now())
                            .updatedAt(LocalDateTime.now())
                            .id(shareRequest.getId())
                            .build()
            );
        } catch (Exception e) {
            throw new RuntimeException("Save share failed", e);
        }
        return share;
    }

    @Cacheable(value = "share", key = "#id", condition = "#id != null")
    public Share update(String id, ShareRequest shareRequest) {
        // Lấy Share hiện tại từ DB
        Share existingShare = shareRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Share not found with id: " + id));

        // Lấy User nếu có userID mới, nếu null thì giữ nguyên
        String userId = Optional.ofNullable(shareRequest.getUserID())
                .map(userService::getUserById)
                .map(User::getId)
                .orElse(existingShare.getUserID());

        // Cập nhật các trường nếu có giá trị mới, giữ nguyên nếu null
        Optional.ofNullable(shareRequest.getReferenceID()).ifPresent(existingShare::setReferenceID);
        existingShare.setUserID(userId);
        existingShare.setUpdatedAt(LocalDateTime.now()); // Chỉ cập nhật thời gian sửa đổi

        return shareRepository.save(existingShare);
    }


    @CacheEvict(value="share",key="#id",condition = "#id!=null")
    public Share delete(String id) {
        Share share = getById(id);
        try {
            shareRepository.delete(share);
        } catch (Exception e) {
            throw new RuntimeException("Delete share failed", e);
        }
        return share;
    }
}