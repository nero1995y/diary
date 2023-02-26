package nero.diary.domain.diary.repository;

import lombok.extern.slf4j.Slf4j;
import nero.diary.domain.diary.entity.Category;
import nero.diary.domain.diary.exception.CategoryNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;

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

    @DisplayName("카테고리 목록을 전부 조회한다")
    @Test
    void findAll() {
        //given
        Category aCategory = Category.builder()
                .name("ACategory")
                .build();

        Category bCategory = Category.builder()
                .name("BCategory")
                .build();

        Category cCategory = Category.builder()
                .name("CCategory")
                .build();

        categoryRepository.save(aCategory);
        categoryRepository.save(bCategory);
        categoryRepository.save(cCategory);

        PageRequest page = PageRequest.of(0, 10,
                Sort.by(Sort.Order.desc("name")));

        //when
        Page<Category> categories = categoryRepository.findAll(page);


        //then
        assertThat(categories.getContent())
                .containsExactly(cCategory,bCategory,aCategory);
    }
}