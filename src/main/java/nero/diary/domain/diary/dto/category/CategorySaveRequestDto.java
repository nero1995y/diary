package nero.diary.domain.diary.dto.category;

import lombok.Builder;
import lombok.Getter;
import nero.diary.domain.diary.entity.Category;

@Getter
public class CategorySaveRequestDto {
    private String name;

    @Builder
    public CategorySaveRequestDto(String name) {
        this.name = name;
    }

    public Category toEntity() {
        return Category.builder()
                .name(this.name)
                .build();
    }
}
