package com.nhnacademy.inkbridge.backend.service;

import com.nhnacademy.inkbridge.backend.dto.book.BookCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.book.BookCreateResponseDto;

/**
 * class: BookService.
 *
 * @author minm063
 * @version 2024/02/14
 */
public interface BookService {

    BookCreateResponseDto createBook(BookCreateRequestDto bookCreateRequestDto);
}
