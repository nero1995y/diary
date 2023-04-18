package nero.diary.global.exception;

import lombok.Getter;

@Getter
public class AuthenticationError extends GlobalException{

    private static final String MESSAGE = "인증 오류입니다";

    public AuthenticationError() {
        super(MESSAGE);
    }

    public AuthenticationError(String fieldName, String message) {
        super(MESSAGE);
        addValidation(fieldName, message);
    }

    @Override
    public int getStatusCode() {
        return 401;
    }
}
