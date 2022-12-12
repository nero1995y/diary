package nero.diary.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nero.diary.domain.user.entity.User;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {

    private String username;
    private String email;
    private String phone;
    private String password;

    public UserResponseDto(User user) {
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.phone = user.getPhone();
        this.password = user.getPassword();

    }
}
