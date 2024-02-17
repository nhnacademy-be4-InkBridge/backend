package com.nhnacademy.inkbridge.backend.service.impl;

import com.nhnacademy.inkbridge.backend.dto.bookcategory.BookCategoryReadResponseDto;
import com.nhnacademy.inkbridge.backend.entity.BookCategory;
import com.nhnacademy.inkbridge.backend.enums.BookMessageEnum;
import com.nhnacademy.inkbridge.backend.exception.NotFoundException;
import com.nhnacademy.inkbridge.backend.repository.BookCategoryRepository;
import com.nhnacademy.inkbridge.backend.service.BookCategoryService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * class: BookCategoryServiceImpl.
 *
 * @author choijaehun
 * @version 2/17/24
 */

@Service
@RequiredArgsConstructor
public class BookCategoryServiceImpl implements BookCategoryService {

    private final BookCategoryRepository bookCategoryRepository;

    @Override
    public List<BookCategoryReadResponseDto> readBookCategory(Long bookId) {
        List<BookCategory> bookCategoryByBookId = bookCategoryRepository.findByPk_BookId(bookId);
        if(bookCategoryByBookId == null){
            throw new NotFoundException(BookMessageEnum.BOOK_NOT_FOUND.toString());
        }

        return bookCategoryByBookId.stream()
            .map(BookCategoryReadResponseDto::new)
            .collect(Collectors.toList());
    }
}
