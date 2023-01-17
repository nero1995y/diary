package nero.diary.domain.diary.integration;

import lombok.extern.slf4j.Slf4j;
import nero.diary.domain.diary.dto.DiariesResponseDto;
import nero.diary.domain.diary.dto.DiaryWriteRequestDto;
import nero.diary.domain.diary.service.DiaryService;
import nero.diary.domain.user.entity.User;
import nero.diary.domain.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@PropertySource("classpath:application-integration.yml")
@Transactional
@Slf4j
public class DiaryIntegrationTest {

    @Autowired
    private DiaryService diaryService;

    @Autowired
    UserRepository userRepository;

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


    @DisplayName("test")
    @Test
    void find() {
        User save = getSave();

        DiaryWriteRequestDto request = write(save.getUsername());

        diaryService.write(request);

        DiariesResponseDto diary = diaryService.findDiary(request.getName(), save.getUsername());


        log.info(">>> {}",diary.getDiaryResponseDtoList());

    }

}
