package com.nhnacademy.inkbridge.backend.controller;

import com.nhnacademy.inkbridge.backend.dto.book.BookAdminCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.book.BookAdminReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.book.BookAdminUpdateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.book.BookAdminUpdateResponseDto;
import com.nhnacademy.inkbridge.backend.dto.book.BooksAdminReadResponseDto;
import com.nhnacademy.inkbridge.backend.exception.ValidationException;
import com.nhnacademy.inkbridge.backend.service.BookService;
import java.util.List;
import java.util.Objects;
import javax.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * class: BookAdminRestController.
 *
 * @author minm063
 * @version 2024/02/15
 */

@RestController
@RequestMapping("/api/admin/books")
public class BookAdminRestController {

    private final BookService bookService;

    public BookAdminRestController(BookService bookService) {
        this.bookService = bookService;
    }

    /**
     * admin 도서 목록 조회 api입니다.
     *
     * @param pageable pagination
     * @return ResponseEntity
     */
    @GetMapping
    public ResponseEntity<List<BooksAdminReadResponseDto>> readBooks(Pageable pageable) {
        List<BooksAdminReadResponseDto> booksDtoByAdmin = bookService.readBooksByAdmin(pageable)
            .getContent();
        return new ResponseEntity<>(booksDtoByAdmin, HttpStatus.OK);
    }

    /**
     * admin 도서 상세 조회 api입니다.
     *
     * @param bookId Long
     * @return ResponseEntity
     */
    @GetMapping("/{bookId}")
    public ResponseEntity<BookAdminReadResponseDto> readBook(@PathVariable Long bookId) {
        BookAdminReadResponseDto bookDtoByAdmin = bookService.readBookByAdmin(bookId);
        return new ResponseEntity<>(bookDtoByAdmin, HttpStatus.OK);
    }

    /**
     * admin 도서 등록 api입니다.
     *
     * @param bookAdminCreateRequestDto bookAdminCreateRequestDto
     * @param bindingResult             validation result
     * @return ResponseEntity
     */
    @PostMapping
    public ResponseEntity<HttpStatus> createBook(
        @Valid @RequestBody BookAdminCreateRequestDto bookAdminCreateRequestDto,
        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(
                Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        bookService.createBook(bookAdminCreateRequestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * admin 도서 업데이트 api입니다.
     *
     * @param bookId                    Long
     * @param bookAdminUpdateRequestDto bookAdminUpdateRequestDto
     * @param bindingResult             validation result
     * @return ResponseEntity
     */
    @PutMapping("/{bookId}")
    public ResponseEntity<BookAdminUpdateResponseDto> updateBook(@PathVariable Long bookId,
        @Valid @RequestBody BookAdminUpdateRequestDto bookAdminUpdateRequestDto,
        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(
                Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }

        BookAdminUpdateResponseDto bookAdminUpdateResponseDto = bookService.updateBookByAdmin(
            bookId, bookAdminUpdateRequestDto);
        return new ResponseEntity<>(bookAdminUpdateResponseDto, HttpStatus.OK);
    }
}
