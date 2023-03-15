package nero.diary.global.config.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtConfig {
    private Key secretKey;
    @Value("${JWT_EXPIRE_TIME}")
    private long expireTime;

    @PostConstruct
    protected void init() {
        this.secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }

    public String createToken(String userEmail, List<String> roleList) {

        return Jwts.builder()
                .setClaims(getClaims(userEmail, roleList))
                .setSubject(userEmail)
                .setIssuedAt(getNowDate())
                .setExpiration(new Date(getNowDate().getTime() + expireTime))
                .signWith(secretKey)
                .compact();
    }

    private Claims getClaims(String userEmail, List<String> roleList) {
        Claims claims = Jwts.claims().setSubject(userEmail);
        claims.put("roles", roleList);
        return claims;
    }

    private Date getNowDate() {
        return new Date();
    }

    public boolean validateToken(String token, String userEmail) {

        if (!isBearer(token)) {
            return isBearer(token);
        }

        return !Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token.split(" ")[1])
                .getBody()
                .getSubject()
                .equals(userEmail);
    }

    private boolean isBearer(String token) {
        return token.startsWith("Bearer ");
    }

}
