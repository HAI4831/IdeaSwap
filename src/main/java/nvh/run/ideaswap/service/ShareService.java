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

    @Cacheable(value="share",key="#id",condition = "#id!=null")
    public Share update(String id, ShareRequest shareRequest) {
        Share updatedShare;
        User user = userService.getUserById(shareRequest.getUserID());
        Share share = getById(id);
        try {
            updatedShare = shareRepository.save(
                    Share.builder()
                            .userID(user.getId())
                            .referenceID(shareRequest.getReferenceID())
                            .createdAt(share.getCreatedAt())
                            .updatedAt(LocalDateTime.now())
                            .id(share.getId())
                            .build()
            );
        } catch (Exception e) {
            throw new RuntimeException("Update share failed", e);
        }
        return updatedShare;
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