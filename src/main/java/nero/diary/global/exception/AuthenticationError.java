package nero.diary.global.exception;

import lombok.Getter;

@Getter
public class AuthenticationError extends GlobalException{

    private static final String MESSEAGE = "인증 오류입니다";

    public String fieldName;
    public String message;

    public AuthenticationError() {
        super(MESSEAGE);
    }

    public AuthenticationError(String fieldName, String message) {
        super(MESSEAGE);
        addValidation(fieldName, message);
    }

    @Override
    public int getStatusCode() {
        return 401;
    }
}
