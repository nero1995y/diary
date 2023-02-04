package nero.diary.domain.user.service;

import nero.diary.domain.user.dto.user.UserResponseDto;
import nero.diary.domain.user.dto.user.UserSaveRequestDto;
import nero.diary.domain.user.dto.user.UserUpdateRequestDto;
import nero.diary.domain.user.dto.user.UsersResponseDto;
import nero.diary.domain.user.entity.User;
import nero.diary.domain.user.exception.AlreadyUserException;
import nero.diary.domain.user.exception.UserNotFoundException;
import nero.diary.domain.user.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@TestPropertySource("classpath:application.yml")
@Transactional
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;


    @AfterEach
    public void cleanup() {
        userRepository.deleteAll();
    }

    @DisplayName("등록한다_유저를")
    @Test
    void register() {

        // given
        UserSaveRequestDto requestDto = UserSaveRequestDto.builder()
                .username("nero")
                .email("test@gmail.com")
                .picture("testPicture")
                .build();

        // when
        userService.register(requestDto);


        // then
        Optional<User> user = userRepository.findByUsername(requestDto.getUsername());

        String findUsername = user.orElseThrow(UserNotFoundException::new)
                .getUsername();

        assertThat(findUsername).isEqualTo(requestDto.getUsername());

    }

    @DisplayName("유저가 존재할시 등록 예외가 발생한다")
    @Test
    void verifyUsername() {
        // given
        UserSaveRequestDto requestDto = UserSaveRequestDto.builder()
                .username("nero")
                .email("test@gmail.com")
                .picture("testPicture")
                .build();

        UserSaveRequestDto requestDto2 = UserSaveRequestDto.builder()
                .username("nero")
                .email("test@gmail.com")
                .picture("testPicture")
                .build();

        // when then
        userService.register(requestDto);
        assertThatThrownBy(() -> userService.register(requestDto2))
                .isInstanceOf(AlreadyUserException.class);
    }


    @DisplayName("유저 리스트를 조회한다")
    @Test
    void findAll() {
        // given
        UserSaveRequestDto requestDto = getUserSaveRequestDto();

        UserSaveRequestDto requestDto2 = UserSaveRequestDto.builder()
                .username("nero2")
                .email("test2@gmail.com")
                .picture("testPicture")
                .build();

        userService.register(requestDto);
        userService.register(requestDto2);

        // when
        UsersResponseDto responseDto = userService.findAll();

        // then
        List<String> usernameList = responseDto.getUserResponseDtoList()
                .stream()
                .map(UserResponseDto::getUsername)
                .collect(Collectors.toList());

        assertThat(usernameList).contains(requestDto.getUsername(), requestDto2.getUsername());
    }



    @DisplayName("유저 단건을 조회한다")
    @Test
    void finnUser() {
        // given
        UserSaveRequestDto requestDto = getUserSaveRequestDto();

        userService.register(requestDto);

        // when
        UserResponseDto findUser = userService.findUser(requestDto.getUsername());

        // then
        assertThat(findUser.getUsername()).isEqualTo(requestDto.getUsername());

    }

    @DisplayName("유저 단건을 ID로 조회한다")
    @Test
    void finnUserId() {
        // given
        UserSaveRequestDto requestDto = getUserSaveRequestDto();

        userService.register(requestDto);

        // when
        UserResponseDto findUser = userService.findUser(requestDto.getUsername());
        UserResponseDto findUserId = userService.findUserId(findUser.getId());

        // then
        assertThat(findUserId.getUsername()).isEqualTo(requestDto.getUsername());

    }

    private UserSaveRequestDto getUserSaveRequestDto() {
        return UserSaveRequestDto.builder()
                .username("nero")
                .email("test@gmail.com")
                .picture("testPicture")
                .build();
    }

    @DisplayName("유저 조회 없을때 예외가 발생한다")
    @Test
    void findUserIdException(){
        // given
        UserSaveRequestDto requestDto = getUserSaveRequestDto();

        userService.register(requestDto);
        UserResponseDto findUser = userService.findUser(requestDto.getUsername());

        // when then
        assertThrows(UserNotFoundException.class, () -> {
           userService.findUserId(findUser.getId()+ 1L);
        });
    }




    @DisplayName("유저 업데이트한다")
    @Test
    void update() {
        // given
        UserSaveRequestDto requestDto = getUserSaveRequestDto();

        userService.register(requestDto);

        UserUpdateRequestDto updateRequestDto = UserUpdateRequestDto.builder()
                .username("nero")
                .email("update@gmail.com")
                .picture("testPicture")
                .build();

        UserResponseDto responseDto = userService.findUser(requestDto.getUsername());

        // when
        userService.update(responseDto.getId(), updateRequestDto);

        // then
        assertThat(responseDto.getUsername()).isEqualTo(requestDto.getUsername());

    }

    @DisplayName("유저를 삭제한다")
    @Test
    void delete() {
        // given
        UserSaveRequestDto requestDto = getUserSaveRequestDto();
        userService.register(requestDto);

        // when
        userService.remove(requestDto.getUsername());

        // then
        assertThatThrownBy(() -> userService.findUser(requestDto.getUsername()))
                .isInstanceOf(UserNotFoundException.class);

    }
}