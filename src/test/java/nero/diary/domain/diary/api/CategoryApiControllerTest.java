package nero.diary.domain.diary.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import nero.diary.domain.diary.dto.category.CategoryListResponseDto;
import nero.diary.domain.diary.dto.category.CategoryResponseDto;
import nero.diary.domain.diary.dto.category.CategorySaveRequestDto;
import nero.diary.domain.diary.entity.Category;
import nero.diary.domain.diary.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CategoryApiController.class)
@MockBean(JpaMetamodelMappingContext.class)
@Slf4j
class CategoryApiControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    CategoryService categoryService;

    @BeforeEach
    void setUp() {
        MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @DisplayName("Post category api")
    @Test
    @WithMockUser(roles = "USER")
    void save() throws Exception {
        //given
        CategorySaveRequestDto request = CategorySaveRequestDto.builder()
                .name("testCategory")
                .build();

        willDoNothing()
                .given(categoryService)
                .create(request);

        String json = objectMapper.writeValueAsString(request);

        //when
        ResultActions actions = mockMvc.perform(post("/api/v2/category")
                .contentType(APPLICATION_JSON)
                .with(csrf())
                .content(json));

        //then
        actions.andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("GET category list api")
    @Test
    @WithMockUser(roles = "USER")
    void findList() throws Exception {
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

        CategoryListResponseDto response = CategoryListResponseDto
                .of(new PageImpl<>(categories, page, 1000));

        given(categoryService.findAll()).willReturn(response);

        //when
        ResultActions actions = mockMvc.perform(get("/api/v2/categories")
                .contentType(APPLICATION_JSON));

        //then
        actions.andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.categoryResponseDtoList.length()", equalTo(2)))
                .andDo(print());
    }


    @DisplayName("GET category single api")
    @Test
    @WithMockUser(roles = "USER")
    void findByName() throws Exception {
        //given
        Category category = Category.builder()
                .name("aCategory")
                .build();

        CategoryResponseDto response = new CategoryResponseDto(category);

        given(categoryService.findByName(any())).willReturn(response);

        //when
        ResultActions actions = mockMvc.perform(get("/api/v2/category/{name}", category.getName())
                .contentType(APPLICATION_JSON));

        //then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(category.getName()))
                .andDo(print());
    }


    @DisplayName("PATCH category api")
    @Test
    @WithMockUser(roles = "USER")
    void update() throws Exception {
        //given
        Category category = Category.builder()
                .id(1L)
                .name("testCategory")
                .build();
        String updateCategoryName = "updateCategory";

        willDoNothing().given(categoryService).update(category.getId(), updateCategoryName);

        String json = objectMapper.writeValueAsString(updateCategoryName);

        //when
        ResultActions actions = mockMvc.perform(patch("/api/v2/category/{id}", category.getId())
                .contentType(APPLICATION_JSON)
                .with(csrf())
                .content(json));

        //then
        actions.andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("DELTE category api")
    @Test
    @WithMockUser(roles = "USER")
    void deleteApi() throws Exception {
        //given
        Category category = Category.builder()
                .id(1L)
                .name("testCategory")
                .build();

        String json = objectMapper.writeValueAsString(category.getName());

        willDoNothing().given(categoryService).delete(category.getName());
        //when
        ResultActions actions = mockMvc.perform(delete("/api/v2/category")
                .with(csrf())
                .content(json));

        //then
        actions.andExpect(status().isOk())
                .andDo(print());
    }
}