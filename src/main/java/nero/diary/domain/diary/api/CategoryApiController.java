package nero.diary.domain.diary.api;

import lombok.RequiredArgsConstructor;
import nero.diary.domain.diary.dto.category.CategoryListResponseDto;
import nero.diary.domain.diary.dto.category.CategoryResponseDto;
import nero.diary.domain.diary.dto.category.CategorySaveRequestDto;
import nero.diary.domain.diary.dto.category.CategoryUpdateRequestDto;
import nero.diary.domain.diary.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CategoryApiController {

    private final CategoryService categoryService;

    @PostMapping("/api/v2/category")
    public ResponseEntity<Void> save(@RequestBody CategorySaveRequestDto request) {
        categoryService.create(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/api/v2/categories")
    public ResponseEntity<CategoryListResponseDto> findList() {
        return ResponseEntity.ok(categoryService.findAll());
    }

    @GetMapping("/api/v2/category/{name}")
    public ResponseEntity<CategoryResponseDto> findByName(@PathVariable String name) {
        return ResponseEntity.ok(categoryService.findByName(name));
    }

    @PatchMapping("/api/v2/category/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody CategoryUpdateRequestDto request) {
        categoryService.update(id, request.getName());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/api/v2/category/{id}")
    public ResponseEntity<Void> remove(@PathVariable Long id) {
        categoryService.delete(id);
        return ResponseEntity.ok().build();
    }

}
