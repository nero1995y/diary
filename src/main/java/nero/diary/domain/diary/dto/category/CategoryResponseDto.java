package nero.diary.domain.diary.dto.category;

import nero.diary.domain.diary.entity.Category;

public class CategoryResponseDto {

    private Long id;
    private String name;

    public CategoryResponseDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public CategoryResponseDto(Category category) {
        this.id = category.getId();
        this.name = category.getName();
    }
}
