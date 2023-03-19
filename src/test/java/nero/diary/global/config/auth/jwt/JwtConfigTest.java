package nero.diary.global.config.auth.jwt;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.security.Key;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
class JwtConfigTest {

    @Autowired
    private JwtConfig config;

    @MockBean
    UserDetailsService userDetailsService;

    @DisplayName("secretKey 생성")
    @Test
    void init() {
        config.init();
        Key key = config.getSecretKey();
        assertThat(key).isNotNull();
    }

    @DisplayName("토큰을 생성한다")
    @Test
    void createToken() {
        //given
        String testEmail = "tesEmail";

        //when
        String token = config.createToken(testEmail);

        //then
        assertThat(token).isNotEmpty();
    }

    @DisplayName("토큰을 검증한다")
    @Test
    void validateToken() {
        //given
        String testEmail = "tesEmail";
        String token = config.createToken(testEmail);

        //when
        boolean validateToken = config.validateToken("Bearer " + token, testEmail);

        //then
        assertThat(validateToken).isTrue();
    }

    @DisplayName("토큰을 검증이 실패한다")
    @Test
    void validateTokenFail() {
        //given
        String testEmail = "tesEmail";
        String fail = "tesEmails";

        String token = config.createToken(testEmail);

        //when
        boolean validateToken = config.validateToken("Bearer " + token, fail);

        //then
        assertThat(validateToken).isFalse();

    }
}