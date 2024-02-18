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
import org.springframework.test.util.ReflectionTestUtils;
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
        BooksAdminReadResponseDto booksAdminReadResponseDto = BooksAdminReadResponseDto.builder()
            .bookTitle("title")
            .authorName("author")
            .publisherName("publisher")
            .statusName("status")
            .build();
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
        BookAdminReadResponseDto bookAdminReadResponseDto = BookAdminReadResponseDto.builder()
            .bookTitle("title")
            .build();

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
        ReflectionTestUtils.setField(bookAdminCreateRequestDto, "bookTitle", "title");
        ReflectionTestUtils.setField(bookAdminCreateRequestDto, "isbn", "1234567890123");

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
        BookAdminUpdateRequestDto bookAdminUpdateRequestDto = new BookAdminUpdateRequestDto();
        ReflectionTestUtils.setField(bookAdminUpdateRequestDto, "bookTitle", "title");
        ReflectionTestUtils.setField(bookAdminUpdateRequestDto, "isbn", "1234567890123");
        BookAdminUpdateResponseDto bookAdminUpdateResponseDto = BookAdminUpdateResponseDto.builder()
            .bookId(1L)
            .build();

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
        BookAdminUpdateRequestDto bookAdminUpdateRequestDto = new BookAdminUpdateRequestDto();
        BookAdminUpdateResponseDto bookAdminUpdateResponseDto = BookAdminUpdateResponseDto.builder()
            .build();
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