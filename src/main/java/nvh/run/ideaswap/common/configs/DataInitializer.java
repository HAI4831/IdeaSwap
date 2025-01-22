package nvh.run.ideaswap.common.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import nvh.run.ideaswap.data.entity.*;
import nvh.run.ideaswap.data.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    ApplicationContext applicationContext;
    PasswordEncoder passwordEncoder;
    @Override
    public void run(String... args) throws Exception {
        // Xuất dữ liệu hiện tại thành file JSON
//        exportDataToFile();

        // Xóa tất cả dữ liệu trong cơ sở dữ liệu
//        clearAllData();

        // Tải lại dữ liệu từ file JSON
//        importDataFromFile();

        // Thêm dữ liệu mới nếu cần
        initializeSampleData();
    }

    private void exportDataToFile() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        Map<String, CrudRepository> repositories = applicationContext.getBeansOfType(CrudRepository.class);

        for (Map.Entry<String, CrudRepository> entry : repositories.entrySet()) {
            String repositoryName = entry.getKey();
            CrudRepository repository = entry.getValue();

            if (isFindAllSupported(repository)) {
                Iterable<?> entities = repository.findAll();
                File outputFile = new File(repositoryName + ".json");

                objectMapper.writeValue(outputFile, entities);
                System.out.println("Exported data for repository: " + repositoryName + " to " + outputFile.getName());
            }
        }
    }

    private void importDataFromFile() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        Map<String, CrudRepository> repositories = applicationContext.getBeansOfType(CrudRepository.class);

        for (Map.Entry<String, CrudRepository> entry : repositories.entrySet()) {
            String repositoryName = entry.getKey();
            CrudRepository repository = entry.getValue();

            File inputFile = new File(repositoryName + ".json");

            if (inputFile.exists() && isFindAllSupported(repository)) {
                Object[] entities = objectMapper.readValue(inputFile, Object[].class);
                for (Object entity : entities) {
                    repository.save(entity);
                }
                System.out.println("Imported data for repository: " + repositoryName + " from " + inputFile.getName());
            }
        }
    }

    private void clearAllData() {
        Map<String, CrudRepository> repositories = applicationContext.getBeansOfType(CrudRepository.class);

        for (CrudRepository repository : repositories.values()) {
            repository.deleteAll();
        }
        System.out.println("Cleared all data from all repositories.");
    }

    private void initializeSampleData() {
        clearAllData();
        //c0
        RoleRepository roleRepository = applicationContext.getBean(RoleRepository.class);
        roleRepository.save(
                Roles.builder()
                        .name("user")
                        .build()
        );
        Roles adminRole = roleRepository.save(
                Roles.builder()
                        .name("admin")
                        .build()
        );
        roleRepository.save(
                Roles.builder()
                        .name("manager")
                        .build()
        );
        roleRepository.save(
                Roles.builder()
                        .name("creator")
                        .build()
        );
        CategoryRepository categoryRepository = applicationContext.getBean(CategoryRepository.class);
        Categories categories = categoryRepository.save(
                Categories.builder()
                        .name("programing")
                        .description("discord fix bug java spring")
                        .build()
        );
        categoryRepository.save(
                Categories.builder()
                        .name("code web")
                        .description("discord fix bug reactks")
                        .build()
        );
        CensorshipsRepository censorshipsRepository = applicationContext.getBean(CensorshipsRepository.class);
        censorshipsRepository.save(
                Censorships.builder()
                        .contentID(null)
                        .status(Status.pending)
                        .feedback("feedback for censorship")
                        .build()
        );
        //c1
        IUserRepository userRepository = applicationContext.getBean(IUserRepository.class);
        Users user = userRepository.save(
                Users.builder()
                        .username("Nguyễn Văn Hải")
                        .email("nvhai227@gmail.com")
                        .password(passwordEncoder.encode("abCD@1234"))
                        .address("Ninh Bình")
                        .firstName("Nguyễn Văn")
                        .lastName("Hải")
                        .gender(Gender.male)
                        .phoneNumber("0123456789")
                        .rating(1000000000)
                        .description("I'm a java spring developer")
                        .roleID(adminRole.getId())
                        .birthday(LocalDateTime.parse("01/01/2003 00:00", DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")))
                        .id(null)
                        .version(2L)
                        .avatar(null)
                        .build()
        );
        ManagerRepository managerRepository = applicationContext.getBean(ManagerRepository.class);
        Managers manager = managerRepository.save(
                Managers.builder()
                        .username("nvhai227")
                        .address("nb")
                        .phoneNumber("0123456789")
                        .email("nvhai227@gmail.com")
                        .password(passwordEncoder.encode("abCD@1234"))
                        .gender(Gender.male)
                        .avatar(null)
                        .roleID(adminRole.getId())
                        .birthday(LocalDateTime.parse("01/01/2003 00:00", DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")))
                        .lastName("nv")
                        .firstName("hai")
                        .id(null)
                        .build()
        );
        BannerRepository bannerRepository = applicationContext.getBean(BannerRepository.class);
        bannerRepository.save(
                Banners.builder()
                        .site("nvhai227")
                        .id(null)
                        .managerID(manager.getId())
                        .createdDate(LocalDateTime.now())
                        .updatedDate(LocalDateTime.now())
                        .imageUrl("https://i.ibb.co/933333/nvhai227.png")
                        .name("nvhai227")
                        .build()
        );
        ICodeR iCodeR = applicationContext.getBean(ICodeR.class);
        iCodeR.save(
                Codes.builder()
                        .code(123)
                        .id(null)
                        .createdDate(LocalDateTime.now())
                        .updatedDate(LocalDateTime.now())
                        .codeExpiration(LocalDateTime.now().plusDays(1))
                        .user(user.getId())
                        .userEmail(user.getId())
                        .build()
        );
        CommentsRepository commentsRepository = applicationContext.getBean(CommentsRepository.class);
        Comments comments = commentsRepository.save(
                Comments.builder()
                        .referenceID(user.getId())
                        .content("content for comment")
                        .createdDate(LocalDateTime.now())
                        .updatedDate(LocalDateTime.now())
                        .id(null)
                        .parentCommentID(null)
                        .userID(user.getId())
                        .build()
        );
        ConversationsRepository conversationsRepository = applicationContext.getBean(ConversationsRepository.class);
        Conversations conversations = conversationsRepository.save(
                Conversations.builder()
                        .createdDate(LocalDateTime.now())
                        .updatedDate(LocalDateTime.now())
                        .id(null)
                        .members(List.of(user.getId()))
                        .wallpaperUrl("https://i.ibb.co/933333/nvhai227.png")
                        .build()
        );
        DocumentsRepository documentsRepository = applicationContext.getBean(DocumentsRepository.class);
        Documents documents = documentsRepository.save(
                Documents.builder()
                        .categoryID(categories.getId())
                        .countDownload(5)
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .id(null)
                        .description("I'm a java spring developer")
                        .fileUrl("https://i.ibb.co/933333/nvhai227.docx")
                        .score(200)
                        .build()
        );
        HeartsRepository   heartsRepository = applicationContext.getBean(HeartsRepository.class);
        Hearts hearts = heartsRepository.save(
                Hearts.builder()
                        .referenceID(user.getId())
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .id(null)
                        .userID(user.getId())
                        .build()
        );
        MessageRepository messageRepository = applicationContext.getBean(MessageRepository.class);
        Messages messages = messageRepository.save(
                Messages.builder()
                        .content("I'm a java spring developer")
                        .conversationID(conversations.getId())
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .id(null)
                        .fileUrl("https://i.ibb.co/933333/nvhai227.txt")
                        .messageParentID(null)
                        .conversationID(conversations.getId())
                        .senderID(user.getId())
                        .type(null)
                        .build()
        );
        NotificationRepository notificationRepository = applicationContext.getBean(NotificationRepository.class);
        Notifications notifications = notificationRepository.save(
                Notifications.builder()
                        .referenceID(user.getId())
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .id(null)
                        .isUnRead(false)
                        .userIDs(List.of(user.getId()))
                        .actorID(null)
                        .isModal(false)
                        .description("descript for notification")
                        .build()
        );
        ShareRepository shareRepository = applicationContext.getBean(ShareRepository.class);
        Shares shares = shareRepository.save(
                Shares.builder()
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .id(null)
                        .referenceID(user.getId())
                        .userID(user.getId())
                        .build()
        );
        //c2

        ReportRepository reportRepository = applicationContext.getBean(ReportRepository.class);
        Reports report = reportRepository.save(
                Reports.builder()
                        .type("type for report")
                        .content("content for report")
                        .referenceID(user.getId())
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .id(null)
                        .userID(user.getId())
                        .status(Status.pending)
                        .build()
        );
        FollowRepository followRepository = applicationContext.getBean(FollowRepository.class);
        Follows follows = followRepository.save(
                Follows.builder()
                        .followerID(user.getId())
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .id(null)
                        .userID(user.getId())
                        .build()
        );
        CoursesRepository coursesRepository = applicationContext.getBean(CoursesRepository.class);
        Courses courses = coursesRepository.save(
                Courses.builder()
                        .title("title for courses")
                        .categoryID(categories.getId())
                        .view(45)
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .id(null)
                        .description("desciption for course")
                        .imageUrl("https://i.ibb.co/933333/nvhai227.png")
                        .userID(user.getId())
                        .build()
        );
        VideoRepository videoRepository = applicationContext.getBean(VideoRepository.class);
        Videos videos = videoRepository.save(
                Videos.builder()
                        .title("title for video")
                        .courseID(courses.getId())
                        .videoUrl("video.mp4")
                        .view(123)
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .id(null)
                        .description("desciption for video")
                        .imageUrl("https://i.ibb.co/933333/nvhai227.png")
                        .userID(user.getId())
                        .build()
        );
        BlogRepository blogRepository = applicationContext.getBean(BlogRepository.class);
        Blogs blog = blogRepository.save(
                Blogs.builder()
                        .url("https://nvhai227.github.io/")
                        .categoryID(categories.getId())
                        .content("discord fix bug java spring")
                        .id(null)
                        .createdDate(LocalDateTime.now())
                        .updatedDate(LocalDateTime.now())
                        .userID(user.getId())
                        .build()
        );

        System.out.println("Sample data initialized.");
    }

    private boolean isFindAllSupported(CrudRepository repository) {
        return CrudRepository.class.isAssignableFrom(repository.getClass());
    }
}
