package nero.diary.global.config.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtConfig {
    private SecretKey secretKey;
    @Value("${JWT_EXPIRE_TIME}")
    private long expireTime;

    @PostConstruct
    protected void init() {
        this.secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    }

    public String createToken(String userEmail, List<String> roleList) {

        return Jwts.builder()
                .setClaims(getClaims(userEmail, roleList))
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


    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }


    public boolean validateToken(String token) {
        return token.startsWith("Bearer ");
    }

}
