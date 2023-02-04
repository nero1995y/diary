package nero.diary.domain.user.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nero.diary.domain.user.entity.Role;
import nero.diary.domain.user.entity.User;

@Getter
@NoArgsConstructor
public class UserSaveRequestDto {

    private String username;
    private String email;

    private String picture;

    @Builder
    public UserSaveRequestDto(String username, String email, String picture) {
        this.username = username;
        this.email = email;
        this.picture = picture;
    }

    public User toEntity() {

        return User.builder()
                .username(this.username)
                .email(this.email)
                .picture(this.picture)
                .role(Role.GUEST)
                .build();
    }
}
