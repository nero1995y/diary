package nero.diary.domain.diary.repository.custom;

import nero.diary.domain.diary.dto.diary.DiaryResponseDto;
import nero.diary.domain.diary.dto.search.DiarySearchCondition;
import nero.diary.domain.diary.entity.Diary;
import nero.diary.domain.diary.repository.DiaryRepository;
import nero.diary.domain.user.entity.Role;
import nero.diary.domain.user.entity.User;
import nero.diary.domain.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
class DiaryRepositoryImplTest {

    @Autowired
    DiaryRepository diaryRepository;

    @Autowired
    UserRepository userRepository;

    @DisplayName("custom 동적쿼리 조회 한다")
    @Test
    void search() {
        // given
        User user = User.builder()
                .username("nero")
                .email("test@gmail.com")
                .picture("testUrl")
                .role(Role.USER)
                .build();

        userRepository.save(user);

        Diary diaryEntity = Diary.builder()
                .name("testDiaryName")
                .content("test content")
                .user(user)
                .build();

        Diary save = diaryRepository.save(diaryEntity);

        DiarySearchCondition condition =
                new DiarySearchCondition("", "", user.getEmail());
        Pageable pageable = PageRequest.of(0, 1);

        // then
        Page<DiaryResponseDto> dto = diaryRepository.search(condition, pageable);

        // when
        List<String> dtoNameList = dto.stream()
                .map(DiaryResponseDto::getName)
                .collect(Collectors.toList());

        List<LocalDateTime> times = dto.stream()
                .map(DiaryResponseDto::getModifiedDate)
                .collect(Collectors.toList());


        assertThat(dtoNameList).containsExactlyInAnyOrder(diaryEntity.getName());
        assertThat(times).isNotNull();
    }
}