package nero.diary.global.config.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
@Getter
@Slf4j
public class JwtConfig {
    private SecretKey secretKey;
    private final static String KEY = "9vQZSRBvJSowzIVIwqsa9cBvncsQkMMnLOa3z3r4Eek=";
    private final long expireTime = 6048000L;

    @PostConstruct
    protected void init() {
        this.secretKey = Keys.hmacShaKeyFor(Base64.getDecoder().decode(KEY));
    }

    public String createToken(String userEmail) {

        return Jwts.builder()
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
        return Jwts.parserBuilder()
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
