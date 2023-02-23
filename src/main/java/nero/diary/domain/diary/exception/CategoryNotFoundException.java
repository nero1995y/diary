package nero.diary.domain.diary.exception;

public class CategoryNotFoundException extends RuntimeException {

    private static final String MESSAGE = "존재하지 않는 분류입니다.";

    public CategoryNotFoundException() {
        super(MESSAGE);
    }
}
