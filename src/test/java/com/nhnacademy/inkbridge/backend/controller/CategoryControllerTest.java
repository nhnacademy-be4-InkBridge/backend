package com.nhnacademy.inkbridge.backend.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.inkbridge.backend.dto.category.CategoryCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.category.CategoryReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.category.CategoryUpdateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.category.CategoryUpdateResponseDto;
import com.nhnacademy.inkbridge.backend.dto.category.ParentCategoryReadResponseDto;
import com.nhnacademy.inkbridge.backend.entity.Category;
import com.nhnacademy.inkbridge.backend.service.CategoryService;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

/**
 * class: CategoryControllerTest.
 *
 * @author choijaehun
 * @version 2/18/24
 */
@AutoConfigureRestDocs
@WebMvcTest(CategoryController.class)
class CategoryControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    CategoryService categoryService;

    @Test
    @DisplayName("Category 데이터 생성 테스트 - 성공")
    void When_CreateCategory_Expect_Success() throws Exception {
        CategoryCreateRequestDto request = new CategoryCreateRequestDto();
        mockMvc.perform(post("/api/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andDo(document("category/category-create",
                requestFields(
                    fieldWithPath("categoryName").description("카테고리 이름"),
                    fieldWithPath("parentId").description("카테고리의 부모 아이디")
                )));

        verify(categoryService, times(1)).createCategory(request);
    }

    @Test
    @DisplayName("Category 데이터 생성 테스트- 10글자를 넘는 카테고리 유효성 검사")
    void When_CreateCategoryByInvalidName_Expect_Fail() throws Exception {
        CategoryCreateRequestDto request = new CategoryCreateRequestDto();
        ReflectionTestUtils.setField(request, "categoryName", "길이가 10이 넘는 카테고리");

        mockMvc.perform(post("/api/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isUnprocessableEntity())
            .andDo(document("category/category-create-validation",
                requestFields(
                    fieldWithPath("categoryName").description("카테고리 이름"),
                    fieldWithPath("parentId").description("카테고리의 부모 아이디")
                )));

        verify(categoryService, times(0)).createCategory(request);
    }

    @Test
    @DisplayName("Category 단일데이터 조회 테스트")
    void When_ReadCategory_Expect_Success() throws Exception {
        CategoryReadResponseDto response = new CategoryReadResponseDto();
        ReflectionTestUtils.setField(response, "categoryId", 1L);
        ReflectionTestUtils.setField(response, "categoryName", "한국도서");
        when(categoryService.readCategory(response.getCategoryId())).thenReturn(response);

        mockMvc.perform(RestDocumentationRequestBuilders.get("/api/categories/{categoryId}", 1))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.categoryId").value(response.getCategoryId()))
            .andExpect(jsonPath("$.categoryName").value(response.getCategoryName()))
            .andDo(document("category/category-read",
                pathParameters(
                    parameterWithName("categoryId").description("카테고리 아이디")
                ),
                responseFields(
                    fieldWithPath("categoryId").description("카테고리 아이디").type(JsonFieldType.NUMBER),
                    fieldWithPath("categoryName").description("카테고리 이름").type(JsonFieldType.STRING))
            ));
    }

    @Test
    @DisplayName("Category 모든 데이터 조회 테스트")
    void When_ReadAllCategory_Expect_Success() throws Exception {
        Category IT = Category.create().categoryName("IT").build();
        ReflectionTestUtils.setField(IT, "categoryId", 1L);

        Category java = Category.create().categoryName("자바").categoryParent(IT).build();
        ReflectionTestUtils.setField(java, "categoryId", 2L);

        Category javascript = Category.create().categoryName("자바스크립트").categoryParent(IT).build();
        ReflectionTestUtils.setField(javascript, "categoryId", 3L);

        ReflectionTestUtils.setField(IT, "categoryChildren", List.of(java, javascript));

        ParentCategoryReadResponseDto response = new ParentCategoryReadResponseDto(IT);
        when(categoryService.readAllCategory()).thenReturn(List.of(response));

        mockMvc.perform(get("/api/categories"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.*.categoryId").value(1))
            .andExpect(jsonPath("$.*.categoryName").value("IT"))
            .andExpect(jsonPath("$.*.parentCategories.length()").value(2))
            .andExpect(jsonPath("$.*.parentCategories[0].categoryId").value(2))
            .andExpect(jsonPath("$.*.parentCategories[0].categoryName").value("자바"))
            .andExpect(jsonPath("$.*.parentCategories[1].categoryId").value(3))
            .andExpect(jsonPath("$.*.parentCategories[1].categoryName").value("자바스크립트"))
            .andDo(document("category/read-all",
                responseFields(
                    fieldWithPath("[].categoryId").description("카테고리 아이디")
                        .type(JsonFieldType.NUMBER),
                    fieldWithPath("[].categoryName").description("카테고리 이름")
                        .type(JsonFieldType.STRING),
                    fieldWithPath("[].parentCategories").description("부모카테고리 객체")
                        .type(JsonFieldType.ARRAY).optional(),
                    fieldWithPath("[].parentCategories[].parentCategories[]").description("부모카테고리"),
                    fieldWithPath("[].parentCategories[].categoryId").description("부모카테고리 객체의 아이디")
                        .type(JsonFieldType.NUMBER).optional(),
                    fieldWithPath("[].parentCategories[].categoryName").description("부모카테고리 객체의 이름")
                        .type(JsonFieldType.STRING).optional()
                )));
    }

    @Test
    @DisplayName("Category 수정 테스트 - 성공")
    void When_UpdateCategory_Expect_Success() throws Exception {
        Long categoryId = 1L;
        String categoryName = "하의";

        CategoryUpdateRequestDto request = new CategoryUpdateRequestDto();
        ReflectionTestUtils.setField(request, "categoryName", categoryName);

        CategoryUpdateResponseDto response = new CategoryUpdateResponseDto();
        ReflectionTestUtils.setField(response, "categoryName", categoryName);

        when(categoryService.updateCategory(categoryId, request)).thenReturn(response);

        mockMvc.perform(RestDocumentationRequestBuilders.put("/api/categories/{categoryId}",1)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.categoryName").value(response.getCategoryName()))
            .andDo(document("category/update",
                pathParameters(
                    parameterWithName("categoryId").description(
                        "카테고리 아이디")
                ),
                requestFields(
                    fieldWithPath("categoryName").description("카테고리 이름")
                        .type(JsonFieldType.STRING))));

        verify(categoryService, times(1)).updateCategory(categoryId, request);
    }

    @Test
    @DisplayName("Category 수정 테스트 - 10글자를 넘는 카테고리 유효성 검사")
    void When_UpdateCategoryByInvalidName_Expect_Fail() throws Exception {
        Long categoryId = 1L;
        String categoryName = "10글자가 넘는 카테고리입니다";

        CategoryUpdateRequestDto request = new CategoryUpdateRequestDto();
        ReflectionTestUtils.setField(request, "categoryName", categoryName);

        mockMvc.perform(RestDocumentationRequestBuilders.put("/api/categories/{categoryId}",1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isUnprocessableEntity())
            .andDo(document("category/update-validation",
                pathParameters(
                    parameterWithName("categoryId").description(
                        "카테고리 아이디")
                ),
                requestFields(
                    fieldWithPath("categoryName").description("카테고리 이름")
                        .type(JsonFieldType.STRING))));

        verify(categoryService, times(0)).updateCategory(categoryId, request);
    }

}