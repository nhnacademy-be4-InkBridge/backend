package com.nhnacademy.inkbridge.backend.controller;

import com.nhnacademy.inkbridge.backend.dto.book.BookCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.book.BookReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.book.BooksReadResponseDto;
import com.nhnacademy.inkbridge.backend.enums.BookMessageEnum;
import com.nhnacademy.inkbridge.backend.exception.ValidationException;
import com.nhnacademy.inkbridge.backend.service.BookService;
import java.awt.print.Pageable;
import java.util.List;
import javax.validation.Valid;
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
 * class: BookRestController.
 *
 * @author minm063
 * @version 2024/02/14
 */

@RestController
@RequestMapping("/api/books")
public class BookRestController {

    private final BookService bookService;

    public BookRestController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<List<BooksReadResponseDto>> readBooks(Pageable pageable) {
        List<BooksReadResponseDto> booksReadResponseDtoList = bookService.readBooks(pageable).getContent();
        return new ResponseEntity<>(booksReadResponseDtoList, HttpStatus.OK);
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<BookReadResponseDto> readBook(@PathVariable Long bookId) {
        BookReadResponseDto bookReadResponseDto = bookService.readBook(bookId);
        return new ResponseEntity<>(bookReadResponseDto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<HttpStatus> createBook(@Valid @RequestBody BookCreateRequestDto bookCreateRequestDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(BookMessageEnum.BOOK_VALID_FAIL.toString());
        }
        bookService.createBook(bookCreateRequestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
