package nero.diary.domain.user.exception;

import nero.diary.global.exception.GlobalException;

public class AlreadyUserException extends GlobalException {

    private static final String MESSAGE = "이미 존재하는 유저 입니다.";

    public AlreadyUserException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 0;
    }
}
