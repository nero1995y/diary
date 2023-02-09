package nero.diary.domain.diary.service;

import nero.diary.domain.diary.dto.DiariesResponseDto;
import nero.diary.domain.diary.dto.DiaryResponseDto;
import nero.diary.domain.diary.dto.DiaryWriteRequestDto;
import nero.diary.domain.diary.dto.search.DiarySearchCondition;
import nero.diary.domain.diary.entity.Diary;
import nero.diary.domain.diary.repository.DiaryRepository;
import nero.diary.domain.user.entity.User;
import nero.diary.domain.user.service.UserService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DiaryServiceTest {

    @Mock(name = "diaryRepository")
    DiaryRepository diaryRepository;

    @Mock(name = "userService")
    UserService userService;

    @InjectMocks
    @Spy
    DiaryService diaryService;

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

    @DisplayName("다이어리를 이름으로 찾는다")
    @Test
    @Disabled
    void find() {
        // given
        DiaryWriteRequestDto request = DiaryWriteRequestDto.builder()
                .name("testTitle")
                .content("테스트 내용입니다")
                .build();

        User user = User.builder().username("nero").build();

        DiarySearchCondition condition = new DiarySearchCondition(request.getName(), "","");

        Pageable pageable = PageRequest.of(0, 1);

        // when
        diaryService.findDiaryByUsername(condition, pageable);

        // then
        verify(diaryRepository, times(1)).search(
                condition, null);
    }
}
