package nero.diary.domain.diary.dto.category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nero.diary.domain.diary.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryListResponseDto {

    private List<CategoryResponseDto> categoryResponseDtoList;
    private Pageable pageable;

    public static CategoryListResponseDto of(Page<Category> categories) {

        List<CategoryResponseDto> categoryResponseDto = toResponse(categories.getContent());

        return new CategoryListResponseDto(categoryResponseDto, categories.getPageable());
    }

    private static List<CategoryResponseDto> toResponse(List<Category> categories) {
        return categories.stream()
                .map(CategoryResponseDto::new)
                .collect(Collectors.toList());
    }

}
