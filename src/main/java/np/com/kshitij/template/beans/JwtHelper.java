package np.com.kshitij.template.beans;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@Component
@Configuration
@PropertySource("classpath:security.properties")
public class JwtHelper {
    public static final String ROLES = "roles";
    @Value("${jwt.secret:77AUGKQB}")
    private String secret;

    @Value("${jwt.access.token.expiry.seconds:84600}")
    private long accessTokenExpirySeconds;

    @Value("${jwt.access.token.expiry.seconds:8460000}")
    private long refreshTokenExpirySeconds;

    public Algorithm getAlgorithm() {
        return Algorithm.HMAC256(StringUtils.getBytes(secret, StandardCharsets.UTF_8));
    }

    public UsernamePasswordAuthenticationToken verifyToken(String token) {
        JWTVerifier verifier = JWT.require(getAlgorithm()).build();
        DecodedJWT decodedJWT = verifier.verify(token);
        String userId = decodedJWT.getSubject();
        String[] roles = decodedJWT.getClaim(ROLES).asArray(String.class);
        List<SimpleGrantedAuthority> authorities =
                Arrays.stream(roles).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        return new UsernamePasswordAuthenticationToken(UUID.fromString(userId), null, authorities);
    }

    public String generateAccessToken(String userId, Collection<GrantedAuthority> authorities, String issuer) {
        return JWT.create()
                .withSubject(userId)
                .withExpiresAt(new Date(System.currentTimeMillis() + accessTokenExpirySeconds * 1000))
                .withIssuer(issuer)
                .withClaim(
                        ROLES,
                        authorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .sign(getAlgorithm());
    }

    public String generateRefreshToken(String userId, Collection<GrantedAuthority> authorities, String issuer) {
        return JWT.create()
                .withSubject(userId)
                .withExpiresAt(new Date(System.currentTimeMillis() + refreshTokenExpirySeconds * 1000))
                .withIssuer(issuer)
                .withClaim(
                        ROLES,
                        authorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .sign(getAlgorithm());
    }
}
