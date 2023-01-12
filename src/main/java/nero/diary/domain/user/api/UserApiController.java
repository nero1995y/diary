package nero.diary.domain.user.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nero.diary.domain.user.dto.user.UserResponseDto;
import nero.diary.domain.user.dto.user.UserSaveRequestDto;
import nero.diary.domain.user.dto.user.UserUpdateRequestDto;
import nero.diary.domain.user.dto.user.UsersResponseDto;
import nero.diary.domain.user.service.UserService;
import nero.diary.global.config.data.UserSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserApiController {

    private final UserService userService;

    @GetMapping("/foo")
    public Long foo(UserSession userSession) {

        log.info(">>>{}", userSession.id);

        return userSession.id;
    }

    @PostMapping("api/v1/user")
    public ResponseEntity<Void> register(@RequestBody @Valid UserSaveRequestDto requestDto
                                         ) {

            userService.register(requestDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("api/v1/users")
    public ResponseEntity<UsersResponseDto> findList() {
        UsersResponseDto response = userService.findAll();

        return ResponseEntity.ok(response);
    }

    @GetMapping("api/v1/user/{id}")
    public ResponseEntity<UserResponseDto> findByUserId(@PathVariable Long id){

        UserResponseDto response = userService.findUserId(id);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("api/v1/user/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id,
                                       @RequestBody @Valid UserUpdateRequestDto requestDto){

        userService.update(id, requestDto);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("api/v1/user/{username}")
    public ResponseEntity<Void> delete(@PathVariable String username){
        userService.remove(username);
        return  ResponseEntity.ok().build();
    }

}
