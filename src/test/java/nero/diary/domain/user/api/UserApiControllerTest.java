package nero.diary.domain.user.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import nero.diary.domain.user.dto.user.UserResponseDto;
import nero.diary.domain.user.dto.user.UserSaveRequestDto;
import nero.diary.domain.user.dto.user.UserUpdateRequestDto;
import nero.diary.domain.user.entity.User;
import nero.diary.domain.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

;
@WebMvcTest(UserApiController.class)
@MockBean(JpaMetamodelMappingContext.class)
@Slf4j
class UserApiControllerTest {


    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;


    @DisplayName("POST user API")
    @Test
    @WithMockUser(roles = "USER")
    void createUser() throws Exception{
        // given
        UserSaveRequestDto request = UserSaveRequestDto.builder()
                .username("nero")
                .password("12345")
                .email("wnsgur765z@naver.com")
                .picture("testPicture")
                .build();

        willDoNothing().given(userService).register(request);

        String json = objectMapper.writeValueAsString(request);

        // when
        ResultActions actions = mockMvc.perform(post("/api/v1/user")
                        .header("authorization", "nero")
                        .contentType(APPLICATION_JSON)
                        .with(csrf())
                        .content(json));

        // then
        actions.andExpect(status().isOk())
                .andDo(print());

        verify(userService).register(any());

    }

    @DisplayName("GET user API 단건")
    @Test
    @WithMockUser(roles = "USER")
    void findSingleUser() throws Exception {
        // given
        User userEntity = User.builder()
                .username("nero")
                .email("wnsgur765z@naver.com")
                .picture("testPicture")
                .build();

        UserResponseDto userResponseDto = new UserResponseDto(userEntity);

        given(userService.findUserId(userResponseDto.getId()))
                .willReturn(userResponseDto);

        // when
        ResultActions actions = mockMvc.perform(get("/api/v1/user/{userId}", 100L)
                .contentType(APPLICATION_JSON));

        // then
        actions.andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("PATCH user API")
    @Test
    @WithMockUser(roles = "USER")
    void updateUser() throws Exception {
        // given
        UserUpdateRequestDto request = UserUpdateRequestDto.builder()
                .username("nero")
                .email("wnsgur765z@naver.com")
                .picture("testPicture")
                .build();

        mockMvc.perform(patch("/api/v1/user/{id}", 1L)
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .with(csrf()))
                .andExpect(status().isOk())
                .andDo(print());
    }

}