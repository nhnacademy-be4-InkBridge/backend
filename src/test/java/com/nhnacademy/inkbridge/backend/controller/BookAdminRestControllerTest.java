//package com.nhnacademy.inkbridge.backend.controller;
//
//import static org.hamcrest.Matchers.equalTo;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyLong;
//import static org.mockito.Mockito.doNothing;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;
//import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
//import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.nhnacademy.inkbridge.backend.dto.book.BookAdminCreateRequestDto;
//import com.nhnacademy.inkbridge.backend.dto.book.BookAdminSelectedReadResponseDto;
//import com.nhnacademy.inkbridge.backend.dto.book.BookAdminUpdateRequestDto;
//import com.nhnacademy.inkbridge.backend.dto.book.BooksAdminReadResponseDto;
//import com.nhnacademy.inkbridge.backend.exception.ValidationException;
//import com.nhnacademy.inkbridge.backend.service.BookService;
//import java.math.BigDecimal;
//import java.time.LocalDate;
//import java.util.List;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.Pageable;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.MediaType;
//import org.springframework.mock.web.MockMultipartFile;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.util.ReflectionTestUtils;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.web.multipart.MultipartFile;
//
///**
// * class: BookAdminRestControllerTest.
// *
// * @author minm063
// * @version 2024/02/16
// */
//@AutoConfigureRestDocs
//@WebMvcTest(BookAdminRestController.class)
//class BookAdminRestControllerTest {
//
//    @Autowired
//    MockMvc mockMvc;
//
//    @MockBean
//    BookService bookService;
//
//    @MockBean
//    Pageable pageable;
//
//    @Test
//    @WithMockUser
//    void whenAdminReadBooks_thenReturnDtoList() throws Exception {
//        BooksAdminReadResponseDto booksAdminReadResponseDto = BooksAdminReadResponseDto.builder()
//            .bookId(1L)
//            .bookTitle("title")
//            .authorName("author")
//            .publisherName("publisher")
//            .statusName("status")
//            .build();
//        Page<BooksAdminReadResponseDto> page = new PageImpl<>(List.of(booksAdminReadResponseDto));
//        when(bookService.readBooksByAdmin(any(Pageable.class))).thenReturn(page);
//
//        mockMvc.perform(get("/api/admin/books")
//                .contentType(MediaType.APPLICATION_JSON))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//            .andExpect(jsonPath("$.content.[0].bookId", equalTo(1)))
//            .andExpect(jsonPath("$.content.[0].bookTitle", equalTo("title")))
//            .andExpect(jsonPath("$.content.[0].authorName", equalTo("author")))
//            .andExpect(jsonPath("$.content.[0].publisherName", equalTo("publisher")))
//            .andExpect(jsonPath("$.content.[0].statusName", equalTo("status")))
//            .andDo(document("docs"));
//
//    }
//
//    @Test
//    @WithMockUser
//    void whenAdminReadBook_thenReturnDto() throws Exception {
//        BookAdminSelectedReadResponseDto bookAdminSelectedReadResponseDto = BookAdminSelectedReadResponseDto.builder()
//            .bookTitle("title")
//            .bookIndex("index")
//            .description("description")
//            .publicatedAt(LocalDate.of(2022, 2, 27))
//            .isbn("1234567890123")
//            .regularPrice(1L)
//            .price(1L)
//            .discountRatio(BigDecimal.valueOf(33.3))
//            .stock(100)
//            .isPackagable(true)
//            .authorId(1L)
//            .publisherId(1L)
//            .statusId(1L)
//            .url("test")
//            .build();
//
//        when(bookService.readBookByAdmin(anyLong())).thenReturn(bookAdminSelectedReadResponseDto);
//
//        mockMvc.perform(get("/api/admin/books/{bookId}", 1L)
//                .contentType(MediaType.APPLICATION_JSON))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//            .andExpect(jsonPath("$.bookTitle", equalTo("title")))
//            .andExpect(jsonPath("$.bookIndex", equalTo("index")))
//            .andExpect(jsonPath("$.description", equalTo("description")))
//            .andExpect(jsonPath("$.publicatedAt", equalTo("2022-02-27")))
//            .andExpect(jsonPath("$.isbn", equalTo("1234567890123")))
//            .andExpect(jsonPath("$.regularPrice", equalTo(1)))
//            .andExpect(jsonPath("$.price", equalTo(1)))
//            .andExpect(jsonPath("$.discountRatio", equalTo(33.3)))
//            .andExpect(jsonPath("$.stock", equalTo(100)))
//            .andExpect(jsonPath("$.isPackagable", equalTo(true)))
//            .andExpect(jsonPath("$.authorId", equalTo(1)))
//            .andExpect(jsonPath("$.publisherId", equalTo(1)))
//            .andExpect(jsonPath("$.statusId", equalTo(1)))
//            .andExpect(jsonPath("$.url", equalTo("test")))
//            .andDo(document("docs"));
//    }
//
//    @Test
//    @WithMockUser
//    void whenCreateBook_thenReturnHttpStatus() throws Exception {
//        ObjectMapper objectMapper = new ObjectMapper();
//        BookAdminCreateRequestDto bookAdminCreateRequestDto = new BookAdminCreateRequestDto();
//        ReflectionTestUtils.setField(bookAdminCreateRequestDto, "bookTitle", "title");
//        ReflectionTestUtils.setField(bookAdminCreateRequestDto, "isbn", "1234567890123");
//
//        String dtoToJson = objectMapper.writeValueAsString(bookAdminCreateRequestDto);
//        MockMultipartFile book = new MockMultipartFile("book", "book", "application/json",
//            dtoToJson.getBytes());
//
//        MockMultipartFile thumbnail = new MockMultipartFile(
//            "image",
//            "thumbnail",
//            MediaType.IMAGE_PNG_VALUE,
//            "thumbnail".getBytes()
//        );
//
//        doNothing().when(bookService)
//            .createBook(mock(MultipartFile.class), bookAdminCreateRequestDto);
//
//        mockMvc.perform(multipart("/api/admin/books")
//                .file(thumbnail)
//                .file(book)
//                .with(csrf())
//            )
//            .andExpect(status().isCreated())
//            .andDo(document("docs"));
//    }
//
//    @Test
//    @WithMockUser
//    void givenInvalidRequest_whenCreateBook_thenThrowValidationException() throws Exception {
//        ObjectMapper objectMapper = new ObjectMapper();
//        BookAdminCreateRequestDto bookAdminCreateRequestDto = new BookAdminCreateRequestDto();
//
//        String dtoToJson = objectMapper.writeValueAsString(bookAdminCreateRequestDto);
//        MockMultipartFile book = new MockMultipartFile("book", "book", "application/json",
//            dtoToJson.getBytes());
//
//        MockMultipartFile thumbnail = new MockMultipartFile("image", "thumbnail",
//            MediaType.IMAGE_PNG_VALUE, "thumbnail".getBytes());
//
//        doNothing().when(bookService)
//            .createBook(mock(MultipartFile.class), bookAdminCreateRequestDto);
//
//        // Perform the request
//        mockMvc.perform(multipart("/api/admin/books")
//                .file(thumbnail)
//                .file(book)
//                .with(csrf())
//            )
//            .andExpect(status().isUnprocessableEntity())
//            .andExpect(
//                result -> assertTrue(result.getResolvedException() instanceof ValidationException))
//            .andDo(document("docs"));
//    }
//
//    @Test
//    @WithMockUser
//    void whenUpdateBook_thenReturnDto() throws Exception {
//        ObjectMapper objectMapper = new ObjectMapper();
//        BookAdminUpdateRequestDto bookAdminUpdateRequestDto = new BookAdminUpdateRequestDto();
//        ReflectionTestUtils.setField(bookAdminUpdateRequestDto, "bookTitle", "title");
//        ReflectionTestUtils.setField(bookAdminUpdateRequestDto, "isbn", "1234567890123");
//
//        String dtoToJson = objectMapper.writeValueAsString(bookAdminUpdateRequestDto);
//        MockMultipartFile book = new MockMultipartFile("book", "book",
//            "application/json", dtoToJson.getBytes());
//        MockMultipartFile thumbnail = new MockMultipartFile("image", "thumbnail",
//            MediaType.IMAGE_PNG_VALUE, "thumbnail".getBytes());
//
//        doNothing().when(bookService).updateBookByAdmin(anyLong(), any(MultipartFile.class), any());
//
//        mockMvc.perform(multipart(HttpMethod.PUT, "/api/admin/books/{bookId}", 1L)
//                .file(book)
//                .file(thumbnail)
//                .with(csrf()))
//            .andExpect(status().isOk())
//            .andDo(document("docs"));
//    }
//
//    @Test
//    @WithMockUser
//    void givenInvalidRequest_whenUpdateBook_thenThrowValidationException() throws Exception {
//        ObjectMapper objectMapper = new ObjectMapper();
//        BookAdminUpdateRequestDto bookAdminUpdateRequestDto = new BookAdminUpdateRequestDto();
//
//        String dtoToJson = objectMapper.writeValueAsString(bookAdminUpdateRequestDto);
//        MockMultipartFile book = new MockMultipartFile("book", "book",
//            "application/json", dtoToJson.getBytes());
//        MockMultipartFile thumbnail = new MockMultipartFile("image", "thumbnail",
//            MediaType.IMAGE_PNG_VALUE, "thumbnail".getBytes());
//
//        doNothing().when(bookService).updateBookByAdmin(anyLong(), any(MultipartFile.class), any());
//
//        mockMvc.perform(multipart(HttpMethod.PUT, "/api/admin/books/{bookId}", 1L)
//                .file(book)
//                .file(thumbnail)
//                .with(csrf()))
//            .andExpect(status().isUnprocessableEntity())
//            .andExpect(
//                result -> assertTrue(result.getResolvedException() instanceof ValidationException))
//            .andDo(document("docs"));
//    }
//
//
//}