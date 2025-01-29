package nvh.run.ideaswap.common.security.component;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import nvh.run.ideaswap.common.security.service.ManagerDetailsServiceImpl;
import nvh.run.ideaswap.common.security.service.UserDetailsServiceImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class UserDetailsServiceSelector {
    UserDetailsServiceImpl userDetailsServiceImpl;
    ManagerDetailsServiceImpl managerDetailsServiceImpl;

    public UserDetailsService selectUserDetailsService(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        String method = request.getMethod();

        // Chọn UserDetailsService theo endpoint và method
        if (requestURI.startsWith("/api/v1/admin/auth") && (method.equals("POST") || method.equals("PUT") || method.equals("DELETE"))) {
            return managerDetailsServiceImpl;
        }
        return userDetailsServiceImpl;
    }
}
