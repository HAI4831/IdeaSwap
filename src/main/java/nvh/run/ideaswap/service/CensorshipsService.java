package nvh.run.ideaswap.service;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import nvh.run.ideaswap.data.dto.NotificationRequest;
import nvh.run.ideaswap.data.entity.*;
import nvh.run.ideaswap.data.repository.CensorshipsRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class CensorshipsService {
    CensorshipsRepository censorshipsRepository;
    NotificationService notificationService;
    BlogService blogService;
    UserService userService;
    VideoService videoService;
    DocumentsService documentsService;
    //    @Cacheable(value="censorship")
    public List<Censorships> getAllCensorships() {
        List<Censorships> censorships;
        try {
            censorships = censorshipsRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Get all censorships failed",e);
        }
        return censorships;
    }
    @CachePut(value="censorship",key="#censorship.id",condition = "#censorship.id!=null")
    public Censorships updateCensorshipByContentID(@Valid Censorships censorship) {
        Censorships updatedCensorship = getCensorshipByContentID(censorship.getContentID());
        try {
            if (updatedCensorship == null) {
                throw new RuntimeException("Update censorship failed: Can't find censorship by contentID");
            }
            updatedCensorship = censorshipsRepository.save(censorship);
        } catch (Exception e) {
            throw new RuntimeException("Update censorship failed",e);
        }
        String contentID = updatedCensorship.getContentID();
        Map<String, Function<String, Object>> contentFetchers = Map.of(
                "blog", blogService::getBlogById,
                "video", videoService::getById,
                "document", documentsService::getDocumentById
        );

        String contentType = null;
        Object content = null;
        Users user = null;

        for (var entry : contentFetchers.entrySet()) {
            content = entry.getValue().apply(contentID);
            if (content != null) {
                contentType = entry.getKey();
                user = switch (contentType) {
                    case "blog" -> userService.getUserById(((Blogs) content).getUserID());
                    case "video" -> userService.getUserById(((Videos) content).getUserID());
                    case "document" -> userService.getUserById(((Documents) content).getUserID());
                    default -> null;
                };
                break;
            }
        }

        if (content != null && user != null) {
            String imageUrl = (content instanceof Blogs) ? ((Blogs) content).getUrl()
                    : (content instanceof Videos) ? ((Videos) content).getImageUrl()
                    : (content instanceof Documents) ? ((Documents) content).getImageUrl()
                    : null;

            notificationService.createNotification(
                    NotificationRequest.builder()
                            .description(updatedCensorship.getFeedback())
                            .imageUrl(imageUrl)
                            .userIDs(List.of(user.getId()))
                            .build()
            );
        }

        return updatedCensorship;
    }
    @Cacheable(value="censorship",key="#id",condition = "#id!=null")
    public Censorships getCensorshipById(String id) {
        Censorships censorship;
        try {
            censorship = censorshipsRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Censorship not found"));
        } catch (Exception e) {
            throw new RuntimeException("Get censorship id:"+id+" failed",e);
        }
        return censorship;
    }
    @CachePut(value="censorship",key="#id",condition = "#id!=null")
    public Censorships updateCensorship(String id, Censorships censorship) {
        getCensorshipById(id);
        Censorships updatedCensorship;
        censorship.setId(id);
        try {
            updatedCensorship = censorshipsRepository.save(censorship);
        } catch (Exception e) {
            throw new RuntimeException("Update censorship with id:"+id+" failed",e);
        }
//        Blogs blog = blogService.getBlogById(updatedCensorship.getContentID());
//        String user;

//        notificationService.createNotification(
//                NotificationRequest.builder()
//                        .description(updatedCensorship.getFeedback())
//                        .userIDs(null)
//                        .imageUrl(null)
//                        .build()
//        );
        return updatedCensorship;
    }
    @Cacheable(value="censorship",key="#contentID",condition = "#contentID!=null")
    public  Censorships getCensorshipByContentID(String contentID) {
        Censorships censorship=null;
        try {
            censorship = censorshipsRepository.findCensorshipsByContentID(contentID);
        } catch (Exception e) {
            throw new RuntimeException("censorship Find By ContentID failed",e);
        }
        return censorship;
    }
//    @Cacheable(value = "censorships",key = "'page:' + #page + ':size:' + #size")
    public Page<Censorships> getAllCensorships(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Censorships> censorshipsPage;
        try {
            censorshipsPage = censorshipsRepository.findAll(pageable);
        } catch (Exception e) {
            throw new RuntimeException("Get all censorships failed",e);
        }
        return censorshipsPage;
    }
    @CachePut(value="censorship",key="#censorship.id",condition = "#censorship.id!=null")
    public Censorships createCensorship(Censorships censorship) {
        try {
            censorship = censorshipsRepository.save(censorship);
        } catch (Exception e) {
            throw new RuntimeException("Create censorship failed",e);
        }
//        notificationService.createNotification(
//                NotificationRequest.builder()
//                        .id(null)
//                        .description(censorship.getFeedback())
////                        .imageUrl()
//                        .build()
//        );
        return censorship;
    }
    @CacheEvict(value="censorship",key="#id")
    public Censorships deleteCensorship(String id) {
        Censorships censorships = getCensorshipById(id);
        try {
            censorshipsRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Delete censorship failed",e);
        }
        return censorships;
    }
}
