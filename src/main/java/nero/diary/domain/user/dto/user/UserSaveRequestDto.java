package nero.diary.domain.user.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nero.diary.domain.user.entity.User;

@Getter
@NoArgsConstructor
public class UserSaveRequestDto {

    private String username;
    private String email;
    private String phone;
    private String password;

    @Builder
    public UserSaveRequestDto(String username, String email, String password, String phone) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.phone = phone;
    }

    public User toEntity() {

        return User.builder()
                .username(this.username)
                .email(this.email)
                .phone(this.phone)
                .password(this.password)
                .build();
    }
}
