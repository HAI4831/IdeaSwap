package nvh.run.ideaswap.common.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import nvh.run.ideaswap.common.security.jwt.AuthEntryPointJwt;
import nvh.run.ideaswap.common.security.jwt.JwtAuthenticationFilter;
import nvh.run.ideaswap.common.security.service.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import java.io.IOException;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SpringSecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final UserDetailsServiceImpl userDetailsServiceImpl;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(
                                "/ping",
                                "/graphql",
                                "/graphiql",
                                "/user/register",
                                "/api/products",
                                "api/v1/auth/login",
                                "/api/v1/auth/register",
                                "/api/v1/auth/registerApi",
                                "/swagger-ui.html",
                                "/swagger*/*"
                        ).permitAll()
                        .requestMatchers("/api/v1/products/*").hasAuthority("user")
                        .requestMatchers("/api/v1/banner/*").hasAuthority("user")
                        .requestMatchers("/api/v1/blogs/*").hasAuthority("user")
                        .requestMatchers("/api/v1/censorships/*").hasAuthority("user")
                        .requestMatchers("/api/v1/code/*").hasAuthority("user")
                        .requestMatchers("/api/v1/comment/*").hasAuthority("user")
                        .requestMatchers("/api/v1/conversation/*").hasAuthority("user")
                        .requestMatchers("/api/v1/course/*").hasAuthority("user")
                        .requestMatchers("/api/v1/document/*").hasAuthority("user")
                        .requestMatchers("/api/v1/follow/*").hasAuthority("user")
                        .requestMatchers("/api/v1/heart/*").hasAuthority("user")
                        .requestMatchers("/api/v1/message/*").hasAuthority("user")//not have
                        .requestMatchers("/api/v1/notification/*").hasAuthority("user")
                        .requestMatchers("/api/v1/roles/*").hasAuthority("user")
                        .requestMatchers("/api/v1/share/*").hasAuthority("user")
                        .requestMatchers("/api/v1/user/*").hasAuthority("user")
                        .requestMatchers("/api/v1/video/*").hasAuthority("user")
                        .requestMatchers("/api/v1/admin/auth/*").hasAuthority("user")//not have
                        .requestMatchers("/api/v1/manager/*").hasAuthority("user")//not have
                        .requestMatchers("/api/v1/contact/*").hasAuthority("user")
                        .requestMatchers("/api/v1/banner").hasAuthority("user")
                        .requestMatchers("/api/v1/auth/account").hasAuthority("user")
                        .requestMatchers("/admin/**").hasAuthority("ADMIN")
                        .requestMatchers("/superadmin/**").hasAuthority("SUPERADMIN")
                        .anyRequest()
//                        .permitAll()
                        .authenticated()
                );
//                .exceptionHandling()
//                .authenticationEntryPoint(spnegoEntryPoint())
//                .and()

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
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

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsServiceImpl);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
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
