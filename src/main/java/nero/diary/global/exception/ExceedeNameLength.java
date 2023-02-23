package nero.diary.global.exception;

import lombok.Getter;

@Getter
public class ExceedeNameLength extends GlobalException{

    private static final String MESSEAGE = "잘못된 요청입니다.";

    public String fieldName;
    public String message;

    public ExceedeNameLength() {
        super(MESSEAGE);
    }

    public ExceedeNameLength(String fieldName, String message) {
        super(MESSEAGE);
        addValidation(fieldName, message);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}
