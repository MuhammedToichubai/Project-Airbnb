package kg.airbnb.airbnb.security.jwt;

import io.jsonwebtoken.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@ConfigurationProperties(prefix = "app.jwt")
@Getter
@Setter
public class JwtUtils {
    private String secretWord;
    private long expiredAt;

    public void setExpiredAt(long expiredAt) {
        this.expiredAt = expiredAt * 24 * 60 * 60;
    }

    public String generateJwt(UserDetails userDetails) {

        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + expiredAt))
                .signWith(SignatureAlgorithm.HS512, secretWord)
                .compact();
    }

    public boolean isValidToken(String jwt)  {
        try {

            Jwts.parser().setSigningKey(secretWord).parseClaimsJws(jwt);

        } catch (ExpiredJwtException e) {
            throw new RuntimeException("JWT is expired!", e);
        } catch (UnsupportedJwtException e) {
            throw new UnsupportedJwtException("Unsupported JWT!");
        } catch (MalformedJwtException e) {
            e.printStackTrace();
            throw new MalformedJwtException("Malformed JWT!");
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Illegal Argument (JWT)!");
        }
        return true;
    }

    public String getEmailFromJwt(String jwt) {
        return Jwts.parser().setSigningKey(secretWord).parseClaimsJws(jwt).getBody().getSubject();
    }
}
