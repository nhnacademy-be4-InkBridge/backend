package com.nhnacademy.inkbridge.backend.controller;

import com.nhnacademy.inkbridge.backend.dto.book.BookCreateRequestDto;
import com.nhnacademy.inkbridge.backend.service.BookService;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
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

    @PostMapping
    public ResponseEntity<String> createBook(@Valid @RequestBody BookCreateRequestDto bookCreateRequestDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {

        }
        bookService.createBook(bookCreateRequestDto);
        return new ResponseEntity<>("success", HttpStatus.CREATED);
    }
}
