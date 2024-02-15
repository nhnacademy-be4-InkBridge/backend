package com.nhnacademy.inkbridge.backend.service;

import com.nhnacademy.inkbridge.backend.dto.book.BookCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.book.BookCreateResponseDto;
import com.nhnacademy.inkbridge.backend.dto.book.BookReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.book.BooksReadResponseDto;
import java.awt.print.Pageable;
import java.util.List;
import org.springframework.data.domain.Page;

/**
 * class: BookService.
 *
 * @author minm063
 * @version 2024/02/14
 */
public interface BookService {
    Page<BooksReadResponseDto> readBooks(Pageable pageable);
    BookReadResponseDto readBook(Long bookId);
    void createBook(BookCreateRequestDto bookCreateRequestDto);
}
