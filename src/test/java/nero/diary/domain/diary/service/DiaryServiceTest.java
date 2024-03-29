package nero.diary.domain.diary.service;

import lombok.extern.slf4j.Slf4j;
import nero.diary.domain.diary.dto.diary.DiaryUpdateRequestDto;
import nero.diary.domain.diary.dto.diary.DiaryWriteRequestDto;
import nero.diary.domain.diary.entity.Diary;
import nero.diary.domain.diary.repository.DiaryRepository;
import nero.diary.domain.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@Slf4j
class DiaryServiceTest {
    @InjectMocks
    @Spy
    DiaryService diaryService;

    @Mock
    DiaryRepository diaryRepository;

    @Mock
    DiaryQueryService diaryQueryService;

    @Mock
    UserService userService;

    @DisplayName("다이어리를 등록한다")
    @Test
    void write() {
        // given
        DiaryWriteRequestDto request = DiaryWriteRequestDto.builder()
                .name("testTitle")
                .content("테스트 내용입니다")
                .build();

        when(diaryRepository.save(any())).thenReturn(request.toEntity());

        // when
        diaryService.write(request);

        // then
        verify(diaryRepository, times(1)).save(any());
    }

    @DisplayName("다이어리를 업데이트 한다")
    @Test
    void update() {
        //given
        DiaryUpdateRequestDto request = DiaryUpdateRequestDto.builder()
                .name("updateTitle")
                .content("updateContent")
                .build();

        Diary diary = Diary.builder()
                .name("testDiary")
                .content("testContent")
                .build();

        doReturn(diary)
                .when(diaryQueryService)
                .findById(any());

        //when
        diaryService.update(1L, request);

        //then
        verify(diaryQueryService, times(1)).findById(any());
        assertThat(diary.getName()).isEqualTo(request.getName());
    }

    @DisplayName("다이어리를 삭제한다")
    @Test
    void delete() {
        //given
        Long diaryId = 1L;
        String userEmail = "test@gmail.com";

        Diary diary = Diary.builder()
                .name("testDiary")
                .content("testContent")
                .build();

        given(diaryRepository.findById(diaryId))
                .willReturn(Optional.of(diary));

        //when
        diaryService.delete(diaryId, userEmail);

        //then
        verify(diaryRepository, times(1)).delete(diary);
    }
}
