package nero.diary.domain.diary.service;

import lombok.RequiredArgsConstructor;
import nero.diary.domain.diary.dto.category.CategoriesResponseDto;
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
    public void createCategory(CategorySaveRequestDto request) {
        verifyDuplicates(request.getName());
        categoryRepository.save(request.toEntity());

    }

    public void verifyDuplicates(String name) {
        categoryRepository.findByName(name)
                .ifPresent(category -> {
                    throw new AlreadyCategoryException();
                });

    }

    public CategoriesResponseDto findCategoryAll() {

        PageRequest page = defaultPageRequest();

        Page<Category> categories = categoryRepository.findAll(page);

        return CategoriesResponseDto.of(categories);
    }

    public CategoryResponseDto findCategory(String name) {

        Category category = categoryRepository.findByName(name)
                .orElseThrow(CategoryNotFoundException::new);

        return new CategoryResponseDto(category);
    }

    public void update(Long id, String name) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(CategoryNotFoundException::new);

        category.update(name);
    }



    private PageRequest defaultPageRequest() {
       return PageRequest.of(0,
                10,
                Sort.by(Sort.Order.desc("name")));
    }
}
