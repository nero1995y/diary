package nero.diary.domain.diary.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import nero.diary.domain.diary.dto.diary.DiariesResponseDto;
import nero.diary.domain.diary.dto.diary.DiaryResponseDto;
import nero.diary.domain.diary.dto.diary.DiaryUpdateRequestDto;
import nero.diary.domain.diary.dto.diary.DiaryWriteRequestDto;
import nero.diary.domain.diary.entity.Diary;
import nero.diary.domain.diary.service.DiaryQueryService;
import nero.diary.domain.diary.service.DiaryService;
import nero.diary.domain.user.entity.Role;
import nero.diary.domain.user.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DiaryApiController.class)
@MockBean(JpaMetamodelMappingContext.class)
@Slf4j
class DiaryApiControllerTest {


    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    DiaryService diaryService;

    @MockBean
    DiaryQueryService diaryQueryService;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }


    @DisplayName("POST 다이어리 등록한다.")
    @Test
    @WithMockUser(roles = "USER")
    void write() throws Exception {
        // given
        User user = User.builder()
                .username("nero")
                .email("nero@gmail")
                .role(Role.USER)
                .build();

        DiaryWriteRequestDto request = DiaryWriteRequestDto.builder()
                .name("메모용")
                .content("다이어리 컨텐츠")
                .user(user)
                .build();

        willDoNothing().given(diaryService).write(request);

        String json = objectMapper.writeValueAsString(request);

        // when
        ResultActions actions = mockMvc.perform(post("/api/v2/diary")
                .contentType(APPLICATION_JSON)
                .with(csrf())
                .content(json));

        // then
        actions.andExpect(status().isOk())
                        .andDo(print());
    }

    @DisplayName("GET 다이어리 리스트를 조회한다.")
    @Test
    @WithMockUser(roles = "USER")
    void diaryList() throws Exception {
        // given
        User user = User.builder()
                .username("nero")
                .email("nero@gmail")
                .role(Role.USER)
                .build();

        Diary request = Diary.builder()
                .name("메모용")
                .content("다이어리 컨텐츠")
                .user(user)
                .build();
        DiaryResponseDto dto = new DiaryResponseDto(request);

        List<DiaryResponseDto> diaryList = new ArrayList<>();

        diaryList.add(dto);

        //Page<DiaryResponseDto> diaries
        DiariesResponseDto response = DiariesResponseDto.of(new PageImpl<>(diaryList));

        given(diaryQueryService.findDiaryByUsername(any(), any()))
                .willReturn(response);

        String json = objectMapper.writeValueAsString(request);

        // when
        ResultActions actions = mockMvc.perform(get("/api/v2/diaries")
                .contentType(APPLICATION_JSON)
                .content(json)
        );

        actions.andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.diaryResponseDtoList[0].name",is("메모용")))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("GET 다이어리 단건을 조회 한다.")
    @Test
    @WithMockUser(roles = "USER")
    void findById() throws Exception {
        // given
        User user = User.builder()
                .username("nero")
                .email("nero@gmail.com")
                .role(Role.USER)
                .build();

        Diary request = Diary.builder()
                .id(1L)
                .name("메모용")
                .content("다이어리 컨텐츠")
                .user(user)
                .build();

        DiaryResponseDto dto = new DiaryResponseDto(request);

        given(diaryQueryService.findDiary(request.getId(), user.getEmail()))
                .willReturn(dto);

        // when
        ResultActions actions = mockMvc.perform(get("/api/v2/diary/{id}", 1L)
                .param("userEmail", user.getEmail())
                .contentType(APPLICATION_JSON));

        // then
        actions.andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andDo(print());
    }

    @DisplayName("PATCH 다이어리를 업데이트 한다.")
    @Test
    @WithMockUser(roles = "USER")
    void update() throws Exception {
        // given
        DiaryUpdateRequestDto request = DiaryUpdateRequestDto.builder()
                .userEmail("nero1995y@gmail.com")
                .name("TestTitle")
                .content("TestContent")
                .build();

        String json = objectMapper.writeValueAsString(request);

        // when
        ResultActions actions = mockMvc.perform(patch("/api/v2/diary/{id}", 1L)
                .with(csrf())
                .content(json)
                .contentType(APPLICATION_JSON));
        // then
        actions.andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("delete 다이어리를 삭제 한다.")
    @Test
    @WithMockUser(roles = "USER")
    void deleteTest() throws Exception{
        // given
        Long diaryId = 1L;
        String  userEmail = "test@email.com";

        // when
        mockMvc.perform(delete("/api/v2/diary/{id}", diaryId)
                .param("userEmail", userEmail)
                .with(csrf()))

        // then
                .andExpect(status().is2xxSuccessful())
                .andDo(print());

        verify(diaryService, times(1)).delete(any(), any());
    }

}