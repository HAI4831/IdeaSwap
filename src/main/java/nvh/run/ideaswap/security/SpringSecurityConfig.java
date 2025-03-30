package nvh.run.ideaswap.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import nvh.run.ideaswap.security.component.CustomAuthenticationProvider;
import nvh.run.ideaswap.security.jwt.AuthEntryPointJwt;
import nvh.run.ideaswap.security.jwt.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.io.IOException;
import java.util.List;

import static nvh.run.ideaswap.config.constants.AppConstants.PUBLIC_URLS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SpringSecurityConfig {
    HttpServletRequest request;
    JwtAuthenticationFilter jwtAuthenticationFilter;
    CustomAuthenticationProvider customAuthenticationProvider;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> {
                            authorize.requestMatchers(PUBLIC_URLS).permitAll();

                            authorize
                                    .requestMatchers(HttpMethod.GET, "/api/v1/auth/account").hasAnyAuthority("user", "creator")
                                    .requestMatchers(HttpMethod.GET, "/api/v1/auth/admin/account").hasAnyAuthority("manager")

                                    .requestMatchers(HttpMethod.GET, "/api/v1/categories/*").hasAnyAuthority("user", "manager")
                                    .requestMatchers(HttpMethod.POST, "/api/v1/categories/*").hasAnyAuthority("manager")
                                    .requestMatchers(HttpMethod.PUT, "/api/v1/categories/*").hasAnyAuthority("manager")
                                    .requestMatchers(HttpMethod.DELETE, "/api/v1/categories/*").hasAnyAuthority("manager")

                                    .requestMatchers(HttpMethod.GET, "/api/v1/products/*").hasAnyAuthority("user")
                                    .requestMatchers(HttpMethod.POST, "/api/v1/products/*").hasAnyAuthority("manager")
                                    .requestMatchers(HttpMethod.PUT, "/api/v1/products/*").hasAnyAuthority("manager")
                                    .requestMatchers(HttpMethod.DELETE, "/api/v1/products/*").hasAnyAuthority("manager")
                                    .requestMatchers(HttpMethod.GET, "/api/v1/banner/*").hasAnyAuthority("user")
                                    .requestMatchers(HttpMethod.POST, "/api/v1/banner/*").hasAnyAuthority("manager")
                                    .requestMatchers(HttpMethod.PUT, "/api/v1/banner/*").hasAnyAuthority("manager")
                                    .requestMatchers(HttpMethod.DELETE, "/api/v1/banner/*").hasAnyAuthority("manager")

                                    .requestMatchers(HttpMethod.GET, "/api/v1/blogs/*").hasAnyAuthority("user", "creator")
                                    .requestMatchers(HttpMethod.POST, "/api/v1/blogs/*").hasAnyAuthority("user", "creator")
                                    .requestMatchers(HttpMethod.PUT, "/api/v1/blogs/*").hasAnyAuthority("user", "creator")
                                    .requestMatchers(HttpMethod.DELETE, "/api/v1/blogs/*").hasAnyAuthority("user", "creator", "manager")

                                    .requestMatchers(HttpMethod.PUT, "/api/v1/censorships/*").hasAnyAuthority("manager")
                                    .requestMatchers("/api/v1/code/*").hasAnyAuthority("user")

                                    .requestMatchers(HttpMethod.POST, "/api/v1/comment/*").hasAnyAuthority("user", "creator")

                                    .requestMatchers(HttpMethod.GET, "/api/v1/conversation/*").hasAnyAuthority("user", "creator", "manager")
                                    .requestMatchers(HttpMethod.POST, "/api/v1/conversation/*").hasAnyAuthority("user", "creator")
                                    .requestMatchers(HttpMethod.DELETE, "/api/v1/conversation/*").hasAnyAuthority("user", "creator")

                                    .requestMatchers(HttpMethod.GET, "/api/v1/course/*").permitAll()
                                    .requestMatchers(HttpMethod.POST, "/api/v1/course/*").hasAnyAuthority("creator")
                                    .requestMatchers(HttpMethod.PUT, "/api/v1/course/*").hasAnyAuthority("creator")
                                    .requestMatchers(HttpMethod.PUT, "/api/v1/course/update/view/*").hasAnyAuthority("user", "creator")
                                    .requestMatchers(HttpMethod.DELETE, "/api/v1/course/*").hasAnyAuthority("creator", "manager")

                                    .requestMatchers(HttpMethod.GET, "/api/v1/document/*").hasAnyAuthority("user", "creator", "manager")
                                    .requestMatchers(HttpMethod.POST, "/api/v1/document/*").hasAnyAuthority("creator")
                                    .requestMatchers(HttpMethod.PUT, "/api/v1/document/*").hasAnyAuthority("creator", "manager")
                                    .requestMatchers(HttpMethod.DELETE, "/api/v1/document/*").hasAnyAuthority("creator", "manager")

                                    .requestMatchers(HttpMethod.GET, "/api/v1/follow/*").permitAll()
                                    .requestMatchers(HttpMethod.POST, "/api/v1/follow/*").hasAnyAuthority("user", "creator")
                                    .requestMatchers(HttpMethod.DELETE, "/api/v1/follow/*").hasAnyAuthority("user", "creator")

                                    .requestMatchers(HttpMethod.GET, "/api/v1/heart/*").permitAll()
                                    .requestMatchers(HttpMethod.POST, "/api/v1/heart/*").hasAnyAuthority("user", "creator")
                                    .requestMatchers(HttpMethod.DELETE, "/api/v1/heart/*").hasAnyAuthority("user", "creator")

                                    .requestMatchers(HttpMethod.GET, "/api/v1/message/*").hasAnyAuthority("user", "creator", "manager")//not have

                                    .requestMatchers(HttpMethod.GET, "/api/v1/notification/*").hasAnyAuthority("user", "creator", "manager")
                                    .requestMatchers(HttpMethod.PUT, "/api/v1/notification/*").hasAnyAuthority("user", "creator", "manager")

                                    .requestMatchers(HttpMethod.GET, "/api/v1/role/*").hasAnyAuthority("user", "creator", "manager")

                                    .requestMatchers(HttpMethod.POST, "/api/v1/share/*").hasAnyAuthority("user", "creator")

                                    .requestMatchers(HttpMethod.PUT, "/api/v1/user/*").hasAnyAuthority("user", "creator", "manager")
                                    .requestMatchers(HttpMethod.DELETE, "/api/v1/user/*").hasAnyAuthority("manager")

                                    .requestMatchers(HttpMethod.GET, "/api/v1/video/*").permitAll()
                                    .requestMatchers(HttpMethod.POST, "/api/v1/video/*").hasAnyAuthority("creator")
                                    .requestMatchers(HttpMethod.PUT, "/api/v1/video/*").hasAnyAuthority("user", "creator", "manager")
                                    .requestMatchers(HttpMethod.PUT, "/api/v1/video/update/view/*").hasAnyAuthority("user", "creator", "manager")
                                    .requestMatchers(HttpMethod.DELETE, "/api/v1/video/*").hasAnyAuthority("creator", "manager")

                                    .requestMatchers(HttpMethod.GET, "/api/v1/contact/*").hasAnyAuthority("user")
                                    .requestMatchers(HttpMethod.POST, "/api/v1/contact/*").hasAnyAuthority("manager")
                                    .requestMatchers(HttpMethod.PUT, "/api/v1/contact/*").hasAnyAuthority("manager")
                                    .requestMatchers(HttpMethod.DELETE, "/api/v1/contact/*").hasAnyAuthority("manager")

                                    .requestMatchers(HttpMethod.GET, "/api/v1/banner").permitAll()
                                    .requestMatchers("/api/v1/banner").hasAnyAuthority("manager")

                                    .requestMatchers(HttpMethod.GET, "/api/v1/auth/account").hasAnyAuthority("user")
                                    .requestMatchers(HttpMethod.GET, "/api/v1/admin/auth/account").hasAnyAuthority("manager")
                                    .requestMatchers(HttpMethod.POST, "/api/v1/admin/auth/account").hasAnyAuthority("manager")

                                    .requestMatchers("/api/v1/admin/*").hasAnyAuthority("manager")
                                    .requestMatchers("/admin/*").hasAnyAuthority("manager")
                                    .anyRequest()
                                    .permitAll();
                        }
                );


        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        http.authenticationProvider(customAuthenticationProvider);
        http.exceptionHandling(ex->ex.authenticationEntryPoint(new AuthEntryPointJwt()));
        http.logout(logout -> logout
                .logoutUrl("/logout") // Đặt URL logout tùy chỉnh
                .logoutSuccessUrl("/home") // Chuyển hướng sau khi logout thành công
                .logoutSuccessHandler(new CustomLogoutSuccessHandler())
                .addLogoutHandler(new CustomLogoutHandler())
                .deleteCookies("JSESSIONID") // Xóa cookie khi logout
                .invalidateHttpSession(true) // Hủy session
                .clearAuthentication(true) // Xóa thông tin xác thực
        );

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    static class CustomLogoutHandler implements LogoutHandler {
        @Override
        public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
            // Thêm logic xóa token hoặc xử lý thêm
            System.out.println("Custom Logout Handler executed");
        }
    }

    static class CustomLogoutSuccessHandler implements LogoutSuccessHandler {
        @Override
        public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
                throws IOException {
            // Gửi phản hồi khi logout thành công
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("Logout successful!");
        }
    }
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.setAllowedOriginPatterns(List.of("*"));
//        configuration.setAllowedOrigins(List.of("http://localhost:5173","http://localhost:3000"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

//    @Bean
//    CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.addAllowedOriginPattern("*");
//        configuration.setAllowedMethods(Arrays.asList("GET","POST","PUT","PATCH","DELETE","OPTIONS"));
//        configuration.setAllowCredentials(false);
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }

}
