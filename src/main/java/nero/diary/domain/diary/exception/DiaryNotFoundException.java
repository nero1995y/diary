package nero.diary.domain.diary.exception;

public class DiaryNotFoundException extends RuntimeException {

    private static final String MESSAGE = "존재하지 않는 유저입니다.";

    public DiaryNotFoundException() {
        super(MESSAGE);
    }
}
