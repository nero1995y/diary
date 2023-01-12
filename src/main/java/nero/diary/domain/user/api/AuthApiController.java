package nero.diary.domain.user.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nero.diary.domain.user.dto.login.LoginRequestDto;
import nero.diary.domain.user.dto.login.SessionResponseDto;
import nero.diary.domain.user.service.login.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;



@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthApiController {

    private final AuthService authService;

    @PostMapping("/auth/login")
    public ResponseEntity<SessionResponseDto> login(@RequestBody LoginRequestDto login) {

        SessionResponseDto sessionResponseDto = authService.login(login);

        return ResponseEntity.ok()
                .header("accessToken", sessionResponseDto.getAccessToken())
                .build();
    }
}
