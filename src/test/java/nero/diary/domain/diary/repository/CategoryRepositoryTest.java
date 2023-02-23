package nero.diary.domain.diary.repository;

import lombok.extern.slf4j.Slf4j;
import nero.diary.domain.diary.entity.Category;
import nero.diary.domain.diary.exception.CategoryNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@Slf4j
class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @DisplayName("카테고리를 저장한다")
    @Test
    void save() {
        // given
        Category category = Category.builder()
                .name("TestCategory")
                .build();

        // when
        categoryRepository.save(category);

        // then
        Category findCategory = categoryRepository.findByName(category.getName())
                .orElseThrow(CategoryNotFoundException::new);

        assertThat(findCategory).isEqualTo(category);
    }

    @DisplayName("카테고리를 검색한다")
    @Test
    void find() {
        //given
        Category category = Category.builder()
                .name("TestCategory")
                .build();

        categoryRepository.save(category);

        //when
        Category findCategory = categoryRepository.findByName(category.getName())
                .orElseThrow(CategoryNotFoundException::new);

        //then
        assertThat(findCategory.getName()).isEqualTo(category.getName());
    }

    @DisplayName("카테고리를 수정한다")
    @Test
    void update() {
        //given
        Category category = Category.builder()
                .name("TestCategory")
                .build();
        categoryRepository.save(category);

        //when
        category.update("changeCategory");

        //then
        Category findCategory = categoryRepository.findByName("changeCategory")
                .orElseThrow(CategoryNotFoundException::new);

        assertThat(findCategory.getName()).isEqualTo(category.getName());
    }

    @DisplayName("카테고리를 삭제한다")
    @Test
    void delete() {
        //given
        Category category = Category.builder()
                .name("TestCategory")
                .build();
        categoryRepository.save(category);
        //when
        categoryRepository.delete(category);

        //then
        boolean isNullEntity = categoryRepository.findById(category.getId())
                .isPresent();

        assertThat(isNullEntity).isEqualTo(false);
    }
}