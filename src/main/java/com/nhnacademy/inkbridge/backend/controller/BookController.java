package com.nhnacademy.inkbridge.backend.controller;

import com.nhnacademy.inkbridge.backend.dto.book.BookReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.book.BooksReadResponseDto;
import com.nhnacademy.inkbridge.backend.service.BookService;
import javax.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
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
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    /**
     * 메인 페이지 도서 목록 조회 api입니다.
     *
     * @param pageable pagination
     * @return BooksReadResponseDto List
     */
    @GetMapping
    public ResponseEntity<Page<BooksReadResponseDto>> readBooks(Pageable pageable) {
        Page<BooksReadResponseDto> content = bookService.readBooks(pageable);
        return new ResponseEntity<>(content, HttpStatus.OK);
    }

    /**
     * 도서 상세 조회 api입니다.
     *
     * @param bookId Long
     * @return BookReadResponseDto
     */
    @GetMapping("/{bookId}")
    public ResponseEntity<BookReadResponseDto> readBook(@PathVariable Long bookId,
        HttpServletRequest request) {
        Long memberId = Long.parseLong(request.getHeader("Authorization-Id"));
        BookReadResponseDto bookReadResponseDto = bookService.readBook(bookId, memberId);
        return new ResponseEntity<>(bookReadResponseDto, HttpStatus.OK);
    }
}
