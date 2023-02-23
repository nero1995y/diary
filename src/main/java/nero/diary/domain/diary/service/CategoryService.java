package nero.diary.domain.diary.service;

import lombok.RequiredArgsConstructor;
import nero.diary.domain.diary.dto.category.CategorySaveRequestDto;
import nero.diary.domain.diary.exception.AlreadyCategoryException;
import nero.diary.domain.diary.exception.CategoryNotFoundException;
import nero.diary.domain.diary.repository.CategoryRepository;
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

    public void verifyDuplicates(String name){
        categoryRepository.findByName(name)
                .ifPresent(category -> {
                   throw new AlreadyCategoryException();
                });

    }
}
