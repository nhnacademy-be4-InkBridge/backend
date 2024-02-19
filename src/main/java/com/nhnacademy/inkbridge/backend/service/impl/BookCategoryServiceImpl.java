package com.nhnacademy.inkbridge.backend.service.impl;

import com.nhnacademy.inkbridge.backend.dto.bookcategory.BookCategoryCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.bookcategory.BookCategoryReadResponseDto;
import com.nhnacademy.inkbridge.backend.entity.Book;
import com.nhnacademy.inkbridge.backend.entity.BookCategory;
import com.nhnacademy.inkbridge.backend.entity.Category;
import com.nhnacademy.inkbridge.backend.enums.BookCategoryMessageEnum;
import com.nhnacademy.inkbridge.backend.enums.BookMessageEnum;
import com.nhnacademy.inkbridge.backend.enums.CategoryMessageEnum;
import com.nhnacademy.inkbridge.backend.exception.NotFoundException;
import com.nhnacademy.inkbridge.backend.repository.BookCategoryRepository;
import com.nhnacademy.inkbridge.backend.repository.BookRepository;
import com.nhnacademy.inkbridge.backend.repository.CategoryRepository;
import com.nhnacademy.inkbridge.backend.service.BookCategoryService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final CategoryRepository categoryRepository;
    private final BookRepository bookRepository;

    @Override
    @Transactional
    public void createBookCategory(BookCategoryCreateRequestDto request) {
        BookCategory.Pk pk = BookCategory.Pk.builder().categoryId(request.getCategoryId()).bookId(
            request.getBookId()).build();

        Category category = categoryRepository.findById(request.getCategoryId())
            .orElseThrow(() -> new NotFoundException(
                CategoryMessageEnum.CATEGORY_NOT_FOUND.toString()));

        Book book = bookRepository.findById(request.getBookId())
            .orElseThrow(() -> new NotFoundException(
                BookMessageEnum.BOOK_NOT_FOUND.toString()));

        BookCategory bookCategory = BookCategory.create().pk(pk).category(category).book(book).build();
        bookCategoryRepository.save(bookCategory);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookCategoryReadResponseDto> readBookCategory(Long bookId) {
        List<BookCategory> bookCategoryByBookId = bookCategoryRepository.findByPk_BookId(bookId);
        if (bookCategoryByBookId == null) {
            throw new NotFoundException(BookMessageEnum.BOOK_NOT_FOUND.toString());
        }

        return bookCategoryByBookId.stream()
            .map(BookCategoryReadResponseDto::new)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteBookCategory(Long bookId) {
        List<BookCategory> bookCategories = bookCategoryRepository.findByPk_BookId(bookId);
        if(bookCategories ==null){
            throw new NotFoundException(BookCategoryMessageEnum.BOOK_CATEGORY_NOT_FOUND.toString());
        }

        bookCategoryRepository.deleteByPk_BookId(bookId);
    }
}