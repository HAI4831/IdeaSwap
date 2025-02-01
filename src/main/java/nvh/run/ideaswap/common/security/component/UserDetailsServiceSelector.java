package nvh.run.ideaswap.common.security.component;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import nvh.run.ideaswap.common.security.service.ManagerDetailsServiceImpl;
import nvh.run.ideaswap.common.security.service.UserDetailsServiceImpl;
import org.springframework.security.core.userdetails.UserDetails;
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
        String method = request.getMethod(); // Lấy phương thức HTTP (GET, POST, PUT, DELETE)

        if (requestURI.startsWith("/api/v1/admin/auth")||requestURI.startsWith("/api/v1/user")||requestURI.startsWith("/api/v1/manager")||requestURI.startsWith("/api/v1/role")) {
            return managerDetailsServiceImpl;
        }
        if (requestURI.startsWith("/api/v1/auth")) {
            return userDetailsServiceImpl;
        }
        if (method.equals("POST") || method.equals("PUT") || method.equals("DELETE")) {
            return managerDetailsServiceImpl;
        }
        return userDetailsServiceImpl;
    }

    public UserDetails selectUserDetails(HttpServletRequest request, String username) {
        return selectUserDetailsService(request).loadUserByUsername(username);
    }
}


//package nvh.run.ideaswap.common.security.component;
//
//import jakarta.servlet.http.HttpServletRequest;
//import lombok.AccessLevel;
//import lombok.RequiredArgsConstructor;
//import lombok.experimental.FieldDefaults;
//import nvh.run.ideaswap.common.security.service.ManagerDetailsServiceImpl;
//import nvh.run.ideaswap.common.security.service.UserDetailsServiceImpl;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.stereotype.Component;
//
//@Component
//@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
//@RequiredArgsConstructor
//public class UserDetailsServiceSelector {
//    UserDetailsServiceImpl userDetailsServiceImpl;
//    ManagerDetailsServiceImpl managerDetailsServiceImpl;
//
//    public UserDetailsService selectUserDetailsService(HttpServletRequest request) {
//        String requestURI = request.getRequestURI();
//        String method = request.getMethod();
//
//        // Chọn UserDetailsService theo endpoint và method
//        if (requestURI.startsWith("/api/v1/admin/auth") || (method.equals("POST") || method.equals("PUT") || method.equals("DELETE"))) {
//            return managerDetailsServiceImpl;
//        }
//        return userDetailsServiceImpl;
//    }
//    public UserDetails selectUserDetails(HttpServletRequest request,String username) {
//        String requestURI = request.getRequestURI();
//        String method = request.getMethod();
//        // Chọn UserDetailsService theo endpoint và method
//        if (requestURI.startsWith("/api/v1/admin/auth") || (method.equals("POST") || method.equals("PUT") || method.equals("DELETE"))) {
//            return managerDetailsServiceImpl.loadUserByUsername(username);
//        }
//        return userDetailsServiceImpl.loadUserByUsername(username);
//    }
//}
