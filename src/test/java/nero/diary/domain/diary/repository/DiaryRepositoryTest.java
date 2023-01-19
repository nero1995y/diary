package nero.diary.domain.diary.repository;

import lombok.extern.slf4j.Slf4j;
import nero.diary.domain.diary.entity.Diary;
import nero.diary.domain.diary.exception.DiaryNotFoundException;
import nero.diary.domain.user.entity.User;
import nero.diary.domain.user.exception.UserNotFoundException;
import nero.diary.domain.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Slf4j
class DiaryRepositoryTest {

    @Autowired
    DiaryRepository diaryRepository;

    @Autowired
    UserRepository userRepository;

    @DisplayName("diary 생성한다")
    @Test
    void save() {

        // given
        User user = User.builder()
                .username("nero")
                .email("test@gmail.com")
                .phone("01022423531")
                .password("12345")
                .build();

        userRepository.save(user);

        Diary diaryEntity = Diary.builder()
                .name("testName")
                .user(user)
                .build();

        // then
        Diary save = diaryRepository.save(diaryEntity);


        // when
        Diary diaryFind = diaryRepository.findById(save.getId())
                .orElseThrow(DiaryNotFoundException::new);

        assertThat(diaryFind.getName()).isEqualTo(diaryEntity.getName());
    }

    @DisplayName("diary 이름으로 조회한다.")
    @Test
    void findDiary() {
        // given
        User user = User.builder()
                .username("nero")
                .email("test@gmail.com")
                .phone("01022423531")
                .password("12345")
                .build();

        Diary diaryEntity = Diary.builder()
                .name("testName")
                .content("testContent")
                .user(user)
                .build();

        userRepository.save(user);
        diaryRepository.save(diaryEntity);

        User userFind = userRepository.findByUsername(user.getUsername())
                .orElseThrow(UserNotFoundException::new);
        // when
        List<Diary> diaries = diaryRepository.findByNameAndUser(
                        diaryEntity.getName(), userFind)
                .orElseThrow(DiaryNotFoundException::new);

        // then
        assertThat(diaries).contains(diaryEntity);

    }

    @DisplayName("diary list 조회한다")
    @Test
    void findDiaryList() {
        // given
        User user = User.builder()
                .username("nero")
                .email("test@gmail.com")
                .phone("01022423531")
                .password("12345")
                .build();

        Diary diaryEntity = Diary.builder()
                .name("testName")
                .user(user)
                .build();

        Diary diaryEntity2 = Diary.builder()
                .name("testName2")
                .user(user)
                .build();

        userRepository.save(user);
        diaryRepository.save(diaryEntity);
        diaryRepository.save(diaryEntity2);

        // when
        List<Diary> diaryList = diaryRepository.findAll();

        assertThat(diaryList).contains(diaryEntity,diaryEntity2);
    }

    @DisplayName("diary 삭제한다")
    @Test
    void deleteDiary() {
        // given
        User user = User.builder()
                .username("nero")
                .email("test@gmail.com")
                .phone("01022423531")
                .password("12345")
                .build();

        Diary diaryEntity = Diary.builder()
                .name("testName")
                .user(user)
                .build();

        userRepository.save(user);
        diaryRepository.save(diaryEntity);

        User userFind = userRepository.findByUsername(user.getUsername())
                .orElseThrow(UserNotFoundException::new);


        List<Diary> diaries = diaryRepository.findByNameAndUser(
                        diaryEntity.getName()
                        , userFind)
                .orElseThrow(DiaryNotFoundException::new);

        // when
        diaryRepository.deleteAll(diaries);


        // then
        boolean isNullEntity = diaryRepository.findById(diaryEntity.getId()).isPresent();

        assertThat(isNullEntity).isEqualTo(false);

    }

    @DisplayName("유저이름으로 다이어리를 찾는다")
    @Test
    void findByUser() {
        // given
        User user = User.builder()
                .username("nero")
                .email("test@gmail.com")
                .phone("01022423531")
                .password("12345")
                .build();

        Diary diaryEntity = Diary.builder()
                .name("testName")
                .content("testContent")
                .user(user)
                .build();

        Diary diaryEntity2 = Diary.builder()
                .name("testName2")
                .content("testContent2")
                .user(user)
                .build();

        User saveUser = userRepository.save(user);
        diaryRepository.save(diaryEntity);
        diaryRepository.save(diaryEntity2);

        // when
        List<Diary> diaries = diaryRepository.findByUser(saveUser)
                .orElseThrow(DiaryNotFoundException::new);

        assertThat(diaries).contains(diaryEntity, diaryEntity2);
    }
}