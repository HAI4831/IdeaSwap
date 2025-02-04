package nvh.run.ideaswap.common.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import nvh.run.ideaswap.common.security.component.CustomAuthenticationProvider;
import nvh.run.ideaswap.common.security.jwt.AuthEntryPointJwt;
import nvh.run.ideaswap.common.security.jwt.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import java.io.IOException;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SpringSecurityConfig {
    HttpServletRequest request;
    JwtAuthenticationFilter jwtAuthenticationFilter;
//    UserDetailsServiceSelector userDetailsServiceSelector;
    CustomAuthenticationProvider customAuthenticationProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .userDetailsService(userDetailsServiceSelector.selectUserDetailsService(request))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(
                                "/ping",
                                "/graphql",
                                "/graphiql",
                                "/user/register",
                                "/api/products",
                                "/api/v1/auth/login",
                                "/api/v1/auth/register",
                                "/api/v1/auth/register",
                                "/swagger-ui.html",
                                "v3/api-docs/*",
                                "/swagger*/*",
                                "/webjars/swagger-ui/*",
                                "/api/v1/admin/auth/register",
                                "/api/v1/admin/auth/login",
                                "/api/v1/admin/auth/refresh",
                                "/api/v1/code/send",
                                "/api/v1/code/verify"

                        ).permitAll()

//                        .requestMatchers("/api/v1/categories/*").hasAuthority("user")
                        .requestMatchers(HttpMethod.GET,"/api/v1/categories/*").hasAuthority("user")
                        .requestMatchers(HttpMethod.POST,"/api/v1/categories/*").hasAuthority("admin")
                        .requestMatchers(HttpMethod.PUT,"/api/v1/categories/*").hasAuthority("admin")
                        .requestMatchers(HttpMethod.DELETE,"/api/v1/categories/*").hasAuthority("admin")

                        .requestMatchers(HttpMethod.GET,"/api/v1/products/*").hasAuthority("user")
                        .requestMatchers(HttpMethod.POST,"/api/v1/products/*").hasAuthority("admin")
                        .requestMatchers(HttpMethod.PUT,"/api/v1/products/*").hasAuthority("admin")
                        .requestMatchers(HttpMethod.DELETE,"/api/v1/products/*").hasAuthority("admin")
                        .requestMatchers(HttpMethod.GET,"/api/v1/banner/*").hasAuthority("user")
                        .requestMatchers(HttpMethod.POST,"/api/v1/banner/*").hasAuthority("admin")
                        .requestMatchers(HttpMethod.PUT,"/api/v1/banner/*").hasAuthority("admin")
                        .requestMatchers(HttpMethod.DELETE,"/api/v1/banner/*").hasAuthority("admin")
                        .requestMatchers(HttpMethod.GET,"/api/v1/blogs/*").hasAuthority("user")
                        .requestMatchers(HttpMethod.POST,"/api/v1/blogs/*").hasAuthority("admin")
                        .requestMatchers(HttpMethod.PUT,"/api/v1/blogs/*").hasAuthority("admin")
                        .requestMatchers(HttpMethod.DELETE,"/api/v1/blogs/*").hasAuthority("admin")
                        .requestMatchers(HttpMethod.GET,"/api/v1/censorships/*").hasAuthority("user")
                        .requestMatchers(HttpMethod.POST,"/api/v1/censorships/*").hasAuthority("admin")
                        .requestMatchers(HttpMethod.PUT,"/api/v1/censorships/*").hasAuthority("admin")
                        .requestMatchers(HttpMethod.DELETE,"/api/v1/censorships/*").hasAuthority("admin")
                        .requestMatchers("/api/v1/code/*").hasAuthority("user")
//                        .requestMatchers("/api/v1/comment/*").hasAuthority("user")
                        .requestMatchers(HttpMethod.GET,"/api/v1/comment/*").hasAuthority("user")
                        .requestMatchers(HttpMethod.POST,"/api/v1/comment/*").hasAuthority("admin")
                        .requestMatchers(HttpMethod.PUT,"/api/v1/comment/*").hasAuthority("admin")
                        .requestMatchers(HttpMethod.DELETE,"/api/v1/comment/*").hasAuthority("admin")
                        .requestMatchers(HttpMethod.GET,"/api/v1/conversation/*").hasAuthority("user")
                        .requestMatchers(HttpMethod.POST,"/api/v1/conversation/*").hasAuthority("admin")
                        .requestMatchers(HttpMethod.PUT,"/api/v1/conversation/*").hasAuthority("admin")
                        .requestMatchers(HttpMethod.DELETE,"/api/v1/conversation/*").hasAuthority("admin")
                        .requestMatchers(HttpMethod.GET,"/api/v1/course/*").hasAuthority("user")
                        .requestMatchers(HttpMethod.POST,"/api/v1/course/*").hasAuthority("admin")
                        .requestMatchers(HttpMethod.PUT,"/api/v1/course/*").hasAuthority("admin")
                        .requestMatchers(HttpMethod.DELETE,"/api/v1/course/*").hasAuthority("admin")
                        .requestMatchers(HttpMethod.GET,"/api/v1/document/*").hasAuthority("user")
                        .requestMatchers(HttpMethod.POST,"/api/v1/document/*").hasAuthority("admin")
                        .requestMatchers(HttpMethod.PUT,"/api/v1/document/*").hasAuthority("admin")
                        .requestMatchers(HttpMethod.DELETE,"/api/v1/document/*").hasAuthority("admin")
                        .requestMatchers(HttpMethod.GET,"/api/v1/follow/*").hasAuthority("user")
                        .requestMatchers(HttpMethod.POST,"/api/v1/follow/*").hasAuthority("admin")
                        .requestMatchers(HttpMethod.PUT,"/api/v1/follow/*").hasAuthority("admin")
                        .requestMatchers(HttpMethod.DELETE,"/api/v1/follow/*").hasAuthority("admin")
                        .requestMatchers(HttpMethod.GET,"/api/v1/heart/*").hasAuthority("user")
                        .requestMatchers(HttpMethod.POST,"/api/v1/heart/*").hasAuthority("admin")
                        .requestMatchers(HttpMethod.PUT,"/api/v1/heart/*").hasAuthority("admin")
                        .requestMatchers(HttpMethod.DELETE,"/api/v1/heart/*").hasAuthority("admin")
                        .requestMatchers("/api/v1/manager/*").hasAuthority("admin")
                        .requestMatchers(HttpMethod.GET,"/api/v1/message/*").hasAuthority("user")//not have
                        .requestMatchers(HttpMethod.POST,"/api/v1/message/*").hasAuthority("admin")//not have
                        .requestMatchers(HttpMethod.PUT,"/api/v1/message/*").hasAuthority("admin")//not have
                        .requestMatchers(HttpMethod.DELETE,"/api/v1/message/*").hasAuthority("admin")//not have
                        .requestMatchers(HttpMethod.GET,"/api/v1/notification/*").hasAuthority("user")
                        .requestMatchers(HttpMethod.POST,"/api/v1/notification/*").hasAuthority("admin")
                        .requestMatchers(HttpMethod.PUT,"/api/v1/notification/*").hasAuthority("admin")
                        .requestMatchers(HttpMethod.DELETE,"/api/v1/notification/*").hasAuthority("admin")
                        .requestMatchers("/api/v1/role/*").hasAuthority("admin")
                        .requestMatchers(HttpMethod.GET,"/api/v1/share/*").hasAuthority("user")
                        .requestMatchers(HttpMethod.POST,"/api/v1/share/*").hasAuthority("admin")
                        .requestMatchers(HttpMethod.PUT,"/api/v1/share/*").hasAuthority("admin")
                        .requestMatchers(HttpMethod.DELETE,"/api/v1/share/*").hasAuthority("admin")
                        .requestMatchers("/api/v1/user/*").hasAuthority("admin")
                        .requestMatchers(HttpMethod.GET,"/api/v1/video/*").hasAuthority("user")
                        .requestMatchers(HttpMethod.POST,"/api/v1/video/*").hasAuthority("admin")
                        .requestMatchers(HttpMethod.PUT,"/api/v1/video/*").hasAuthority("admin")
                        .requestMatchers(HttpMethod.DELETE,"/api/v1/video/*").hasAuthority("admin")
                        .requestMatchers(HttpMethod.GET,"/api/v1/contact/*").hasAuthority("user")
                        .requestMatchers(HttpMethod.POST,"/api/v1/contact/*").hasAuthority("admin")
                        .requestMatchers(HttpMethod.PUT,"/api/v1/contact/*").hasAuthority("admin")
                        .requestMatchers(HttpMethod.DELETE,"/api/v1/contact/*").hasAuthority("admin")
                        .requestMatchers(HttpMethod.GET,"/api/v1/banner").hasAuthority("user")
                        .requestMatchers(HttpMethod.POST,"/api/v1/banner").hasAuthority("admin")
                        .requestMatchers(HttpMethod.PUT,"/api/v1/banner").hasAuthority("admin")
                        .requestMatchers(HttpMethod.DELETE,"/api/v1/banner").hasAuthority("admin")

                        .requestMatchers(HttpMethod.GET,"/api/v1/auth/account").hasAuthority("user")
                        .requestMatchers(HttpMethod.POST,"/api/v1/admin/auth/account").hasAuthority("admin")

                        .requestMatchers("/api/v1/admin/*").hasAuthority("admin")
                        .requestMatchers("/admin/*").hasAuthority("ADMIN")
                        .requestMatchers("/superadmin/**").hasAuthority("SUPERADMIN")
                        .anyRequest()
                        .permitAll()
//                        .authenticated()
                );
//                .exceptionHandling()
//                .authenticationEntryPoint(spnegoEntryPoint())
//                .and()

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
//    ____________________________

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

// viết bản custom với SecurityFilterChain
// http.authenticationProvider(customAuthenticationProvider)
//    @Bean
//    public DaoAuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//        authProvider.setUserDetailsService(userDetailsServiceImpl);
////        authProvider.setUserDetailsService(managerDetailsServiceImpl);
//        authProvider.setPasswordEncoder(passwordEncoder());
//        return authProvider;
//    }
//    _________________________
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
}
