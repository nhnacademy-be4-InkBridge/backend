package com.nhnacademy.inkbridge.backend.service;

import com.nhnacademy.inkbridge.backend.dto.bookcategory.BookCategoryReadResponseDto;
import java.util.List;

/**
 * class: BookCategoryService.
 *
 * @author choijaehun
 * @version 2/17/24
 */
public interface BookCategoryService {

    List<BookCategoryReadResponseDto> readBookCategory(Long bookId);
}
