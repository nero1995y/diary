package nero.diary.domain.user.dto.login;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SessionResponseDto {

    private String accessToken;

    public SessionResponseDto(String accessToken) {
        this.accessToken = accessToken;
    }
}
