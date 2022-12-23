package nero.diary.domain.user.api;

import lombok.RequiredArgsConstructor;
import nero.diary.domain.user.dto.UserResponseDto;
import nero.diary.domain.user.dto.UserSaveRequestDto;
import nero.diary.domain.user.dto.UserUpdateRequestDto;
import nero.diary.domain.user.dto.UsersResponseDto;
import nero.diary.domain.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;

    @PostMapping("api/v1/user")
    public ResponseEntity<Void> register(@RequestBody @Valid UserSaveRequestDto requestDto) {
        userService.register(requestDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("api/v1/users")
    public ResponseEntity<UsersResponseDto> findList() {
        UsersResponseDto response = userService.findAll();

        return ResponseEntity.ok(response);
    }

    @GetMapping("api/v1/user/{username}")
    public ResponseEntity<UserResponseDto> findByUser(@PathVariable String username){

        UserResponseDto response = userService.findUser(username);

        return ResponseEntity.ok(response);
    }

    @GetMapping("api/v1/user/{id}")
    public ResponseEntity<UserResponseDto> findByUserId(@PathVariable Long id){

        UserResponseDto response = userService.findUserId(id);

        return ResponseEntity.ok(response);
    }



    @PatchMapping("api/v1/user/{username}")
    public ResponseEntity<Void> update(@PathVariable String username,
                                       @RequestBody @Valid UserUpdateRequestDto requestDto){

        userService.update(username, requestDto);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("api/v1/user/{username}")
    public ResponseEntity<Void> delete(@PathVariable String username){
        userService.remove(username);
        return  ResponseEntity.ok().build();
    }

}
