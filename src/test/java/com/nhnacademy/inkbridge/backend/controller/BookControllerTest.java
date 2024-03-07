package com.nhnacademy.inkbridge.backend.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.relaxedResponseFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.nhnacademy.inkbridge.backend.dto.book.BookReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.book.BooksReadResponseDto;
import com.nhnacademy.inkbridge.backend.service.BookService;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;

/**
 * class: BookRestControllerTest.
 *
 * @author minm063
 * @version 2024/02/16
 */
@AutoConfigureRestDocs
@WebMvcTest(BookController.class)
@ExtendWith(RestDocumentationExtension.class)
class BookControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    BookService bookService;

    @MockBean
    Pageable pageable;

    @Test
    void whenReadBooks_thenReturnDtoList() throws Exception {
        BooksReadResponseDto booksReadResponseDto = BooksReadResponseDto.builder()
            .bookId(1L)
            .bookTitle("title")
            .price(1000L)
            .publisherName("publisher")
            .authorName("author")
            .fileUrl("url")
            .build();
        Page<BooksReadResponseDto> page = new PageImpl<>(List.of(booksReadResponseDto));
        when(bookService.readBooks(any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/api/books")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.content.[0].bookId", equalTo(1)))
            .andExpect(jsonPath("$.content.[0].bookTitle", equalTo("title")))
            .andExpect(jsonPath("$.content.[0].price", equalTo(1000)))
            .andExpect(jsonPath("$.content.[0].publisherName", equalTo("publisher")))
            .andExpect(jsonPath("$.content.[0].authorName", equalTo("author")))
            .andExpect(jsonPath("$.content.[0].fileUrl", equalTo("url")))
            .andDo(document("book/member-get-books",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                relaxedResponseFields(
                    fieldWithPath("content[].bookId").description("도서 번호"),
                    fieldWithPath("content[].bookTitle").description("도서 이름"),
                    fieldWithPath("content[].price").description("도서 판매가"),
                    fieldWithPath("content[].publisherName").description("출판사 이름"),
                    fieldWithPath("content[].authorName").description("작가 이름"),
                    fieldWithPath("content[].fileUrl").description("도서 썸네일"),
                    fieldWithPath("totalPages").description("총 페이지"),
                    fieldWithPath("totalElements").description("총 개수"),
                    fieldWithPath("size").description("화면에 출력할 개수"),
                    fieldWithPath("number").description("현재 페이지"),
                    fieldWithPath("numberOfElements").description("현재 페이지 개수")
                )));
    }

    @Test
    void whenReadBook_thenReturnDto() throws Exception {
        BookReadResponseDto bookReadResponseDto = BookReadResponseDto.builder()
            .bookTitle("title").bookIndex("index").description("description").publicatedAt(
                LocalDate.now()).isbn("1234567890123").regularPrice(10000L).price(8000L)
            .discountRatio(new BigDecimal("3.3"))
            .isPackagable(true).thumbnail("thumbnail").publisherId(1L).publisherName("publisher")
            .authorId(1L)
            .authorName("author").wish(1L).fileUrl(Set.of("url")).tagName(Set.of("tag"))
            .categoryName(Set.of("category")).build();
        when(bookService.readBook(anyLong(), anyLong())).thenReturn(bookReadResponseDto);

        mockMvc.perform(RestDocumentationRequestBuilders.get("/api/books/{bookId}", 1L)
                .header("Authorization-Id", "1"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.bookTitle", equalTo("title")))
            .andExpect(jsonPath("$.bookIndex", equalTo("index")))
            .andExpect(jsonPath("$.description", equalTo("description")))
            .andExpect(jsonPath("$.isbn", equalTo("1234567890123")))
            .andExpect(jsonPath("$.regularPrice", equalTo(10000)))
            .andExpect(jsonPath("$.price", equalTo(8000)))
            .andExpect(jsonPath("$.discountRatio", equalTo(3.3)))
            .andExpect(jsonPath("$.isPackagable", equalTo(true)))
            .andExpect(jsonPath("$.thumbnail", equalTo("thumbnail")))
            .andExpect(jsonPath("$.publisherId", equalTo(1)))
            .andExpect(jsonPath("$.publisherName", equalTo("publisher")))
            .andExpect(jsonPath("$.authorId", equalTo(1)))
            .andExpect(jsonPath("$.authorName", equalTo("author")))
            .andExpect(jsonPath("$.wish", equalTo(1)))
            .andExpect(jsonPath("$.fileUrl[0]", equalTo("url")))
            .andExpect(jsonPath("$.tagName[0]", equalTo("tag")))
            .andExpect(jsonPath("$.categoryName[0]", equalTo("category")))
            .andDo(document("book/member-get-book",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                pathParameters(parameterWithName("bookId").description("도서 번호")),
                responseFields(
                    fieldWithPath("bookTitle").description("도서 이름"),
                    fieldWithPath("bookIndex").description("도서 목차"),
                    fieldWithPath("description").description("도서 설명"),
                    fieldWithPath("publicatedAt").description("출판일시"),
                    fieldWithPath("isbn").description("도서 isbn"),
                    fieldWithPath("regularPrice").description("도서 정가"),
                    fieldWithPath("price").description("도서 판매가"),
                    fieldWithPath("discountRatio").description("도서 할인율"),
                    fieldWithPath("isPackagable").description("도서 포장여부"),
                    fieldWithPath("thumbnail").description("도서 썸네일"),
                    fieldWithPath("publisherId").description("출판사 번호"),
                    fieldWithPath("publisherName").description("출판사 이름"),
                    fieldWithPath("authorId").description("작가 번호"),
                    fieldWithPath("authorName").description("작가 이름"),
                    fieldWithPath("wish").description("좋아요"),
                    fieldWithPath("fileUrl[]").description("도서 상세 이미지"),
                    fieldWithPath("tagName[]").description("태그 이름"),
                    fieldWithPath("categoryName[]").description("카테고리 이름")
                )));
    }
}