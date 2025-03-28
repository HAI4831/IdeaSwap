package nvh.run.ideaswap.security.jwt;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import nvh.run.ideaswap.security.service.ManagerDetailsExtImpl;
import nvh.run.ideaswap.security.service.UserDetailsExtImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.PrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public class JwtUtilities {
    @NonFinal
    @Value("${jwt.ACCESS_TOKEN_VALID_DURATION:3600}") // Default 1 hour
    Long ACCESS_TOKEN_VALID_DURATION;

    @NonFinal
    @Value("${jwt.REFRESH_TOKEN_VALID_DURATION:86400}") // Default 24 hours
    Long REFRESH_TOKEN_VALID_DURATION;

    PrivateKey privateKey;
    RSAPublicKey publicKey;
    @NonFinal
    Map<String, Object> claimsMap=null;

    public String generateAccessToken(Authentication authentication) {
        return generateToken(authentication, ACCESS_TOKEN_VALID_DURATION, "access");
    }

    public String generateRefreshToken(Authentication authentication) {
        return generateToken(authentication, REFRESH_TOKEN_VALID_DURATION, "refresh");
    }

    private String generateToken(Authentication authentication, long validDuration, String tokenType) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        log.trace("Generating {} token for user: {}", tokenType, userDetails.getUsername());

        try {
            JWSHeader header = new JWSHeader(JWSAlgorithm.RS512);

            Map<String, Object> customClaims = new HashMap<>();
//            customClaims.put("tokenType", tokenType);
//            customClaims.put("scope", buildScope(userDetails));

            //cần có userId để client thao tác thông tin cá nhân user bằng gọi userId tới be
            String userId = null;
            String roleId = null;
            if (userDetails instanceof UserDetailsExtImpl) {
                userId = ((UserDetailsExtImpl) userDetails).getId();
                roleId = ((UserDetailsExtImpl) userDetails).getRoleID();
            }
            if (userDetails instanceof ManagerDetailsExtImpl) {
                userId = ((ManagerDetailsExtImpl) userDetails).getId();
                roleId = ((ManagerDetailsExtImpl) userDetails).getRoleID();
            }
            JWTClaimsSet claims = new JWTClaimsSet.Builder()
                    .subject(userDetails.getUsername())
                    .issuer(userDetails.getUsername())
                    .issueTime(new Date())
                    .expirationTime(Date.from(Instant.now().plus(validDuration, ChronoUnit.SECONDS)))
                    .jwtID(UUID.randomUUID().toString())
                    .claim("_id",userId)
                    .claim("tokenType", tokenType)
                    .claim("scope", buildScope(userDetails))
                    .claim("roleID", roleId)
//                    .claim("customClaims", customClaims)
                    .build();

            SignedJWT signedJWT = new SignedJWT(header, claims);
            signedJWT.sign(new RSASSASigner(privateKey));

            log.info("Successfully created {} token for user: {}", tokenType, userDetails.getUsername());
            return signedJWT.serialize();
        } catch (JOSEException e) {
            log.error("Failed to generate {} token for user: {}", tokenType, userDetails.getUsername(), e);
            throw new RuntimeException("Error generating JWT", e);
        }
    }

    private String buildScope(UserDetails userDetails) {
        StringJoiner joiner = new StringJoiner(" ");
        userDetails.getAuthorities().forEach(authority -> joiner.add(authority.getAuthority()));
        return joiner.toString();
    }
//__________________________________________________________
public boolean verifySignedToken(String token) {
    try {
        if(token==null || token.isEmpty()) {
            log.warn("Jwt is empty!!!");
            return false;
        }
        JWSVerifier verifier = new RSASSAVerifier(publicKey);
        SignedJWT signedJWT = SignedJWT.parse(token);

        if (signedJWT.verify(verifier)) {
            JWTClaimsSet claimsSet = signedJWT.getJWTClaimsSet();

            // Kiểm tra thời gian hết hạn (exp)
            if (claimsSet.getExpirationTime() != null) {
                long currentTime = System.currentTimeMillis();
                long expTime = claimsSet.getExpirationTime().getTime();

                if (currentTime > expTime) {
                    log.info("Token has expired.");
                    return false;  // Token hết hiệu lực
                }
            }
            claimsMap = claimsSet.toJSONObject();
            log.info("Token signature verified successfully.");
            return true;  // Token hợp lệ và chưa hết hạn
        } else {
            log.info("Token signature verification failed.");
            return false;  // Không verify được chữ ký
        }
    } catch (Exception e) {
        System.err.println("Error verifying JWT");
        e.printStackTrace();
        throw new RuntimeException("Error verifying JWT", e);
    }
}

    public String extractUsername(String token) {
        return claimsMap.get("sub").toString();
    }


    public String getToken (HttpServletRequest httpServletRequest) {
        final String bearerToken = httpServletRequest.getHeader("Authorization");
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer "))
        {return bearerToken.substring(7); } // The part after "Bearer "
        return null;
    }

    public String extractScope(String token) {
        return claimsMap.get("tokenType").toString();
    }

    public UserDetails extractUserDetails(String token) {
        if (claimsMap == null) verifySignedToken(token);

        String username = (String) claimsMap.get("sub");
        Object scope = claimsMap.get("scope");

        // Chuyển đổi scope thành danh sách quyền
        List<SimpleGrantedAuthority> authorities = (scope instanceof List<?> list) ?
                list.stream().map(Object::toString).map(SimpleGrantedAuthority::new).collect(Collectors.toList()) :
                Collections.singletonList(new SimpleGrantedAuthority(scope.toString()));

        return new User(username, "", authorities);
    }
    public String extractRole(String token) {
        if(claimsMap==null) verifySignedToken(token);
        return claimsMap.get("scope").toString();
    }
}
