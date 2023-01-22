package nero.diary.domain.diary.integration;

import lombok.extern.slf4j.Slf4j;
import nero.diary.domain.diary.dto.DiariesResponseDto;
import nero.diary.domain.diary.dto.DiaryResponseDto;
import nero.diary.domain.diary.dto.DiaryWriteRequestDto;
import nero.diary.domain.diary.entity.Diary;
import nero.diary.domain.diary.repository.DiaryRepository;
import nero.diary.domain.diary.service.DiaryService;
import nero.diary.domain.user.dto.user.UserResponseDto;
import nero.diary.domain.user.entity.User;
import nero.diary.domain.user.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles({"integration"})
@Slf4j
public class DiaryIntegrationTest {

    @Autowired
    private DiaryService diaryService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    DiaryRepository diaryRepository;

    @BeforeEach
    public void cleanup() {
        diaryRepository.deleteAll();
        userRepository.deleteAll();
    }


    private User getSave() {

        return userRepository.save(
                User.builder()
                        .email("nero1995y@gmail.com")
                        .username("nero")
                        .build());
    }

    private User getSave2() {

        return userRepository.save(
                User.builder()
                        .email("nero1995y2@gmail.com")
                        .username("nero2")
                        .build());
    }

    private DiaryWriteRequestDto write(String username) {

        return DiaryWriteRequestDto.builder()
                .name("메모테스트")
                .content("컨텐츠")
                .username(username)
                .build();
    }

    private DiaryWriteRequestDto write2(String username) {

        return DiaryWriteRequestDto.builder()
                .name("메모테스트2")
                .content("컨텐츠2")
                .username(username)
                .build();
    }

    @DisplayName("저장이 실제로 저장한다.")
    @Test
    @Rollback(value = false)
    void find() {
        User save = getSave();

        DiaryWriteRequestDto request = write(save.getUsername());

        diaryService.write(request);

        DiariesResponseDto diary = diaryService.findDiary(request.getName(), save.getUsername());

    }

    @DisplayName("유저이름과 제목으로 찾는다")
    @Test
    @Rollback(value = false)
    void findDiaryName() {

        // given
        User user = getSave();
        User user2 = getSave2();

        DiaryWriteRequestDto request = write(user.getUsername());
        DiaryWriteRequestDto request2 = write2(user2.getUsername());

        diaryService.write(request);
        diaryService.write(request2);

        // when
        DiariesResponseDto diary = diaryService.findDiary(request.getName(), user.getUsername());

        List<String> diaries = diary.getDiaryResponseDtoList()
                .stream()
                .map(DiaryResponseDto::getName)
                .collect(Collectors.toList());

        assertThat(diaries).contains(request.getName());
        assertThat(diaries.size()).isEqualTo(1);

    }
}
