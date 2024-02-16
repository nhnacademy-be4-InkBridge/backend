package com.nhnacademy.inkbridge.backend.controller;

import com.nhnacademy.inkbridge.backend.dto.book.BookAdminReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.book.BookCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.book.BooksAdminReadResponseDto;
import com.nhnacademy.inkbridge.backend.enums.BookMessageEnum;
import com.nhnacademy.inkbridge.backend.exception.ValidationException;
import com.nhnacademy.inkbridge.backend.service.BookService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

    @GetMapping
    public ResponseEntity<List<BooksAdminReadResponseDto>> readBooks(Pageable pageable) {
        List<BooksAdminReadResponseDto> booksDtoByAdmin = bookService.readBooksByAdmin(pageable)
            .getContent();
        return new ResponseEntity<>(booksDtoByAdmin, HttpStatus.OK);
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<BookAdminReadResponseDto> readBook(@PathVariable Long bookId) {
        BookAdminReadResponseDto bookDtoByAdmin = bookService.readBookByAdmin(bookId);
        return new ResponseEntity<>(bookDtoByAdmin, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<HttpStatus> createBook(
        @Valid @RequestBody BookCreateRequestDto bookCreateRequestDto,
        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(BookMessageEnum.BOOK_VALID_FAIL.toString());
        }
        bookService.createBook(bookCreateRequestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
