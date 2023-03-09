package nero.diary.domain.user.dto.auth;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nero.diary.domain.user.entity.Role;
import nero.diary.domain.user.entity.User;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class LoginRequestDto {
    @NotBlank(message = "PASSWORD_IS_MANDATORY")
    private String password;
    @Email(message = "NOT_VALID_EMAIL")
    private String email;

    private Role role;

    @Builder
    public LoginRequestDto(String password, String email, Role role) {
        this.password = password;
        this.email = email;
        this.role = role;
    }
}
