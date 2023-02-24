package nero.diary.domain.diary.exception;

public class AlreadyCategoryException extends RuntimeException {

    private static final String MESSAGE = "이미 존재하는 카테고리 입니다 ";

    public AlreadyCategoryException() {
        super(MESSAGE);
    }
}
