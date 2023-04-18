package nero.diary.domain.user.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import nero.diary.domain.user.service.auth.AuthService;
import nero.diary.global.config.auth.jwt.JwtConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthApiController.class)
@MockBean(JpaMetamodelMappingContext.class)
@Slf4j
class AuthApiControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @MockBean(name = "jwtConfig")
    private JwtConfig jwtConfig;

    @MockBean(name = "authService")
    private AuthService authService;

    @BeforeEach
    void setUp() {
        MockMvcBuilders.webAppContextSetup(context)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }


    @DisplayName("본인 인증 null 문자열이 올때 응답값이 401이다.")
    @Test
    @WithMockUser(roles = "USER")
    void me401() throws Exception {
        //given
        String authorization = "null";
        String userEmail = "null";

        //when
        ResultActions actions = mockMvc.perform(get("/api/auth/me/{userEmail}", userEmail)
                .header("Authorization", authorization)
                .contentType(MediaType.APPLICATION_JSON));

        //then
        actions.andExpect(status().is4xxClientError())
                .andDo(print());
    }

    @DisplayName("본인 인증할때 올바르게 이메일을 전달한다")
    @Test
    @WithMockUser(roles = "USER")
    void meSucceed() throws Exception {
        //given
        String authorization = "Bearer test";
        String userEmail = "wnsgur765z1@naver.com";

        given(jwtConfig.validateToken(any(), any()))
                .willReturn(true);

        //when
        ResultActions actions = mockMvc.perform(get("/api/auth/me/{userEmail}", userEmail)
                .header("Authorization", authorization)
                .contentType(MediaType.APPLICATION_JSON));

        //then
        actions.andExpect(status().isOk())
                .andDo(print());
    }
}