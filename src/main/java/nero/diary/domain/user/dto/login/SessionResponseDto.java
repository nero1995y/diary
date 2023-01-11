package nero.diary.domain.user.dto.login;

import lombok.Getter;

@Getter
public class SessionResponseDto {

    private final String accessToken;

    public SessionResponseDto(String accessToken) {
        this.accessToken = accessToken;
    }
}
