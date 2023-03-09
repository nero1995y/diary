package nero.diary.domain.user.vallidation;

import nero.diary.domain.user.dto.user.UserSaveRequestDto;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class UserValidationTest {

    private static ValidatorFactory factory;
    private static Validator validator;

    @BeforeAll
    public static void init() {
        factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @AfterAll
    public static void close() {
        factory.close();
        ;
    }

    @DisplayName("빈 문자열 전송시 에러 발생")
    @Test
    void blank() {
        //given
        UserSaveRequestDto userSaveRequestDto = UserSaveRequestDto.builder()
                .username("")
                .password("12345")
                .picture("email@gmail.com")
                .build();
        //when
        Set<ConstraintViolation<UserSaveRequestDto>> violations = validator.validate(userSaveRequestDto);

        //then
        assertThat(violations).isNotEmpty();
        violations.forEach(error -> {
            assertThat(error.getMessage()).isEqualTo("NAME_IS_MANDATORY");
        });
    }

    @DisplayName("이메일 아닌 경우 에러 발생")
    @Test
    void email() {
        //given
        UserSaveRequestDto userSaveRequestDto = UserSaveRequestDto.builder()
                .username("name")
                .password("12345")
                .email("email")
                .picture("")
                .build();

        //when
        Set<ConstraintViolation<UserSaveRequestDto>> violations = validator.validate(userSaveRequestDto);

        //then
        assertThat(violations).isNotEmpty();
        violations.forEach(error -> {
            assertThat(error.getMessage()).isEqualTo("NOT_VALID_EMAIL");
        });

    }

    @DisplayName("유효성 검사 제공")
    @Test
    void success() {
        //given
        UserSaveRequestDto userSaveRequestDto = UserSaveRequestDto.builder()
                .username("name")
                .password("12345")
                .email("email@gmail.com")
                .picture("")
                .build();

        //when
        Set<ConstraintViolation<UserSaveRequestDto>> violations = validator.validate(userSaveRequestDto);

        // then
        assertThat(violations).isEmpty();
    }
}
