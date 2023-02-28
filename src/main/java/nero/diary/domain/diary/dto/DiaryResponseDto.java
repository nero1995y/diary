package nero.diary.domain.diary.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nero.diary.domain.diary.entity.Diary;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class DiaryResponseDto {

    private Long id;
    private String name;
    private String content;

    private LocalDateTime modifiedDate;

    private String userEmail;

    @QueryProjection
    public DiaryResponseDto(Long id, String name, String content, String userEmail, LocalDateTime modifiedDate) {

        this.id = id;
        this.name = name;
        this.content = content;
        this.userEmail = userEmail;
        this.modifiedDate = modifiedDate;
    }

    public DiaryResponseDto(Diary diary) {

        this.id = diary.getId();
        this.name = diary.getName();
        this.content = diary.getContent();
        this.modifiedDate = diary.getModifiedDate();
    }
}
