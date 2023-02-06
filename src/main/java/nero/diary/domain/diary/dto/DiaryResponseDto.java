package nero.diary.domain.diary.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nero.diary.domain.diary.entity.Diary;

@Getter
@NoArgsConstructor
public class DiaryResponseDto {

    private Long id;
    private String name;
    private String content;

    private String userEmail;
    @QueryProjection
    public DiaryResponseDto(Long id, String name, String content, String userEmail) {

        this.id = id;
        this.name = name;
        this.content = content;
        this.userEmail = userEmail;
    }

    public DiaryResponseDto(Diary diary) {

        this.id = diary.getId();
        this.name = diary.getName();
        this.content = diary.getContent();
    }
}
