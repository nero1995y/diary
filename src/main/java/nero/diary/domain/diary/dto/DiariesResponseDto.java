package nero.diary.domain.diary.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nero.diary.domain.diary.entity.Diary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class DiariesResponseDto {

    private List<DiaryResponseDto> diaryResponseDtoList;
    private Pageable pageable;

    public static DiariesResponseDto of(Page<DiaryResponseDto> diaries) {
        return new DiariesResponseDto(diaries.getContent(), diaries.getPageable());
    }
}
