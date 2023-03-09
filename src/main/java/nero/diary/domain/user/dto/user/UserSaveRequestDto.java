package nero.diary.domain.user.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nero.diary.domain.user.entity.Role;
import nero.diary.domain.user.entity.User;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class UserSaveRequestDto {
    @NotBlank(message = "NAME_IS_MANDATORY")
    private String username;
    @NotBlank(message = "PASSWORD_IS_MANDATORY")
    private String password;
    @Email(message = "NOT_VALID_EMAIL")
    private String email;
    private String picture;

    @Builder
    public UserSaveRequestDto(String username, String password, String email, String picture) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.picture = picture;
    }

    public User toEntity(String hashedPassword) {

        return User.builder()
                .username(this.username)
                .password(hashedPassword)
                .email(this.email)
                .picture(this.picture)
                .role(Role.GUEST)
                .build();
    }
}
