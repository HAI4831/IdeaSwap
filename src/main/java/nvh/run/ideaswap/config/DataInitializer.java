package nvh.run.ideaswap.config;

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
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
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
//        initializeSampleDataIfNotExist();
//        initializeSampleData();
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

//    private void initializeSampleDataIfNotExist() {
//        RoleRepository roleRepository = applicationContext.getBean(RoleRepository.class);
//        Role userRole = roleRepository.save(Role.builder()
//                .id("6799978226692b76e021501c")
//                .name("user")
//                .createdAt(LocalDateTime.now())
//                .updatedAt(LocalDateTime.now())
//                .build());
////        Role userRole = roleRepository.findById("6799978226692b76e021501c")
////                .orElseGet(() -> roleRepository.save(Role.builder()
////                        .id("6799978226692b76e021501c")
////                        .name("user")
////                        .createdAt(LocalDateTime.now())
////                        .updatedAt(LocalDateTime.now())
////                        .build()));
//
//        Role creatorRole = roleRepository.findById("6799980fc73db076c26e65fc")
//                .orElseGet(() -> roleRepository.save(Role.builder()
//                        .id("6799980fc73db076c26e65fc")
//                        .name("creator")
//                        .createdAt(LocalDateTime.now())
//                        .updatedAt(LocalDateTime.now())
//                        .build()));
//
//        Role managerRole = roleRepository.findById("67999800c73db076c26e65fb")
//                .orElseGet(() -> roleRepository.save(Role.builder()
//                        .id("67999800c73db076c26e65fb")
//                        .name("manager")
//                        .createdAt(LocalDateTime.now())
//                        .updatedAt(LocalDateTime.now())
//                        .build()));
//        CategoryRepository categoryRepository = applicationContext.getBean(CategoryRepository.class);
//        Category category = categoryRepository.save(
//                Category.builder()
//                        .id("67999819c73db076c26e6610")
//                        .name("programing")
//                        .description("discord fix bug java spring")
//                        .createdDate(LocalDateTime.now())
//                        .updatedDate(LocalDateTime.now())
//                        .build()
//        );
//        categoryRepository.save(
//                Category.builder()
//                        .id("6799985d57bcd648ae781355")
//                        .name("code web")
//                        .description("discord fix bug reactks")
//                        .createdDate(LocalDateTime.now())
//                        .updatedDate(LocalDateTime.now())
//                        .build()
//        );
//        //c1
//        IUserRepository userRepository = applicationContext.getBean(IUserRepository.class);
//        User user = userRepository.findById("6799988457bcd648ae78136a").orElseGet(() ->
//                userRepository.save(
//                User.builder()
//                        .id("6799988457bcd648ae78136a")
//                        .username("user")
//                        .roleID(userRole.getId())
//                        .email("user@gmail.com")
//                        .password(passwordEncoder.encode("abCD@1234"))
//                        .address("Ninh Bình")
//                        .firstName("Nguyễn Văn")
//                        .lastName("Hải")
//                        .gender(Gender.Male)
//                        .phoneNumber("0123456789")
//                        .rating(1000000000)
//                        .description("I'm a java spring developer")
//                        .birthday(LocalDate.parse("01/01/2003", DateTimeFormatter.ofPattern("dd/MM/yyyy")))
//                        .version(2L)
//                        .avatar("https://antimatter.vn/wp-content/uploads/2022/11/anh-avatar-trang-fb-mac-dinh.jpg")
//                        .createdAt(LocalDateTime.now())
//                        .updatedAt(LocalDateTime.now())
//                        .build()
//        ));
//        User creator = userRepository.findById("6799988457bcd648ae78132a").orElseGet(() ->
//                userRepository.save(
//                User.builder()
//                        .id("6799988457bcd648ae78132a")
//                        .username("creator")
//                        .roleID(creatorRole.getId())
//                        .email("creator@gmail.com")
//                        .password(passwordEncoder.encode("abCD@1234"))
//                        .address("Ninh Bình")
//                        .firstName("Nguyễn Văn")
//                        .lastName("Hải")
//                        .gender(Gender.Male)
//                        .phoneNumber("0123456789")
//                        .rating(1000000000)
//                        .description("I'm a java spring developer")
//                        .birthday(LocalDate.parse("01/01/2003", DateTimeFormatter.ofPattern("dd/MM/yyyy")))
//                        .version(2L)
//                        .avatar("https://antimatter.vn/wp-content/uploads/2022/11/anh-avatar-trang-fb-mac-dinh.jpg")
//                        .createdAt(LocalDateTime.now())
//                        .updatedAt(LocalDateTime.now())
//                        .build()
//        ));
//
//        ManagerRepository managerRepository = applicationContext.getBean(ManagerRepository.class);
//        Manager manager = managerRepository.findById("679998b457bcd648ae781385").orElseGet(() ->
//                managerRepository.save(
//                Manager.builder()
//                        .id("679998b457bcd648ae781385")
//                        .username("manager")
//                        .roleID(managerRole.getId())
//                        .address("nb")
//                        .phoneNumber("0123456789")
//                        .email("manager@gmail.com")
//                        .password(passwordEncoder.encode("abCD@1234"))
//                        .gender(Gender.Male)
//                        .avatar("https://antimatter.vn/wp-content/uploads/2022/11/anh-avatar-trang-fb-mac-dinh.jpg")
//                        .birthday(LocalDate.parse("01/01/2003",DateTimeFormatter.ofPattern("dd/MM/yyyy")))
//                        .lastName("nv")
//                        .firstName("hai")
//                        .createdAt(LocalDateTime.now())
//                        .updatedAt(LocalDateTime.now())
//                        .build()
//        ));
//        BannerRepository bannerRepository = applicationContext.getBean(BannerRepository.class);
//        bannerRepository.findById("679998c657bcd648ae781386").orElseGet(() ->
//        bannerRepository.save(
//                Banner.builder()
//                        .id("679998c657bcd648ae781386")
//                        .site("nvhai227")
//                        .managerID(manager.getId())
//                        .createdDate(LocalDateTime.now())
//                        .updatedDate(LocalDateTime.now())
//                        .imageUrl("https://i.ibb.co/933333/nvhai227.png")
//                        .name("nvhai227")
//                        .createdDate(LocalDateTime.now())
//                        .updatedDate(LocalDateTime.now())
//                        .build()
//        ));
//        ICodeR iCodeR = applicationContext.getBean(ICodeR.class);
//        iCodeR.findById("679998d657bcd648ae78138a").orElseGet(() ->
//        iCodeR.save(
//                Code.builder()
//                        .code(123)
//                        .id("679998d657bcd648ae78138a")
//                        .createdDate(LocalDateTime.now())
//                        .updatedDate(LocalDateTime.now())
//                        .codeExpiration(Date.from(Instant.now().plusSeconds(3600)))
//                        .userID(user.getId())
//                        .userEmail(user.getEmail())
//                        .createdDate(LocalDateTime.now())
//                        .updatedDate(LocalDateTime.now())
//                        .build()
//        ));
//
//
//        CommentRepository commentRepository = applicationContext.getBean(CommentRepository.class);
//        Comment comment =commentRepository.findById("679998d657bcd648ae78138a").orElseGet(() ->
//                commentRepository.save(
//                Comment.builder()
//                        .referenceID("referenceID")
//                        .content("content for comment")
//                        .createdDate(LocalDateTime.now())
//                        .updatedDate(LocalDateTime.now())
//                        .id("679998e757bcd648ae78139d")
//                        .parentCommentID(null)
//                        .userID(user.getId())
//                        .createdDate(LocalDateTime.now())
//                        .updatedDate(LocalDateTime.now())
//                        .build()
//        ));
//        ConversationRepository conversationRepository = applicationContext.getBean(ConversationRepository.class);
//        Conversation conversation =conversationRepository.findById("679998d657bcd648ae78138a").orElseGet(() ->
//                conversationRepository.save(
//                Conversation.builder()
//                        .createdDate(LocalDateTime.now())
//                        .updatedDate(LocalDateTime.now())
//                        .id("679998f257bcd648ae7813af")
//                        .memberIDs(List.of(user.getId()))
//                        .wallpaperUrl("https://i.ibb.co/933333/nvhai227.png")
//                        .build()
//        ));
//        DocumentRepository documentRepository = applicationContext.getBean(DocumentRepository.class);
//        Document document =documentRepository.findById("679998d657bcd648ae78138a").orElseGet(() ->
//                documentRepository.save(
//                Document.builder()
//                        .categoryID(category)
//                        .countDownload(5)
//                        .createdAt(LocalDateTime.now())
//                        .updatedAt(LocalDateTime.now())
//                        .id("679998fd57bcd648ae7813bf")
//                        .description("I'm a java spring developer")
//                        .fileUrl("https://i.ibb.co/933333/nvhai227.docx")
//                        .score(200)
//                        .build()
//        ));
//        HeartRepository heartRepository = applicationContext.getBean(HeartRepository.class);
//        Heart heart =heartRepository.findById("679998d657bcd648ae78138a").orElseGet(() ->
//                heartRepository.save(
//                Heart.builder()
//                        .referenceID("referenceID")
//                        .createdAt(LocalDateTime.now())
//                        .updatedAt(LocalDateTime.now())
//                        .id("6799990857bcd648ae7813ce")
//                        .userID(user.getId())
//                        .build()
//        ));
//        MessageRepository messageRepository = applicationContext.getBean(MessageRepository.class);
//        Message message =messageRepository.findById("679998d657bcd648ae78138a").orElseGet(() ->
//                messageRepository.save(
//                Message.builder()
//                        .content("I'm a java spring developer")
//                        .conversationID(conversation.getId())
//                        .createdAt(LocalDateTime.now())
//                        .updatedAt(LocalDateTime.now())
//                        .id("6799991357bcd648ae7813dc")
//                        .fileUrl("https://i.ibb.co/933333/nvhai227.txt")
//                        .messageParentID(null)
//                        .conversationID(conversation.getId())
//                        .senderID(user.getId())
//                        .type(null)
//                        .build()
//        ));
//        NotificationRepository notificationRepository = applicationContext.getBean(NotificationRepository.class);
//        Notification notification =notificationRepository.findById("679998d657bcd648ae78138a").orElseGet(() ->
//                notificationRepository.save(
//                Notification.builder()
//                        .referenceID("referenceID")
//                        .createdAt(LocalDateTime.now())
//                        .updatedAt(LocalDateTime.now())
//                        .id("6799992557bcd648ae7813de")
//                        .isUnRead(false)
//                        .userIDs(List.of(user.getId()))
//                        .actorID(null)
//                        .isModal(false)
//                        .description("descript for notification")
//                        .build()
//        ));
//        ShareRepository shareRepository = applicationContext.getBean(ShareRepository.class);
//        Share share =shareRepository.findById("679998d657bcd648ae78138a").orElseGet(() ->
//                shareRepository.save(
//                Share.builder()
//                        .createdAt(LocalDateTime.now())
//                        .updatedAt(LocalDateTime.now())
//                        .id("6799992f57bcd648ae7813e6")
//                        .referenceID("referenceID")
//                        .userID(user.getId())
//                        .build()
//        ));
//        //c2
//
//        ReportRepository reportRepository = applicationContext.getBean(ReportRepository.class);
//        Report report = reportRepository.findById("6799993a57bcd648ae7813f5").orElseGet(() ->
//                reportRepository.save(
//                Report.builder()
//                        .type("type for report")
//                        .content("content for report")
//                        .referenceID("referenceID")
//                        .createdAt(LocalDateTime.now())
//                        .updatedAt(LocalDateTime.now())
//                        .id("6799993a57bcd648ae7813f5")
//                        .userID(user.getId())
//                        .status(Status.pending)
//                        .build()
//        ));
//        FollowRepository followRepository = applicationContext.getBean(FollowRepository.class);
//        Follow follow = followRepository.findById("6799994657bcd648ae7813ff").orElseGet(() ->
//                followRepository.save(
//                Follow.builder()
//                        .followerID(user.getId())
//                        .createdAt(LocalDateTime.now())
//                        .updatedAt(LocalDateTime.now())
//                        .id("6799994657bcd648ae7813ff")
//                        .userID(user.getId())
//                        .build()
//        ));
//        CourseRepository courseRepository = applicationContext.getBean(CourseRepository.class);
//        Course course = courseRepository.findById("6799995757bcd648ae781408").orElseGet(() ->
//                courseRepository.save(
//                Course.builder()
//                        .title("title for courses")
//                        .categoryID(category.getId())
//                        .view(45)
//                        .createdAt(LocalDateTime.now())
//                        .updatedAt(LocalDateTime.now())
//                        .id("6799995757bcd648ae781408")
//                        .description("desciption for course")
//                        .imageUrl("https://i.ibb.co/933333/nvhai227.png")
//                        .userID(user.getId())
//                        .build()
//        ));
//        VideoRepository videoRepository = applicationContext.getBean(VideoRepository.class);
//        Video video = videoRepository.findById("6799996257bcd648ae781410").orElseGet(() ->
//                videoRepository.save(
//                Video.builder()
//                        .title("title for video")
//                        .courseID(course.getId())
//                        .view(123)
//                        .createdAt(LocalDateTime.now())
//                        .updatedAt(LocalDateTime.now())
//                        .id("6799996257bcd648ae781410")
//                        .description("desciption for video")
//                        .imageUrl("https://yt3.ggpht.com/h7mMk0EIIrXGuMqzNTI9rNRBYuABVuHKOl9NJrdytR4KcUJEnZ2wPwdGppEeqHmB40UAvh8jFRQ=s48-c-k-c0x00ffffff-no-rj")
//                        .videoUrl("https://youtube.com/shorts/Q6dFMcHyY4U?si=Txj0LA9qzdsmX8Lw")
//                        .userID(user.getId())
//                        .build()
//        ));
//        BlogRepository blogRepository = applicationContext.getBean(BlogRepository.class);
//        Blog blog =blogRepository.findById("6799996f57bcd648ae781417").orElseGet(() ->
//                blogRepository.save(
//                Blog.builder()
//                        .url("https://nvhai227.github.io/")
//                        .categoryID(category.getId())
//                        .content("discord fix bug java spring")
//                        .id("6799996f57bcd648ae781417")
//                        .createdDate(LocalDateTime.now())
//                        .updatedDate(LocalDateTime.now())
//                        .userID(user.getId())
//                        .build()
//        ));
//    }

    private void initializeSampleData() {
//        clearAllData();
        //c0
        RoleRepository roleRepository = applicationContext.getBean(RoleRepository.class);
        Role userRole = roleRepository.save(
                Role.builder()
                        .id("6799978226692b76e021501c")
                        .name("user")
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .build()
        );
        Role creatorRole = roleRepository.save(
                Role.builder()
                        .id("6799980fc73db076c26e65fc")
                        .name("creator")
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .build()
        );
        Role managerRole = roleRepository.save(
                Role.builder()
                        .id("67999800c73db076c26e65fb")
                        .name("manager")
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .build()
        );

        CategoryRepository categoryRepository = applicationContext.getBean(CategoryRepository.class);
        Category category = categoryRepository.save(
                Category.builder()
                        .id("67999819c73db076c26e6610")
                        .name("programing")
                        .description("discord fix bug java spring")
                        .createdDate(LocalDateTime.now())
                        .updatedDate(LocalDateTime.now())
                        .build()
        );
        categoryRepository.save(
                Category.builder()
                        .id("6799985d57bcd648ae781355")
                        .name("code web")
                        .description("discord fix bug reactks")
                        .createdDate(LocalDateTime.now())
                        .updatedDate(LocalDateTime.now())
                        .build()
        );
        CensorshipRepository censorshipRepository = applicationContext.getBean(CensorshipRepository.class);
        censorshipRepository.save(
                Censorship.builder()
                        .id("6799986f57bcd648ae781356")
                        .contentID(null)
                        .status(Status.pending)
                        .feedback("feedback for censorship")
                        .createdDate(LocalDateTime.now())
                        .updatedDate(LocalDateTime.now())
                        .build()
        );
        //c1
        IUserRepository userRepository = applicationContext.getBean(IUserRepository.class);
        User user = userRepository.save(
                User.builder()
                        .id("6799988457bcd648ae78136a")
                        .username("user")
                        .roleID(userRole.getId())
                        .email("nvhai227@gmail.com")
                        .password(passwordEncoder.encode("abCD@1234"))
                        .address("Ninh Bình")
                        .firstName("Nguyễn Văn")
                        .lastName("Hải")
                        .gender(Gender.Male)
                        .phoneNumber("0123456789")
                        .rating(1000000000)
                        .description("I'm a java spring developer")
                        .birthday(LocalDate.parse("01/01/2003", DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                        .version(2L)
                        .avatar("https://antimatter.vn/wp-content/uploads/2022/11/anh-avatar-trang-fb-mac-dinh.jpg")
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .build()
        );
        User creator = userRepository.save(
                User.builder()
                        .id("6799988457bcd648ae78132a")
                        .username("creator")
                        .roleID(creatorRole.getId())
                        .email("creator.com")
                        .password(passwordEncoder.encode("abCD@1234"))
                        .address("Ninh Bình")
                        .firstName("Nguyễn Văn")
                        .lastName("Hải")
                        .gender(Gender.Male)
                        .phoneNumber("0123456789")
                        .rating(1000000000)
                        .description("I'm a java spring developer")
                        .birthday(LocalDate.parse("01/01/2003", DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                        .version(2L)
                        .avatar("https://antimatter.vn/wp-content/uploads/2022/11/anh-avatar-trang-fb-mac-dinh.jpg")
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .build()
        );

        ManagerRepository managerRepository = applicationContext.getBean(ManagerRepository.class);
        Manager manager = managerRepository.save(
                Manager.builder()
                        .id("679998b457bcd648ae781385")
                        .username("manager")
                        .roleID(managerRole.getId())
                        .address("nb")
                        .phoneNumber("0123456789")
                        .email("manager.com")
                        .password(passwordEncoder.encode("abCD@1234"))
                        .gender(Gender.Male)
                        .avatar("https://antimatter.vn/wp-content/uploads/2022/11/anh-avatar-trang-fb-mac-dinh.jpg")
                        .birthday(LocalDate.parse("01/01/2003",DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                        .lastName("nv")
                        .firstName("hai")
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .build()
        );
        BannerRepository bannerRepository = applicationContext.getBean(BannerRepository.class);
        bannerRepository.save(
                Banner.builder()
                        .id("679998c657bcd648ae781386")
                        .site("nvhai227")
                        .managerID(manager.getId())
                        .createdDate(LocalDateTime.now())
                        .updatedDate(LocalDateTime.now())
                        .imageUrl("https://i.ibb.co/933333/nvhai227.png")
                        .name("nvhai227")
                        .createdDate(LocalDateTime.now())
                        .updatedDate(LocalDateTime.now())
                        .build()
        );
        ICodeR iCodeR = applicationContext.getBean(ICodeR.class);
        iCodeR.save(
                Code.builder()
                        .code(123)
                        .id("679998d657bcd648ae78138a")
                        .createdDate(LocalDateTime.now())
                        .updatedDate(LocalDateTime.now())
                        .codeExpiration(Date.from(Instant.now().plusSeconds(3600)))
                        .userID(user.getId())
                        .userEmail(user.getEmail())
                        .createdDate(LocalDateTime.now())
                        .updatedDate(LocalDateTime.now())
                        .build()
        );
        CommentRepository commentRepository = applicationContext.getBean(CommentRepository.class);
        Comment comment = commentRepository.save(
                Comment.builder()
                        .referenceID("referenceID")
                        .content("content for comment")
                        .createdDate(LocalDateTime.now())
                        .updatedDate(LocalDateTime.now())
                        .id("679998e757bcd648ae78139d")
                        .parentCommentID(null)
                        .userID(user.getId())
                        .createdDate(LocalDateTime.now())
                        .updatedDate(LocalDateTime.now())
                        .build()
        );
        ConversationRepository conversationRepository = applicationContext.getBean(ConversationRepository.class);
        Conversation conversation = conversationRepository.save(
                Conversation.builder()
                        .createdDate(LocalDateTime.now())
                        .updatedDate(LocalDateTime.now())
                        .id("679998f257bcd648ae7813af")
                        .memberIDs(List.of(user.getId()))
                        .wallpaperUrl("https://i.ibb.co/933333/nvhai227.png")
                        .build()
        );
        DocumentRepository documentRepository = applicationContext.getBean(DocumentRepository.class);
        Document document = documentRepository.save(
                Document.builder()
                        .userID(creator.getId())
                        .categoryID(category.getId())
                        .countDownload(5)
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .id("679998fd57bcd648ae7813bf")
                        .description("I'm a java spring developer")
                        .fileUrl("https://i.ibb.co/933333/nvhai227.docx")
                        .score(200d)
                        .build()
        );
        HeartRepository heartRepository = applicationContext.getBean(HeartRepository.class);
        Heart heart = heartRepository.save(
                Heart.builder()
                        .referenceID("referenceID")
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .id("6799990857bcd648ae7813ce")
                        .userID(user.getId())
                        .build()
        );
        MessageRepository messageRepository = applicationContext.getBean(MessageRepository.class);
        Message message = messageRepository.save(
                Message.builder()
                        .content("I'm a java spring developer")
                        .conversationID(conversation.getId())
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .id("6799991357bcd648ae7813dc")
                        .fileUrl("https://i.ibb.co/933333/nvhai227.txt")
                        .messageParentID(null)
                        .conversationID(conversation.getId())
                        .senderID(user.getId())
                        .type(null)
                        .build()
        );
        NotificationRepository notificationRepository = applicationContext.getBean(NotificationRepository.class);
        Notification notification = notificationRepository.save(
                Notification.builder()
                        .referenceID("referenceID")
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .id("6799992557bcd648ae7813de")
                        .isUnRead(false)
                        .userIDs(List.of(user.getId()))
                        .actorID(null)
                        .isModal(false)
                        .description("descript for notification")
                        .build()
        );
        ShareRepository shareRepository = applicationContext.getBean(ShareRepository.class);
        Share share = shareRepository.save(
                Share.builder()
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .id("6799992f57bcd648ae7813e6")
                        .referenceID("referenceID")
                        .userID(user.getId())
                        .build()
        );
        //c2

        ReportRepository reportRepository = applicationContext.getBean(ReportRepository.class);
        Report report = reportRepository.save(
                Report.builder()
                        .type("type for report")
                        .content("content for report")
                        .referenceID("referenceID")
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .id("6799993a57bcd648ae7813f5")
                        .userID(user.getId())
                        .status(Status.pending)
                        .build()
        );
        FollowRepository followRepository = applicationContext.getBean(FollowRepository.class);
        Follow follow = followRepository.save(
                Follow.builder()
                        .followerID(user.getId())
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .id("6799994657bcd648ae7813ff")
                        .userID(user.getId())
                        .build()
        );
        CourseRepository courseRepository = applicationContext.getBean(CourseRepository.class);
        Course course = courseRepository.save(
                Course.builder()
                        .userID(creator.getId())
                        .title("title for courses")
                        .categoryID(category.getId())
                        .view(45)
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .id("6799995757bcd648ae781408")
                        .description("desciption for course")
                        .imageUrl("https://i.ibb.co/933333/nvhai227.png")
                        .build()
        );
        VideoRepository videoRepository = applicationContext.getBean(VideoRepository.class);
        Video video = videoRepository.save(
                Video.builder()
                        .title("title for video")
                        .courseID(course.getId())
                        .view(123)
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .id("6799996257bcd648ae781410")
                        .description("desciption for video")
                        .imageUrl("https://yt3.ggpht.com/h7mMk0EIIrXGuMqzNTI9rNRBYuABVuHKOl9NJrdytR4KcUJEnZ2wPwdGppEeqHmB40UAvh8jFRQ=s48-c-k-c0x00ffffff-no-rj")
                        .videoUrl("https://youtube.com/shorts/Q6dFMcHyY4U?si=Txj0LA9qzdsmX8Lw")
                        .userID(creator.getId())
                        .build()
        );
        BlogRepository blogRepository = applicationContext.getBean(BlogRepository.class);
        Blog blog = blogRepository.save(
                Blog.builder()
                        .url("https://nvhai227.github.io/")
                        .categoryID(category.getId())
                        .content("discord fix bug java spring")
                        .id("6799996f57bcd648ae781417")
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
