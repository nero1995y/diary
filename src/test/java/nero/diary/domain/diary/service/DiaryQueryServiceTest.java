package nero.diary.domain.diary.service;

import lombok.extern.slf4j.Slf4j;
import nero.diary.domain.diary.dto.diary.DiaryResponseDto;
import nero.diary.domain.diary.dto.search.DiarySearchCondition;
import nero.diary.domain.diary.entity.Diary;
import nero.diary.domain.diary.repository.DiaryRepository;
import nero.diary.domain.user.entity.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
@Slf4j
class DiaryQueryServiceTest {

    @Mock
    DiaryRepository diaryRepository;

    @Spy
    @InjectMocks
    DiaryQueryService diaryQueryService;

    @DisplayName("다이어리 단건 조회 한다")
    @Test
    void findById() {
        // given
        Long diaryId = 1L;
        String userEmail = "testEmail@gmail.com";

        Diary diary = Diary.builder()
                .name("testDiary")
                .content("testContent")
                .build();

        given(diaryRepository.findById(diaryId))
                .willReturn(Optional.ofNullable(diary));

        willDoNothing().given(diaryQueryService)
                .findUserByEmail(userEmail);

        // when
        DiaryResponseDto response = diaryQueryService.findById(diaryId, userEmail);

        // then
        Assertions.assertThat(response.getContent()).isEqualTo(diary.getContent());
        verify(diaryRepository, times(1)).findById(any());
    }


    @Test
    void find() {
        // given
        User user = User.builder().username("nero").build();

        Diary diary = Diary.builder()
                .name("testTitle")
                .content("test 내용")
                .user(user)
                .build();

        DiarySearchCondition condition = new DiarySearchCondition(diary.getName(), "", "");
        Pageable pageable = PageRequest.of(0, 1);

        List<DiaryResponseDto> items = new ArrayList<>();
        items.add(new DiaryResponseDto(diary));

        when(diaryRepository.search(any(), any())).thenReturn(new PageImpl<>(items, pageable, 10));

        // when
        diaryQueryService.findDiaryByUsername(condition, pageable);

        // then
        verify(diaryRepository, times(1)).search(
                any(), any());

    }
}