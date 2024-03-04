package com.nhnacademy.inkbridge.backend.repository.impl;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import com.nhnacademy.inkbridge.backend.dto.book.BooksReadResponseDto;
import com.nhnacademy.inkbridge.backend.entity.Book;
import com.nhnacademy.inkbridge.backend.entity.File;
import com.nhnacademy.inkbridge.backend.entity.Publisher;
import com.nhnacademy.inkbridge.backend.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * class: BookRepositoryImplTest.
 *
 * @author minm063
 * @version 2024/02/29
 */
@DataJpaTest
class BookRepositoryImplTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    BookRepository bookRepository;

    Book book;

    @BeforeEach
    void setup() {
        book = Book.builder().bookTitle("title").bookIndex("index").price(10000L)
            .isbn("1234567890123").publisher(mock(Publisher.class)).thumbnailFile(mock(File.class))
            .build();
        book = testEntityManager.persist(book);
    }

    @Test
    void findAllBooks() {
        Pageable pageable = mock(Pageable.class);
        Page<BooksReadResponseDto> books = bookRepository.findAllBooks(pageable);

        assertAll(
            () -> assertEquals(1, books.getSize()),
            () -> assertEquals(book.getBookTitle(), books.getContent().get(0).getBookTitle())
        );

    }

    @Test
    void findByBookId() {
    }

    @Test
    void findAllBooksByAdmin() {
    }

    @Test
    void findBookByAdminByBookId() {
    }
}