package nero.diary.domain.diary.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nero.diary.domain.diary.entity.Diary;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class DiariesResponseDto {

    private List<DiaryResponseDto> diaryResponseDtoList;

    public static DiariesResponseDto of(List<Diary> diaries) {
        return new DiariesResponseDto(toResponse(diaries));

    }

    public static List<DiaryResponseDto> toResponse(List<Diary> diaries) {
        return diaries.stream()
                .map(DiaryResponseDto::new)
                .collect(Collectors.toList());
    }
}
