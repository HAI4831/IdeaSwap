package nvh.run.ideaswap.common.security.jwt;

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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.PrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

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

            JWTClaimsSet claims = new JWTClaimsSet.Builder()
                    .subject(userDetails.getUsername())
                    .issuer(userDetails.getUsername())
                    .issueTime(new Date())
                    .expirationTime(Date.from(Instant.now().plus(validDuration, ChronoUnit.SECONDS)))
                    .jwtID(UUID.randomUUID().toString())
                    .claim("tokenType", tokenType)
                    .claim("scope", buildScope(userDetails))
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
//________________________________________________________________

//    public Date extractExpiration(String token) { return extractClaim(token, Claims::getExpiration); }
//    public Boolean isTokenExpired(String token) {
//        return extractExpiration(token).before(new Date());
//    }
//    public String getUsernameFromToken(String token) {
//        try {
//            SignedJWT signedJWT = SignedJWT.parse(token);
//            return signedJWT.getJWTClaimsSet().getSubject();
//        } catch (Exception e) {
//            log.error("Failed to extract username from token", e);
//            throw new RuntimeException("Invalid token", e);
//        }
//    }

//    public Authentication getAuthentication(String token, UserDetails userDetails) {
//        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//    }

    public String extractUsername(String token) {
        return claimsMap.get("sub").toString();
    }


//    public Claims extractAllClaims(String token) {
//        try {
//            //chuyển token thành JWT đã ký
//            SignedJWT signedJWT = SignedJWT.parse(token);
//
//            // Verify the JWT using the JWSVerifier
//            if (verifySignedToken(token) && !isTokenExpired(token)) {
//                 = signedJWT.getJWTClaimsSet().getClaims();
//                return Jwts.claims(claimsMap);  // Convert Map to Claims
//                //                return signedJWT.getJWTClaimsSet().getClaims();//return Map<String, Object>
//            } else {
//                throw new RuntimeException("Token signature verification failed");
//            }
//        } catch (Exception e) {
//            log.error("Error extracting claims from JWT", e);
//            throw new RuntimeException("Error extracting claims", e);
//        }
//    }
//
//    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
//        final Claims claims = extractAllClaims(token);
//        return claimsResolver.apply(claims);
//    }
//    public boolean validateToken(String token, UserDetails userDetails) {
//        try {
//            SignedJWT signedJWT = SignedJWT.parse(token);
//            return !isTokenExpired(signedJWT) && signedJWT.getJWTClaimsSet().getSubject().equals(userDetails.getUsername());
//        } catch (Exception e) {
//            log.error("Failed to validate token", e);
//            return false;
//        }
//    }
//    public Boolean validateTokenForUserDetails(String token, UserDetails userDetails) {
//        final String email = extractUsername(token);
//        return (email.equals(userDetails.getUsername()) && !isTokenExpired(token) && verifySignedToken(token));
//    }
//    public boolean validateToken(String token) {
//        try {
//            if(verifySignedToken(token) && !isTokenExpired(token)){
//                claimsMap=extractAllClaims(token);
//            }
//            return true ;
//        } catch (MalformedJwtException e) {
//            log.info("Invalid JWT token.");
//            log.trace("Invalid JWT token trace: {}", e);
//        } catch (ExpiredJwtException e) {
//            log.info("Expired JWT token.");
//            log.trace("Expired JWT token trace: {}", e);
//        } catch (UnsupportedJwtException e) {
//            log.info("Unsupported JWT token.");
//            log.trace("Unsupported JWT token trace: {}", e);
//        } catch (IllegalArgumentException e) {
//            log.info("JWT token compact of handler are invalid.");
//            log.trace("JWT token compact of handler are invalid trace: {}", e);
//        }
//        return false;
//    }
//
    public String getToken (HttpServletRequest httpServletRequest) {
        final String bearerToken = httpServletRequest.getHeader("Authorization");
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer "))
        {return bearerToken.substring(7); } // The part after "Bearer "
        return null;
    }
}
