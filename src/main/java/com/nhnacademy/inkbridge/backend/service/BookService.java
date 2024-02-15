package com.nhnacademy.inkbridge.backend.service;

import com.nhnacademy.inkbridge.backend.dto.book.BookCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.book.BookReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.book.BooksAdminReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.book.BooksReadResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * class: BookService.
 *
 * @author minm063
 * @version 2024/02/14
 */
public interface BookService {

    Page<BooksReadResponseDto> readBooks(Pageable pageable);

    BookReadResponseDto readBook(Long bookId);

    Page<BooksAdminReadResponseDto> readBooksByAdmin(Pageable pageable);

    void createBook(BookCreateRequestDto bookCreateRequestDto);
}
