package nvh.run.authsystemgradle.common.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import nvh.run.authsystemgradle.common.security.jwt.AuthEntryPointJwt;
import nvh.run.authsystemgradle.common.security.jwt.JwtAuthenticationFilter;
import nvh.run.authsystemgradle.common.security.service.UserDetailsServiceImpl;
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
                                "/api/v1/auth/registerApi"
                        ).permitAll()
                        .requestMatchers("/api/v1/auth/account").hasAuthority("user")
                        .requestMatchers("/admin/**").hasAuthority("ADMIN")
                        .requestMatchers("/superadmin/**").hasAuthority("SUPERADMIN")
                        .anyRequest().authenticated()
                );
//                .exceptionHandling()
//                .authenticationEntryPoint(spnegoEntryPoint())
//                .and()
        http.exceptionHandling(ex->ex.authenticationEntryPoint(new AuthEntryPointJwt()));

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
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
