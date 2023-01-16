package nero.diary.domain.diary.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nero.diary.domain.diary.entity.Diary;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DiaryResponseDto {

    private Long id;
    private String name;
    private String content;

    public DiaryResponseDto(Diary diary) {

        this.id = diary.getId();
        this.name = diary.getName();
        this.content = diary.getContent();
    }
}
