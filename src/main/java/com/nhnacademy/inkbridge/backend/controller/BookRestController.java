package com.nhnacademy.inkbridge.backend.controller;

import com.nhnacademy.inkbridge.backend.dto.book.BookReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.book.BooksReadResponseDto;
import com.nhnacademy.inkbridge.backend.service.BookService;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
        List<BooksReadResponseDto> content = bookService.readBooks(pageable)
            .getContent();
        return new ResponseEntity<>(content, HttpStatus.OK);
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<BookReadResponseDto> readBook(@PathVariable Long bookId) {
        BookReadResponseDto bookReadResponseDto = bookService.readBook(bookId);
        return new ResponseEntity<>(bookReadResponseDto, HttpStatus.OK);
    }
}
