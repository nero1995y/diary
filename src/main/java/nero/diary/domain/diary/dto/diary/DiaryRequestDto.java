package nero.diary.domain.diary.dto.diary;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nero.diary.domain.diary.entity.Diary;

@Getter
@NoArgsConstructor
public class DiaryRequestDto {
    private String userEmail;
    private Long id;

    @Builder
    public DiaryRequestDto(String userEmail, Long id) {
        this.userEmail = userEmail;
        this.id = id;
    }
}
