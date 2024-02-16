package com.nhnacademy.inkbridge.backend.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.inkbridge.backend.dto.book.BookAdminCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.book.BookAdminReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.book.BookAdminUpdateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.book.BookAdminUpdateResponseDto;
import com.nhnacademy.inkbridge.backend.dto.book.BooksAdminReadResponseDto;
import com.nhnacademy.inkbridge.backend.exception.ValidationException;
import com.nhnacademy.inkbridge.backend.service.BookService;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

/**
 * class: BookAdminRestControllerTest.
 *
 * @author minm063
 * @version 2024/02/16
 */
@WebMvcTest(BookAdminRestController.class)
class BookAdminRestControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    BookService bookService;

    @MockBean
    Pageable pageable;

    @Test
    @WithMockUser
    void whenAdminReadBooks_thenReturnDtoList() throws Exception {
        BooksAdminReadResponseDto booksAdminReadResponseDto = new BooksAdminReadResponseDto("title",
            "author", "publisher", "status");
        Page<BooksAdminReadResponseDto> page = new PageImpl<>(List.of(booksAdminReadResponseDto),
            pageable, 0);
        when(bookService.readBooksByAdmin(any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/api/admin/books"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$[0].bookTitle", equalTo("title")))
            .andExpect(jsonPath("$[0].authorName", equalTo("author")))
            .andExpect(jsonPath("$[0].publisherName", equalTo("publisher")))
            .andExpect(jsonPath("$[0].statusName", equalTo("status")));
    }

    @Test
    @WithMockUser
    void whenAdminReadBook_thenReturnDto() throws Exception {
        BookAdminReadResponseDto bookAdminReadResponseDto = new BookAdminReadResponseDto("title",
            "index", "description", LocalDate.now(), "1234567890123", 10000L, 8800L,
            new BigDecimal("3.3"), 999, false, "publisher", "author", "판매중");
        when(bookService.readBookByAdmin(anyLong())).thenReturn(bookAdminReadResponseDto);

        mockMvc.perform(get("/api/admin/books/{bookId}", 1))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.bookTitle", equalTo("title")));
    }

    @Test
    @WithMockUser
    void whenCreateBook_thenReturnHttpStatus() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        BookAdminCreateRequestDto bookAdminCreateRequestDto = new BookAdminCreateRequestDto();
        bookAdminCreateRequestDto.setBookTitle("title");
        bookAdminCreateRequestDto.setIsbn("1234567890123");

        doNothing().when(bookService).createBook(bookAdminCreateRequestDto);

        mockMvc.perform(post("/api/admin/books")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bookAdminCreateRequestDto)))
            .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser
    void givenInvalidRequest_whenCreateBook_thenThrowValidationException() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        BookAdminCreateRequestDto bookAdminCreateRequestDto = new BookAdminCreateRequestDto();
        bookAdminCreateRequestDto.setBookTitle("");
        bookAdminCreateRequestDto.setIsbn("1234567890");

        mockMvc.perform(post("/api/admin/books")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bookAdminCreateRequestDto)))
            .andExpect(status().isUnprocessableEntity())
            .andExpect(
                result -> assertTrue(result.getResolvedException() instanceof ValidationException));
    }

    @Test
    @WithMockUser
    void whenUpdateBook_thenReturnDto() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        BookAdminUpdateResponseDto bookAdminUpdateResponseDto = new BookAdminUpdateResponseDto();
        bookAdminUpdateResponseDto.setBookId(1L);

        BookAdminUpdateRequestDto bookAdminUpdateRequestDto = new BookAdminUpdateRequestDto();
        bookAdminUpdateRequestDto.setBookTitle("title");
        bookAdminUpdateRequestDto.setIsbn("1234567890123");

        when(bookService.updateBookByAdmin(anyLong(),
            any(BookAdminUpdateRequestDto.class))).thenReturn(
            bookAdminUpdateResponseDto);

        mockMvc.perform(put("/api/admin/books/{bookId}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bookAdminUpdateRequestDto))
                .with(csrf()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.bookId", equalTo(1)));

    }

    @Test
    @WithMockUser
    void givenInvalidRequest_whenUpdateBook_thenThrowValidationException() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        BookAdminUpdateResponseDto bookAdminUpdateResponseDto = new BookAdminUpdateResponseDto();
        bookAdminUpdateResponseDto.setBookId(1L);

        BookAdminUpdateRequestDto bookAdminUpdateRequestDto = new BookAdminUpdateRequestDto();
        bookAdminUpdateRequestDto.setBookTitle("");
        bookAdminUpdateRequestDto.setIsbn("123456789");

        when(bookService.updateBookByAdmin(anyLong(),
            any(BookAdminUpdateRequestDto.class))).thenReturn(
            bookAdminUpdateResponseDto);

        mockMvc.perform(put("/api/admin/books/{bookId}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bookAdminUpdateRequestDto))
                .with(csrf()))
            .andExpect(status().isUnprocessableEntity())
            .andExpect(
                result -> assertTrue(result.getResolvedException() instanceof ValidationException));
    }


}