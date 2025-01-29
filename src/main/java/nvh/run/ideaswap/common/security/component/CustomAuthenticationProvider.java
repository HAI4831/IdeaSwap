package nvh.run.ideaswap.common.security.component;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import nvh.run.ideaswap.common.security.service.ManagerDetailsServiceImpl;
import nvh.run.ideaswap.common.security.service.UserDetailsServiceImpl;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {
    private final HttpServletRequest request;
    private final UserDetailsServiceImpl userDetailsServiceImpl;
    private final ManagerDetailsServiceImpl managerDetailsServiceImpl;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        // Determine the appropriate UserDetailsService based on URL
        UserDetails userDetails;
        String requestURI = request.getRequestURI();
        if (requestURI.startsWith("/api/v1/admin/auth")) {
            userDetails = managerDetailsServiceImpl.loadUserByUsername(username);
        } else if (requestURI.startsWith("/api/v1/auth")) {
            userDetails = userDetailsServiceImpl.loadUserByUsername(username);
        } else {
            throw new AuthenticationException("Unsupported authentication path: " + requestURI) {};
        }

        // Validate the password
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new AuthenticationException("Invalid credentials") {};
        }

        return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
