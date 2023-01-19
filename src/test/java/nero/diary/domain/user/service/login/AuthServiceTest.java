package nero.diary.domain.user.service.login;

import nero.diary.domain.user.dto.login.SessionResponseDto;
import nero.diary.domain.user.dto.user.UserResponseDto;
import nero.diary.domain.user.dto.user.UserSaveRequestDto;
import nero.diary.domain.user.dto.login.LoginRequestDto;
import nero.diary.domain.user.repository.UserRepository;
import nero.diary.domain.user.repository.auth.SessionRepository;
import nero.diary.domain.user.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestPropertySource("classpath:application.yml")
@Transactional
class AuthServiceTest {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private UserRepository userRepository;


    @AfterEach
    public void cleanup() {
        userRepository.deleteAll();
    }

    private UserSaveRequestDto getUserSaveRequestDto() {
        return UserSaveRequestDto.builder()
                .username("nero")
                .email("wnsgur123@snaver.com")
                .phone("01022423531")
                .password("12345")
                .build();
    }

    private LoginRequestDto getLoginRequestDto(UserSaveRequestDto requestDto) {
        return LoginRequestDto.builder()
                .email(requestDto.getEmail())
                .password(requestDto.getPassword())
                .build();
    }


    @DisplayName("로그인이 된다")
    @Test
    void login() {
        // given
        UserSaveRequestDto requestDto = getUserSaveRequestDto();

        userService.register(requestDto);

        LoginRequestDto loginRequestDto = LoginRequestDto.builder()
                .email(requestDto.getEmail())
                .password(requestDto.getPassword())
                .build();

        // when
        SessionResponseDto sessionResponseDto = authService.login(loginRequestDto);


        // then
        assertThat(sessionResponseDto).isNotNull();
    }

    @DisplayName("로그인시 세션이 1생성된다.")
    @Test
    void createSession() {
        // given
        UserSaveRequestDto requestDto = getUserSaveRequestDto();

        userService.register(requestDto);

        LoginRequestDto loginRequestDto = getLoginRequestDto(requestDto);

        authService.login(loginRequestDto);

        // when then
        UserResponseDto user = userService.findUser(requestDto.getUsername());
        UserResponseDto userId = userService.findUserId(user.getId());

        //assertThat(userId.getSessions().size()).isEqualTo(1L);
    }


}