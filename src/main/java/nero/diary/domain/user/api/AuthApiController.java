package nero.diary.domain.user.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nero.diary.domain.user.dto.auth.LoginRequestDto;
import nero.diary.domain.user.dto.user.UserResponseDto;
import nero.diary.domain.user.service.auth.AuthService;
import nero.diary.global.config.auth.jwt.JwtConfig;
import nero.diary.global.exception.AuthenticationError;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthApiController {
    private final AuthService authService;
    private final JwtConfig jwtConfig;

    @PostMapping("/api/auth/login")
    public ResponseEntity<UserResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) {
        return ResponseEntity.ok(authService.login(loginRequestDto));
    }

    @GetMapping("/api/auth/me/{userEmail}")
    public ResponseEntity<String> me(@RequestHeader("Authorization") String authorization,
                                     @PathVariable String userEmail) {

        if (!jwtConfig.validateToken(authorization, userEmail)) {
            throw new AuthenticationError();
        }

        return ResponseEntity.ok(userEmail);
    }
}
