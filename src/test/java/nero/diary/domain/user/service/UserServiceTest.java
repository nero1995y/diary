package nero.diary.domain.user.service;

import nero.diary.domain.user.dto.UserResponseDto;
import nero.diary.domain.user.dto.UserSaveRequestDto;
import nero.diary.domain.user.dto.UsersResponseDto;
import nero.diary.domain.user.entity.User;
import nero.diary.domain.user.exception.AlreadyUserException;
import nero.diary.domain.user.exception.UserNotFoundException;
import nero.diary.domain.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;


@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @DisplayName("등록한다_유저를")
    @Test
    void register() {

        // given
        UserSaveRequestDto requestDto = UserSaveRequestDto.builder()
                .username("nero")
                .email("test@gmail.com")
                .phone("01022423531")
                .password("12345")
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
                .phone("01022423531")
                .password("12345")
                .build();

        UserSaveRequestDto requestDto2 = UserSaveRequestDto.builder()
                .username("nero")
                .email("test@gmail.com")
                .phone("01022423531")
                .password("12345")
                .build();

        // when then
        userService.register(requestDto);
        assertThatThrownBy(() -> userService.register(requestDto2))
                .isInstanceOf(AlreadyUserException.class);
    }


    @DisplayName("유저가 존재할시 등록 예외가 발생한다")
    @Test
    void findAll() {
        // given
        UserSaveRequestDto requestDto = getUserSaveRequestDto();

        UserSaveRequestDto requestDto2 = UserSaveRequestDto.builder()
                .username("nero2")
                .email("test2@gmail.com")
                .phone("01022423531")
                .password("12345")
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

    private UserSaveRequestDto getUserSaveRequestDto() {
        return UserSaveRequestDto.builder()
                .username("nero")
                .email("test@gmail.com")
                .phone("01022423531")
                .password("12345")
                .build();
    }

}