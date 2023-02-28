package nero.diary.domain.diary.service;

import nero.diary.domain.diary.dto.category.CategoryListResponseDto;
import nero.diary.domain.diary.dto.category.CategorySaveRequestDto;
import nero.diary.domain.diary.entity.Category;
import nero.diary.domain.diary.exception.AlreadyCategoryException;
import nero.diary.domain.diary.repository.CategoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.*;


@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    CategoryRepository categoryRepository;

    @InjectMocks
    CategoryService categoryService;

    @DisplayName("카테고리를 등록한다")
    @Test
    void create() {
        //given
        CategorySaveRequestDto request = CategorySaveRequestDto.builder()
                .name("카테고리테스트이름")
                .build();

        when(categoryRepository.save(any()))
                .thenReturn(request.toEntity());

        //when
        categoryService.create(request);

        //then
        verify(categoryRepository, times(1)).save(any());
    }


    @DisplayName("카테고리 중복 메소드가 동작하는지")
    @Test
    void verifyDuplicates() {
        //given
        CategorySaveRequestDto request = CategorySaveRequestDto.builder()
                .name("카테고리테스트이름")
                .build();

        String fakeRequestName = request.getName();

        when(categoryRepository.findByName(any()))
                .thenThrow(new AlreadyCategoryException());

        //when then
        assertThatThrownBy(() -> categoryService.verifyDuplicates(fakeRequestName))
                .isInstanceOf(AlreadyCategoryException.class);

        verify(categoryRepository, times(1)).findByName(any());
    }

    @DisplayName("카테고리 목록을 조회한다")
    @Test
    void findAll() {
        //given
        Category aCategory = Category.builder()
                .name("aCategory")
                .build();

        Category bCategory = Category.builder()
                .name("bCategory")
                .build();

        List<Category> categories = new ArrayList<>();
        categories.add(aCategory);
        categories.add(bCategory);

        PageRequest page = PageRequest.of(0, 10,
                Sort.by(Sort.Order.desc("name")));

        given(categoryRepository.findAll(page)).willReturn(new PageImpl<>(categories, page, 1000));

        //when
        CategoryListResponseDto categoryAll = categoryService.findAll();

        //then
        verify(categoryRepository, times(1)).findAll(page);
    }

    @DisplayName("카테고리를 이름으로 조회한다")
    @Test
    void findCategory() {
        //given
        Category categoryEntity = Category.builder()
                .name("TestCategory")
                .build();

        Optional<Category> category = Optional.of(categoryEntity);

        given(categoryRepository.findByName(any()))
                .willReturn(category);

        //when
        categoryService.findByName(categoryEntity.getName());

        //then
        verify(categoryRepository, times(1)).findByName(any());
    }

    @DisplayName("카테고리를 업데이트 한다")
    @Test
    void update() {
        //given
        Category categoryEntity = Category.builder()
                .id(1L)
                .name("TestCategory")
                .build();

        String updateCategoryName = "updateCategory";
        Optional<Category> category = Optional.of(categoryEntity);

        given(categoryRepository.findById(any()))
                .willReturn(category);

        //when
        categoryService.update(categoryEntity.getId(), updateCategoryName);

        //then
        verify(categoryRepository, times(1))
                .findById(any());

        assertThat(categoryEntity.getName()).isEqualTo(updateCategoryName);
    }

    @DisplayName("카테고리가 삭제된다")
    @Test
    void delete() {
        //given
        Category category = Category.builder()
                .id(1L)
                .name("tesCategory")
                .build();

        given(categoryRepository.findByName(any()))
                .willReturn(Optional.of(category));

        doNothing().when(categoryRepository).deleteById(anyLong());

        //when
        categoryService.delete(category.getName());

        //then
        verify(categoryRepository, times(1))
                .findByName(any());

        verify(categoryRepository, times(1))
                .deleteById(any());
    }
}