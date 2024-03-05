package com.nhnacademy.inkbridge.backend.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.nhnacademy.inkbridge.backend.dto.book.BookReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.book.BooksReadResponseDto;
import com.nhnacademy.inkbridge.backend.service.BookService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

/**
 * class: BookRestControllerTest.
 *
 * @author minm063
 * @version 2024/02/16
 */
@AutoConfigureRestDocs
@WebMvcTest(BookRestController.class)
class BookRestControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    BookService bookService;

    @MockBean
    Pageable pageable;

    @Test
    void whenReadBooks_thenReturnDtoList() throws Exception {
        BooksReadResponseDto booksReadResponseDto = BooksReadResponseDto.builder()
            .bookTitle("title")
            .price(1000L)
            .publisherName("publisher")
            .build();
        Page<BooksReadResponseDto> page = new PageImpl<>(
            List.of(booksReadResponseDto), pageable, 0);
        when(bookService.readBooks(any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/api/books"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$[0].bookTitle", equalTo("title")))
            .andExpect(jsonPath("$[0].price", equalTo(1000)))
            .andExpect(jsonPath("$[0].publisherName", equalTo("publisher")))
            .andDo(document("docs"));
    }

    @Test
    void whenReadBook_thenReturnDto() throws Exception {
        BookReadResponseDto bookReadResponseDto = BookReadResponseDto.builder().build();
        when(bookService.readBook(anyLong())).thenReturn(bookReadResponseDto);

        mockMvc.perform(get("/api/books/{bookId}", 1))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andDo(document("docs"));
    }
}