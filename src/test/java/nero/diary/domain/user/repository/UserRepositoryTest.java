package nero.diary.domain.user.repository;

import nero.diary.domain.user.entity.User;
import nero.diary.domain.user.exception.UserNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @AfterEach
    public void cleanup() {
        userRepository.deleteAll();
    }

    @DisplayName("저장한다 유저를")
    @Test
    void save() {
        // given
        User user = User.builder().username("nero").build();

        // when
        User saveUser = userRepository.save(user);

        // then
        Optional<User> findUser = userRepository.findById(saveUser.getId());

        String findusername = findUser.orElseThrow(UserNotFoundException::new).getUsername();


        assertThat(saveUser.getUsername()).isEqualTo(findusername);
    }

    @DisplayName("불러온다 유저를")
    @Test
    void findUser() {
        // given
        User user = User.builder().username("nero").build();

        User saveUser = userRepository.save(user);

        // when
        Optional<User> findUser = userRepository.findById(saveUser.getId());

        String findUsername = findUser.orElseThrow(UserNotFoundException::new).getUsername();

        // then
        assertThat(findUsername).isEqualTo(saveUser.getUsername());

    }

    @DisplayName("불러온다 유저들을")
    @Test
    void findUsers() {

        // given
        User user1 = User.builder().username("nero").build();
        User user2 = User.builder().username("nero2").build();

        userRepository.save(user1);
        userRepository.save(user2);

        // when
        List<User> userList = userRepository.findAll();

        assertThat(userList).contains(user1, user2);
    }

    @DisplayName("삭제한다 유저를")
    @Test
    void deleteUser() {

        // given
        User user = User.builder().username("nero").build();
        User saveUser = userRepository.save(user);

        User findUser = userRepository
                .findById(saveUser.getId())
                .orElseThrow(UserNotFoundException::new);

        // when
        userRepository.delete(findUser);

        // then
        boolean present = userRepository.findById(saveUser.getId()).isPresent();
        assertThat(present).isEqualTo(false);
    }


    @DisplayName("이메일과 비밀번호를 조회한다 유저의")
    @Test
    void findByEmailAndPassword() {
        // given
        User user = User.builder()
                .username("nero")
                .password("12345")
                .email("nero1995y@naver.com")
                .phone("01022423531")
                .build();

        User saveUser = userRepository.save(user);

        // when
        Optional<User> findUser = userRepository.findByEmailAndPassword(
                saveUser.getEmail(),
                saveUser.getPassword()
        );

        // then
        User findUserEntity = findUser.orElseThrow(UserNotFoundException::new);

        String email = findUserEntity.getEmail();
        String password = findUserEntity.getPassword();


        assertThat(email).isEqualTo(user.getEmail());
        assertThat(password).isEqualTo(user.getPassword());

    }
}
