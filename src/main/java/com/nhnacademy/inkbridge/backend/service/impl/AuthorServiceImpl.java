package com.nhnacademy.inkbridge.backend.service.impl;

import com.nhnacademy.inkbridge.backend.dto.author.AuthorInfoReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.author.AuthorReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.book.BooksPaginationReadResponseDto;
import com.nhnacademy.inkbridge.backend.repository.AuthorRepository;
import com.nhnacademy.inkbridge.backend.repository.BookRepository;
import com.nhnacademy.inkbridge.backend.service.AuthorService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * class: AuthorServiceImpl.
 *
 * @author minm063
 * @version 2024/03/14
 */
@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    /**
     * @param authorId
     * @param pageable
     * @return
     */
    @Override
    public AuthorReadResponseDto getAuthor(Long authorId, Pageable pageable) {
        Page<BooksPaginationReadResponseDto> books = bookRepository.findAllBooksByAuthor(pageable,
            authorId);
        AuthorInfoReadResponseDto author = authorRepository.findByAuthorId(authorId);
        return AuthorReadResponseDto.builder().booksPaginationReadResponseDtos(books)
            .authorInfoReadResponseDto(author).build();
    }
}
