package tr.com.nebildev.springsecurityjwt.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class JwtTokenProvider {

    private static final String CLAIM_NAME = "roles";

    @Value("${app.jwt.token.secret.access}")
    private String accessSecret;

    @Value("${app.jwt.token.expiration-in-minute.access}")
    private long accessTokenExpirationInMinutes;
    @Value("${app.jwt.token.expiration-in-minute.refresh}")
    private long refreshTokenExpirationInMinutes;

    public Algorithm getAccessTokenAlgorithm() {
        return Algorithm.HMAC256(accessSecret.getBytes());
    }

    public String createAccessToken(String username, Collection<? extends GrantedAuthority> authorities, String uri) {
        return JWT.create()
                .withSubject(username)
                .withExpiresAt(new Date(System.currentTimeMillis() + accessTokenExpirationInMinutes * 60 * 1000))
                .withIssuer(uri)
                .withClaim(CLAIM_NAME, authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(this.getAccessTokenAlgorithm());
    }

    public String createRefreshToken(String username, String uri) {
        return JWT.create()
                .withSubject(username)
                .withExpiresAt(new Date(System.currentTimeMillis() + refreshTokenExpirationInMinutes * 60 * 1000))
                .withIssuer(uri)
                .sign(this.getAccessTokenAlgorithm());
    }

    private DecodedJWT getDecodedJwtValues(String token) {
        JWTVerifier jwtVerifier = JWT.require(this.getAccessTokenAlgorithm()).build();

        try {
            return jwtVerifier.verify(token);
        } catch (JWTVerificationException e) {
            log.error("Token decoded failed, token : " + token + "  | error : " + e.getMessage());
            throw e;
        }
    }

    private String getUsername(DecodedJWT decodedJwtValues) {
        return decodedJwtValues.getSubject();
    }

    public List<SimpleGrantedAuthority> getGrantedAuthorityList(DecodedJWT decodedJwtValues) {
        final List<String> roles = decodedJwtValues.getClaim(CLAIM_NAME).asList(String.class);
        return roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    public String getRefreshTokenUsername(String token) {
        final DecodedJWT decodedJwtValues = getDecodedJwtValues(token);
        return this.getUsername(decodedJwtValues);
    }

    public UsernamePasswordAuthenticationToken verifyAccessToken(String token) {
        final DecodedJWT decodedJwtValues = getDecodedJwtValues(token);
        final String username = this.getUsername(decodedJwtValues);
        final List<SimpleGrantedAuthority> grantedAuthorityList = getGrantedAuthorityList(decodedJwtValues);
        return new UsernamePasswordAuthenticationToken(username, null, grantedAuthorityList);
    }

    public void verifyRefreshToken(String token){
        getDecodedJwtValues(token);
    }

    public Optional<String> verifyHeader(String authenticationHeader) {
        if (authenticationHeader != null && authenticationHeader.startsWith("Bearer ")) {
            return Optional.of(authenticationHeader.substring("Bearer ".length()));
        }
        return Optional.empty();
    }


}
