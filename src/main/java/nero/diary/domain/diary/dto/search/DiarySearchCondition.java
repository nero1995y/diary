package nero.diary.domain.diary.dto.search;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DiarySearchCondition {

    private String name;
    private String context;
    private String userEmail;

}
