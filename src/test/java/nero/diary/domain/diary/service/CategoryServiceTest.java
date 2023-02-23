package nero.diary.domain.diary.service;

import nero.diary.domain.diary.dto.category.CategorySaveRequestDto;
import nero.diary.domain.diary.repository.CategoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    CategoryRepository categoryRepository;

    @InjectMocks
    CategoryService categoryService;


    @DisplayName("카테고리를 등록한다")
    @Test
    void create() {
        // given
        CategorySaveRequestDto request = CategorySaveRequestDto.builder()
                .name("카테고리테스트이름")
                .build();

        when(categoryRepository.save(any()))
                .thenReturn(request.toEntity());

        // when
       categoryService.createCategory(request);

       // then
        verify(categoryRepository, times(1)).save(any());
    }

}