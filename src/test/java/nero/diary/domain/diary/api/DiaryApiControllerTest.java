package nero.diary.domain.diary.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import nero.diary.domain.diary.dto.DiariesResponseDto;
import nero.diary.domain.diary.dto.DiaryWriteRequestDto;
import nero.diary.domain.diary.entity.Diary;
import nero.diary.domain.diary.service.DiaryService;
import nero.diary.domain.user.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.withSettings;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DiaryApiController.class)
@MockBean(JpaMetamodelMappingContext.class)
@Slf4j
class DiaryApiControllerTest {


    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    DiaryService diaryService;


    @DisplayName("POST diary api")
    @Test
    void write() throws Exception {
        // given
        User user = User.builder()
                .username("nero")
                .email("nero@gmail")
                .build();

        DiaryWriteRequestDto request = DiaryWriteRequestDto.builder()
                .name("메모용")
                .content("다이어리 컨텐츠")
                .user(user)
                .build();

        willDoNothing().given(diaryService).write(request);

        String json = objectMapper.writeValueAsString(request);

        // when
        ResultActions actions = mockMvc.perform(post("/api/v1/diary")
                .contentType(APPLICATION_JSON)
                .content(json));

        // then
        actions.andExpect(status().isOk())
                        .andDo(print());
    }

    @DisplayName("GET diary List API")
    @Test
    void diaryList() throws Exception {
        // given
        User user = User.builder()
                .username("nero")
                .email("nero@gmail")
                .build();

        Diary request = Diary.builder()
                .name("메모용")
                .content("다이어리 컨텐츠")
                .user(user)
                .build();

        List<Diary> diaryList = new ArrayList<>();

        diaryList.add(request);
        DiariesResponseDto response = DiariesResponseDto.of(diaryList);

        given(diaryService.findDiary(any(), any()))
                .willReturn(response);

        // when
        ResultActions actions = mockMvc.perform(get("/api/v1/diaries/{diaryName}", request.getName())
                .contentType(APPLICATION_JSON)
                .param("username", user.getUsername())
        );

        actions.andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.diaryResponseDtoList[0].name",is("메모용")))
                .andExpect(status().isOk())
                .andDo(print());
    }
}