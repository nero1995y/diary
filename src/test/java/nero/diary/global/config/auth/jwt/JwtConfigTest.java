package nero.diary.global.config.auth.jwt;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

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
        String key = "6R6yuErcTCdNW7APsxJ2wPkCcJMVmtoC";
        String secretKey = Base64.getEncoder().encodeToString(key.getBytes());

        log.info(">>> {}",secretKey);

        assertThat(secretKey).isNotEmpty();
    }

    @DisplayName("토큰을 생성한다")
    @Test
    void createToken() {
        //given
        String testEmail = "tesEmail";
        List<String> roleList = new ArrayList<>();
        roleList.add("USER");

        //when
        String token = config.createToken(testEmail, roleList);

        //then
        assertThat(token).isNotEmpty();
    }


}