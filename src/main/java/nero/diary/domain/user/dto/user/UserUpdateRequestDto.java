package nero.diary.domain.user.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nero.diary.domain.user.entity.User;

@Getter
@NoArgsConstructor
public class UserUpdateRequestDto {
    private String username;
    private String email;

    private String picture;

    @Builder
    public UserUpdateRequestDto(String username,
                                String email,
                                String picture) {
        this.username = username;
        this.email = email;
    }

    public User toEntity() {
        return User.builder()
                .username(this.username)
                .email(this.email)
                .picture(this.picture)
                .build();

    }
}
