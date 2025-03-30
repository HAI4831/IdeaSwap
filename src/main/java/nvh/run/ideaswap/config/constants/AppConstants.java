package nvh.run.ideaswap.config.constants;

import org.springframework.http.HttpMethod;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import java.util.Map;

import static java.util.Map.entry;

public class AppConstants {
    public static final String[] PUBLIC_URLS = {
            "/ping",
            "/graphql",
            "/graphiql",
            "/user/register",
            "/api/products",
            "/api/v1/auth/login",
            "/api/v1/auth/register",
            "/swagger-ui.html",
            "v3/api-docs/*",
            "/swagger*/*",
            "/webjars/swagger-ui/*",
            "/api/v1/admin/auth/register",
            "/api/v1/admin/auth/login",
            "/api/v1/auth//admin/register",
            "/api/v1/auth/admin/login",
            "/api/v1/admin/auth/refresh",
            "/api/v1/code/send",
            "/api/v1/code/verify"
    };
    public static final Map<RequestMatcher, String[]> protectedEndpoints = Map.ofEntries(
            entry(new AntPathRequestMatcher("/api/v1/auth/account", HttpMethod.GET.name()), new String[]{"user", "creator"}),
            entry(new AntPathRequestMatcher("/api/v1/auth/admin/account", HttpMethod.GET.name()), new String[]{"manager"}),

            entry(new AntPathRequestMatcher("/api/v1/categories/**", HttpMethod.GET.name()), new String[]{"user", "manager"}),
            entry(new AntPathRequestMatcher("/api/v1/categories/**", HttpMethod.POST.name()), new String[]{"manager"}),
            entry(new AntPathRequestMatcher("/api/v1/categories/**", HttpMethod.PUT.name()), new String[]{"manager"}),
            entry(new AntPathRequestMatcher("/api/v1/categories/**", HttpMethod.DELETE.name()), new String[]{"manager"}),

            entry(new AntPathRequestMatcher("/api/v1/products/**", HttpMethod.GET.name()), new String[]{"user"}),
            entry(new AntPathRequestMatcher("/api/v1/products/**", HttpMethod.POST.name()), new String[]{"manager"}),
            entry(new AntPathRequestMatcher("/api/v1/products/**", HttpMethod.PUT.name()), new String[]{"manager"}),
            entry(new AntPathRequestMatcher("/api/v1/products/**", HttpMethod.DELETE.name()), new String[]{"manager"}),

            entry(new AntPathRequestMatcher("/api/v1/banner/**", HttpMethod.GET.name()), new String[]{"user"}),
            entry(new AntPathRequestMatcher("/api/v1/banner/**", HttpMethod.POST.name()), new String[]{"manager"}),
            entry(new AntPathRequestMatcher("/api/v1/banner/**", HttpMethod.PUT.name()), new String[]{"manager"}),
            entry(new AntPathRequestMatcher("/api/v1/banner/**", HttpMethod.DELETE.name()), new String[]{"manager"}),

            entry(new AntPathRequestMatcher("/api/v1/blogs/**", HttpMethod.GET.name()), new String[]{"user", "creator"}),
            entry(new AntPathRequestMatcher("/api/v1/blogs/**", HttpMethod.POST.name()), new String[]{"user", "creator"}),
            entry(new AntPathRequestMatcher("/api/v1/blogs/**", HttpMethod.PUT.name()), new String[]{"user", "creator"}),
            entry(new AntPathRequestMatcher("/api/v1/blogs/**", HttpMethod.DELETE.name()), new String[]{"user", "creator", "manager"}),

            entry(new AntPathRequestMatcher("/api/v1/censorships/**", HttpMethod.PUT.name()), new String[]{"manager"}),
            entry(new AntPathRequestMatcher("/api/v1/code/**"), new String[]{"user"}),

            entry(new AntPathRequestMatcher("/api/v1/comment/**", HttpMethod.POST.name()), new String[]{"user", "creator"}),

            entry(new AntPathRequestMatcher("/api/v1/conversation/**", HttpMethod.GET.name()), new String[]{"user", "creator", "manager"}),
            entry(new AntPathRequestMatcher("/api/v1/conversation/**", HttpMethod.POST.name()), new String[]{"user", "creator"}),
            entry(new AntPathRequestMatcher("/api/v1/conversation/**", HttpMethod.DELETE.name()), new String[]{"user", "creator"}),

            entry(new AntPathRequestMatcher("/api/v1/course/**", HttpMethod.POST.name()), new String[]{"creator"}),
            entry(new AntPathRequestMatcher("/api/v1/course/**", HttpMethod.PUT.name()), new String[]{"creator"}),
            entry(new AntPathRequestMatcher("/api/v1/course/update/view/**", HttpMethod.PUT.name()), new String[]{"user", "creator"}),
            entry(new AntPathRequestMatcher("/api/v1/course/**", HttpMethod.DELETE.name()), new String[]{"creator", "manager"}),

            entry(new AntPathRequestMatcher("/api/v1/document/**", HttpMethod.GET.name()), new String[]{"user", "creator", "manager"}),
            entry(new AntPathRequestMatcher("/api/v1/document/**", HttpMethod.POST.name()), new String[]{"creator"}),
            entry(new AntPathRequestMatcher("/api/v1/document/**", HttpMethod.PUT.name()), new String[]{"creator", "manager"}),
            entry(new AntPathRequestMatcher("/api/v1/document/**", HttpMethod.DELETE.name()), new String[]{"creator", "manager"}),

            entry(new AntPathRequestMatcher("/api/v1/follow/**", HttpMethod.POST.name()), new String[]{"user", "creator"}),
            entry(new AntPathRequestMatcher("/api/v1/follow/**", HttpMethod.DELETE.name()), new String[]{"user", "creator"}),

            entry(new AntPathRequestMatcher("/api/v1/heart/**", HttpMethod.POST.name()), new String[]{"user", "creator"}),
            entry(new AntPathRequestMatcher("/api/v1/heart/**", HttpMethod.DELETE.name()), new String[]{"user", "creator"}),

            entry(new AntPathRequestMatcher("/api/v1/message/**", HttpMethod.GET.name()), new String[]{"user", "creator", "manager"}),

            entry(new AntPathRequestMatcher("/api/v1/notification/**", HttpMethod.GET.name()), new String[]{"user", "creator", "manager"}),
            entry(new AntPathRequestMatcher("/api/v1/notification/**", HttpMethod.PUT.name()), new String[]{"user", "creator", "manager"}),

            entry(new AntPathRequestMatcher("/api/v1/role/**", HttpMethod.GET.name()), new String[]{"user", "creator", "manager"}),

            entry(new AntPathRequestMatcher("/api/v1/share/**", HttpMethod.POST.name()), new String[]{"user", "creator"}),

            entry(new AntPathRequestMatcher("/api/v1/user/**", HttpMethod.PUT.name()), new String[]{"user", "creator", "manager"}),
            entry(new AntPathRequestMatcher("/api/v1/user/**", HttpMethod.DELETE.name()), new String[]{"manager"}),

            entry(new AntPathRequestMatcher("/api/v1/video/**", HttpMethod.POST.name()), new String[]{"creator"}),
            entry(new AntPathRequestMatcher("/api/v1/video/**", HttpMethod.PUT.name()), new String[]{"user", "creator", "manager"}),
            entry(new AntPathRequestMatcher("/api/v1/video/update/view/**", HttpMethod.PUT.name()), new String[]{"user", "creator", "manager"}),
            entry(new AntPathRequestMatcher("/api/v1/video/**", HttpMethod.DELETE.name()), new String[]{"creator", "manager"}),

            entry(new AntPathRequestMatcher("/api/v1/contact/**", HttpMethod.GET.name()), new String[]{"user"}),
            entry(new AntPathRequestMatcher("/api/v1/contact/**", HttpMethod.POST.name()), new String[]{"manager"}),
            entry(new AntPathRequestMatcher("/api/v1/contact/**", HttpMethod.PUT.name()), new String[]{"manager"}),
            entry(new AntPathRequestMatcher("/api/v1/contact/**", HttpMethod.DELETE.name()), new String[]{"manager"}),

            entry(new AntPathRequestMatcher("/api/v1/banner"), new String[]{"manager"}),

            entry(new AntPathRequestMatcher("/api/v1/admin/**"), new String[]{"manager"}),
            entry(new AntPathRequestMatcher("/admin/**"), new String[]{"manager"})
    );


}
