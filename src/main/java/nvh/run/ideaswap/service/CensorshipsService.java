package nvh.run.ideaswap.service;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import nvh.run.ideaswap.data.dto.NotificationRequest;
import nvh.run.ideaswap.data.entity.*;
import nvh.run.ideaswap.data.repository.CensorshipsRepository;
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

    public List<Censorships> getAllCensorships() {
        List<Censorships> censorships;
        try {
            censorships = censorshipsRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Get all censorships failed",e);
        }
        return censorships;
    }

    public Censorships getCensorshipById(String id) {
        Censorships censorship;
        try {
            censorship = censorshipsRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Censorship not found"));
        } catch (Exception e) {
            throw new RuntimeException("Get censorship failed",e);
        }
        return censorship;
    }

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

    public Censorships updateCensorship(String id, Censorships censorship) {
        getCensorshipById(id);
        Censorships updatedCensorship;
        censorship.setId(id);
        try {
            updatedCensorship = censorshipsRepository.save(censorship);
        } catch (Exception e) {
            throw new RuntimeException("Update censorship failed",e);
        }
        Blogs blog = blogService.getBlogById(updatedCensorship.getContentID());
        String user;

        notificationService.createNotification(
                NotificationRequest.builder()
                        .description(updatedCensorship.getFeedback())
                        .userIDs(null)
                        .imageUrl(null)
                        .build()
        );
        return updatedCensorship;
    }

    public Censorships deleteCensorship(String id) {
        Censorships censorships = getCensorshipById(id);
        try {
            censorshipsRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Delete censorship failed",e);
        }
        return censorships;
    }

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
                    case "blog" -> userService.getUserById(((Blogs) content).getUserID().getId());
                    case "video" -> userService.getUserById(((Videos) content).getUserID().getId());
                    case "document" -> userService.getUserById(((Documents) content).getUserID().getId());
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


    public  Censorships getCensorshipByContentID(String contentID) {
        Censorships censorship=null;
        try {
            censorship = censorshipsRepository.findCensorshipsByContentID(contentID);
        } catch (Exception e) {
            throw new RuntimeException("censorship Find By ContentID failed",e);
        }
        return censorship;
    }
}
