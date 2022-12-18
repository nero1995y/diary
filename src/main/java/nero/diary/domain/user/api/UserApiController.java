package nero.diary.domain.user.api;

import lombok.RequiredArgsConstructor;
import nero.diary.domain.user.dto.UserSaveRequestDto;
import nero.diary.domain.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;

    @PostMapping("api/v1/user")
    public ResponseEntity<Void> register(@RequestBody @Valid UserSaveRequestDto requestDto) {
        userService.register(requestDto)
        return ResponseEntity.ok().build();
    }

}
