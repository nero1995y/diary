package nero.diary.domain.diary.service;

import lombok.RequiredArgsConstructor;
import nero.diary.domain.diary.dto.category.CategoryListResponseDto;
import nero.diary.domain.diary.dto.category.CategoryResponseDto;
import nero.diary.domain.diary.dto.category.CategorySaveRequestDto;
import nero.diary.domain.diary.entity.Category;
import nero.diary.domain.diary.exception.AlreadyCategoryException;
import nero.diary.domain.diary.exception.CategoryNotFoundException;
import nero.diary.domain.diary.repository.CategoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional
    public void create(CategorySaveRequestDto request) {
        verifyDuplicates(request.getName());
        categoryRepository.save(request.toEntity());
    }

    public void verifyDuplicates(String name) {
        categoryRepository.findByName(name)
                .ifPresent(category -> {
                    throw new AlreadyCategoryException();
                });
    }

    public CategoryListResponseDto findAll() {

        Page<Category> categories = categoryRepository.findAll(defaultPageRequest());

        return CategoryListResponseDto.of(categories);
    }

    public CategoryResponseDto findByName(String name) {

        Category category = getCategory(name);

        return new CategoryResponseDto(category);
    }

    @Transactional
    public void update(Long id, String name) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(CategoryNotFoundException::new);

        category.update(name);
    }

    @Transactional
    public void delete(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(CategoryNotFoundException::new);

        categoryRepository.deleteById(category.getId());
    }

    private Category getCategory(String name) {
        return categoryRepository.findByName(name)
                .orElseThrow(CategoryNotFoundException::new);
    }

    private PageRequest defaultPageRequest() {
        return PageRequest.of(0,
                10,
                Sort.by(Sort.Order.desc("name")));
    }
}
