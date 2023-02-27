package nero.diary.domain.diary.api;

import lombok.RequiredArgsConstructor;
import nero.diary.domain.diary.dto.category.CategorySaveRequestDto;
import nero.diary.domain.diary.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CategoryApiController {

    private final CategoryService categoryService;

    @PostMapping("/api/v2/category")
    public ResponseEntity<Void> save(@RequestBody CategorySaveRequestDto request) {

        categoryService.create(request);
        return ResponseEntity.ok().build();
    }

}
