package com.nhnacademy.inkbridge.backend.controller;

import com.nhnacademy.inkbridge.backend.dto.search.BookSearchResponseDto;
import com.nhnacademy.inkbridge.backend.entity.Search;
import com.nhnacademy.inkbridge.backend.service.BookSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * class: SearchController.
 *
 * @author choijaehun
 * @version 2024/03/11
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class SearchController {

    private final BookSearchService bookSearchService;

    @GetMapping("/search")
    public ResponseEntity<Page<BookSearchResponseDto>> searchByText(@RequestParam String text,
        Pageable pageable) {
        Page<Search> searchPage = bookSearchService.searchByText(text,
            pageable);
        Page<BookSearchResponseDto> books = searchPage.map(
            BookSearchResponseDto::toBookSearchResponseDto);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @GetMapping("/books/filter")
    public ResponseEntity<Page<BookSearchResponseDto>> searchByAll(Pageable pageable) {
        Page<Search> searchPage = bookSearchService.searchByAll(pageable);
        Page<BookSearchResponseDto> books = searchPage.map(
            BookSearchResponseDto::toBookSearchResponseDto);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @GetMapping("/categories/{category}/books")
    public ResponseEntity<Page<BookSearchResponseDto>> readByCategory(@PathVariable String category, Pageable pageable){
        Page<Search> searchPage = bookSearchService.searchByCategory(category,pageable);
        Page<BookSearchResponseDto> books = searchPage.map(BookSearchResponseDto::toBookSearchResponseDto);
        return new ResponseEntity<>(books,HttpStatus.OK);
    }
}