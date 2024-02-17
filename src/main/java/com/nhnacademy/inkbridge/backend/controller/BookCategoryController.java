package com.nhnacademy.inkbridge.backend.controller;

import com.nhnacademy.inkbridge.backend.dto.bookcategory.BookCategoryReadResponseDto;
import com.nhnacademy.inkbridge.backend.service.BookCategoryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * class: BookCategoryController.
 *
 * @author choijaehun
 * @version 2/17/24
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("api/book-category")
public class BookCategoryController {

    private final BookCategoryService bookCategoryService;

    @GetMapping("{bookId}")
    public ResponseEntity<List<BookCategoryReadResponseDto>> readCategoryByBookId(
        @PathVariable Long bookId) {
        List<BookCategoryReadResponseDto> bookCategoryReadResponseDto = bookCategoryService.readBookCategory(
            bookId);
        return new ResponseEntity<>(bookCategoryReadResponseDto, HttpStatus.OK);
    }
}
