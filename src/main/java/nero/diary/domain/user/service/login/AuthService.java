package nero.diary.domain.user.service.login;

import lombok.RequiredArgsConstructor;
import nero.diary.domain.user.dto.login.LoginRequestDto;
import nero.diary.domain.user.dto.login.SessionResponseDto;
import nero.diary.domain.user.entity.User;
import nero.diary.domain.user.entity.auth.Session;
import nero.diary.domain.user.repository.UserRepository;
import nero.diary.domain.user.repository.auth.SessionRepository;
import nero.diary.global.exception.InvalidSignInformation;
import nero.diary.global.exception.Unauthorized;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    private final SessionRepository sessionRepository;

    @Transactional
    public SessionResponseDto login(LoginRequestDto loginRequestDto) {

        String email = loginRequestDto.getEmail();
        String password = loginRequestDto.getPassword();

        User user = userRepository.findByEmailAndPassword(email, password)
                .orElseThrow(InvalidSignInformation::new);

        Session session = user.addSession();

        return new SessionResponseDto(session.getAccessToken());
    }

    public Session findSession(String accessToken) {

        return sessionRepository.findByAccessToken(accessToken)
                .orElseThrow(Unauthorized::new);
    }
}
