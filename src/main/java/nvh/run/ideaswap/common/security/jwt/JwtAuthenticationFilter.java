package nvh.run.ideaswap.common.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import nvh.run.ideaswap.common.security.component.UserDetailsServiceSelector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    JwtUtilities jwtUtilities;
    UserDetailsServiceSelector userDetailsServiceSelector;
    static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        String token = jwtUtilities.getToken(request);

        if (token != null && jwtUtilities.verifySignedToken(token)) {
            String username = jwtUtilities.extractUsername(token);
            String tokenType = jwtUtilities.extractScope(token);
            try {
                if(tokenType.equals("refresh")) {
                    throw new RuntimeException("Refresh token is forbidden for access this resource");
                }
            } catch (Exception e) {
                throw new RuntimeException("Token error!",e);
            }
            UserDetailsService userDetailsService = userDetailsServiceSelector.selectUserDetailsService(request);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if (userDetails != null) {
                Authentication authentication = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                log.info("Authenticated user with username : {},have role : {} ", username,userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);
    }
}
