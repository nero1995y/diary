package nero.diary.domain.user.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import nero.diary.domain.user.dto.login.LoginRequestDto;
import nero.diary.domain.user.dto.login.SessionResponseDto;
import nero.diary.domain.user.dto.user.UserSaveRequestDto;
import nero.diary.domain.user.service.UserService;
import nero.diary.domain.user.service.login.AuthService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthApiController.class)
@Slf4j
class AuthApiControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private AuthService authService;

    private UserSaveRequestDto getUserSaveRequestDto() {
        return UserSaveRequestDto.builder()
                .username("nero")
                .email("wnsgur123@snaver.com")
                .phone("0102242")
                .password("12345")
                .build();
    }

    @DisplayName("POST auth API")
    @Test
    void login() throws Exception{
        // given
        UserSaveRequestDto request = getUserSaveRequestDto();

        LoginRequestDto loginRequestDto = LoginRequestDto.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .build();

        String accessToken = UUID.randomUUID().toString();

        willDoNothing().given(userService).register(request);

        given(authService.login(any()))
                .willReturn(new SessionResponseDto(accessToken));

        String json = objectMapper.writeValueAsString(loginRequestDto);

        // when
        ResultActions actions = mockMvc.perform(post("/auth/login")
                .contentType(APPLICATION_JSON)
                .content(json));

        // then
        actions.andExpect(status().isOk())
                .andDo(print());

        verify(authService).login(any());
    }

}