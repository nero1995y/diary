package nero.diary.domain.diary.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Getter
@AllArgsConstructor
public class DiariesResponseDto {

    private List<DiaryResponseDto> diaryResponseDtoList;
    private Pageable pageable;

    public static DiariesResponseDto of(Page<DiaryResponseDto> diaries) {
        return new DiariesResponseDto(diaries.getContent(), diaries.getPageable());
    }
}
