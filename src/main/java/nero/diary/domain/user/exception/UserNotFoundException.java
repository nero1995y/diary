package nero.diary.domain.user.exception;

import nero.diary.global.exception.GlobalException;

public class UserNotFoundException extends GlobalException {

    private static final String MESSAGE = "존재하지 않는 유저입니다.";

    public UserNotFoundException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 404;
    }
}
