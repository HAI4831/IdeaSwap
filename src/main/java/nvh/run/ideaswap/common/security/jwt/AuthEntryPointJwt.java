package nvh.run.ideaswap.common.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import nvh.run.ideaswap.common.exceptions.exception.global.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint {

    private static final Logger logger = LoggerFactory.getLogger(AuthEntryPointJwt.class);

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(HttpServletResponse.SC_UNAUTHORIZED)
                .success(false)
                .message(authException.getMessage())
                .error("Unauthorized")
                .errorClass(authException.getClass().getName())
                .path(request.getRequestURI())
//                .timestamp(LocalDateTime.now())
                .build();

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(new ObjectMapper().writeValueAsString(errorResponse));
    }
}
