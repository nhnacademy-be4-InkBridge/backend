package com.nhnacademy.inkbridge.backend.controller;

import com.nhnacademy.inkbridge.backend.dto.book.BookAdminCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.book.BookAdminReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.book.BookAdminUpdateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.book.BooksAdminReadResponseDto;
import com.nhnacademy.inkbridge.backend.exception.ValidationException;
import com.nhnacademy.inkbridge.backend.service.BookService;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * class: BookAdminRestController.
 *
 * @author minm063
 * @version 2024/02/15
 */

@Slf4j
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
    public ResponseEntity<Page<BooksAdminReadResponseDto>> readBooks(Pageable pageable) {
        Page<BooksAdminReadResponseDto> booksDtoByAdmin = bookService.readBooksByAdmin(
            pageable);
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
        @RequestPart(value = "thumbnail") MultipartFile thumbnail,
        @Valid @RequestPart(value = "book") BookAdminCreateRequestDto bookAdminCreateRequestDto,
        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            FieldError firstError = bindingResult.getFieldErrors().get(0);
            log.info("ERROR:" + firstError.getDefaultMessage());
            throw new ValidationException(firstError.getDefaultMessage());
        }
        bookService.createBook(thumbnail, bookAdminCreateRequestDto);
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
    public ResponseEntity<HttpStatus> updateBook(@PathVariable Long bookId,
        @RequestPart(value = "thumbnail") MultipartFile thumbnail,
        @RequestPart(value = "bookImages", required = false) MultipartFile[] bookImages,
        @Valid @RequestBody BookAdminUpdateRequestDto bookAdminUpdateRequestDto,
        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            FieldError firstError = bindingResult.getFieldErrors().get(0);
            log.info("ERROR:" + firstError.getDefaultMessage());
            throw new ValidationException(firstError.getDefaultMessage());
        }
        bookService.updateBookByAdmin(bookId, thumbnail, bookImages, bookAdminUpdateRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}