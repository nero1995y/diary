package nero.diary.domain.user.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import nero.diary.domain.user.dto.UserResponseDto;
import nero.diary.domain.user.dto.UserSaveRequestDto;
import nero.diary.domain.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.snippet.Attributes;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "api.nero.com", uriPort = 443)
@ExtendWith(RestDocumentationExtension.class)
public class UserApiControllerDOCTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @Test
    @DisplayName("단건 조회 문서")
    void singleSearchUser() throws Exception {
        // given
        UserSaveRequestDto request = UserSaveRequestDto.builder()
                .username("nerotestss")
                .email("test@gmail.com")
                .phone("01022423531")
                .password("12345")
                .build();

        userService.register(request);
        UserResponseDto response = userService.findUser(request.getUsername());

        // expected
        this.mockMvc.perform(get("/api/v1/user/{userId}", response.getId())
                        .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("user-inquiry", pathParameters(
                        parameterWithName("userId").description("유저 ID")
                        ),

                        responseFields(
                                fieldWithPath("id").description("유저 id"),
                                fieldWithPath("username").description("이름"),
                                fieldWithPath("email").description("이메일"),
                                fieldWithPath("phone").description("전화 번호"),
                                fieldWithPath("password").description("비밀번호")
                        )


                ));

    }

    @Test
    @DisplayName("유저 등록")
    void createUser() throws Exception {
        // given
        UserSaveRequestDto request = UserSaveRequestDto.builder()
                .username("nerotest")
                .email("test@gmail.com")
                .phone("01022423531")
                .password("12345")
                .build();

        String json = new ObjectMapper().writeValueAsString(request);


        // expected
        this.mockMvc.perform(post("/api/v1/user")
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("user-register",
                        requestFields(
                                fieldWithPath("username").description("이름").attributes(
                                        Attributes.key("constraint").value("이름을 입력하는 란")),
                                fieldWithPath("email").description("이메일"),
                                fieldWithPath("phone").description("전화 번호").optional(),
                                fieldWithPath("password").description("비밀번호")
                        )
                ));
    }
}
