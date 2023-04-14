package nero.diary.domain.diary.dto.category;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nero.diary.domain.diary.entity.Category;

@Getter
@NoArgsConstructor
public class CategoryUpdateRequestDto {

    private String name;

    @Builder
    private CategoryUpdateRequestDto(String name) {
        this.name = name;
    }

    public Category toEntity() {
        return Category.builder()
                .name(this.name)
                .build();
    }
}
