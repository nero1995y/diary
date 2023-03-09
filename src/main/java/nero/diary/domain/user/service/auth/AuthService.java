package nero.diary.domain.user.service.auth;

import lombok.RequiredArgsConstructor;
import nero.diary.domain.user.dto.auth.LoginRequestDto;
import nero.diary.domain.user.dto.user.UserResponseDto;
import nero.diary.domain.user.entity.User;
import nero.diary.domain.user.exception.InvalidSignInformation;
import nero.diary.domain.user.exception.UserNotFoundException;
import nero.diary.domain.user.repository.UserRepository;
import nero.diary.global.config.auth.jwt.JwtConfig;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtConfig jwtConfig;

    public UserResponseDto login(LoginRequestDto loginRequestDto) {

        User user = userRepository.findByEmail(loginRequestDto.getEmail())
                .orElseThrow(UserNotFoundException::new);

        if (!passwordEncoder.matches(
                loginRequestDto.getPassword(), user.getPassword())) {

            throw new InvalidSignInformation();
        }

        String token = jwtConfig.createToken(loginRequestDto.getEmail(), Arrays.asList(user.getRole().getKey()));
        return new UserResponseDto(user, token);
    }
}
