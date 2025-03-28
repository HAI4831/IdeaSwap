package nvh.run.ideaswap.controller;

import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;
import java.util.Map;

@RestController
@RequestMapping("/api/i18n")
public class I18nController {
    private final MessageSource messageSource;

    public I18nController(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @GetMapping("/{lang}")
    public Map<String, String> getTranslations(@PathVariable String lang) {
        Locale locale = new Locale(lang);
        String[] keys = {"welcome", "logout"}; // Các key cần lấy
        return Map.ofEntries(
                Map.entry("welcome", messageSource.getMessage("welcome", null, locale)),
                Map.entry("logout", messageSource.getMessage("logout", null, locale))
        );
    }
}
//i18n.jsx:69 i18next::backendConnector: No backend was added via i18next.use. Will not load resources.
//spring:
//messages:
//basename: i18n/messages
//encoding: UTF-8


//import i18n from 'i18next';
//        import { initReactI18next } from 'react-i18next';
//        import Backend from 'i18next-http-backend'; // Backend để tải file từ server
//        import LanguageDetector from 'i18next-browser-languagedetector';
//
//        i18n
//        .use(Backend) // Sử dụng backend để tải dữ liệu
//  .use(LanguageDetector) // Phát hiện ngôn ngữ của trình duyệt
//  .use(initReactI18next)
//  .init({
//    fallbackLng: 'en', // Ngôn ngữ mặc định
//            debug: true,
//            interpolation: {
//        escapeValue: false,
//    },
//    backend: {
//        loadPath: 'http://localhost:8080/api/i18n/{{lng}}', // Gọi API từ Spring Boot
//    },
//});
//
//export default i18n;
