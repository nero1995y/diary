package nero.diary.global.exception;

public class TokenValueException extends GlobalException {

    private static final String MESSAGE = "잘못된 토큰 값입니다.";

    public TokenValueException() {
        super(MESSAGE);
    }

    public TokenValueException(String fieldName, String message) {
        super(MESSAGE);
        addValidation(fieldName, message);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}
