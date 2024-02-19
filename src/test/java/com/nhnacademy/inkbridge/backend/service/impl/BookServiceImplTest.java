package com.nhnacademy.inkbridge.backend.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.mock;
import static org.mockito.BDDMockito.times;
import static org.mockito.BDDMockito.verify;
import static org.mockito.BDDMockito.when;

import com.nhnacademy.inkbridge.backend.dto.book.BookAdminCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.book.BookAdminReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.book.BookAdminUpdateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.book.BookAdminUpdateResponseDto;
import com.nhnacademy.inkbridge.backend.dto.book.BookReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.book.BooksAdminReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.book.BooksReadResponseDto;
import com.nhnacademy.inkbridge.backend.entity.Book;
import com.nhnacademy.inkbridge.backend.entity.BookStatus;
import com.nhnacademy.inkbridge.backend.entity.File;
import com.nhnacademy.inkbridge.backend.entity.Publisher;
import com.nhnacademy.inkbridge.backend.exception.NotFoundException;
import com.nhnacademy.inkbridge.backend.repository.BookRepository;
import com.nhnacademy.inkbridge.backend.repository.BookStatusRepository;
import com.nhnacademy.inkbridge.backend.repository.FileRepository;
import com.nhnacademy.inkbridge.backend.repository.PublisherRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

/**
 * class: BookServiceImplTest.
 *
 * @author minm063
 * @version 2024/02/18
 */
@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

    @InjectMocks
    BookServiceImpl bookService;

    @Mock
    BookRepository bookRepository;

    @Mock
    BookStatusRepository bookStatusRepository;

    @Mock
    FileRepository fileRepository;

    @Mock
    PublisherRepository publisherRepository;

    @Mock
    Pageable pageable;

    @BeforeEach
    void setUp() {
    }

    @Test
    void whenReadBooks() {
        BooksReadResponseDto booksReadResponseDto = mock(BooksReadResponseDto.class);
        Page<BooksReadResponseDto> resultPage = new PageImpl<>(List.of(booksReadResponseDto),
            pageable, 0);
        when(bookRepository.findAllBooks(any(Pageable.class))).thenReturn(resultPage);

        Page<BooksReadResponseDto> page = bookService.readBooks(pageable);

        assertEquals(1, page.getSize());
        verify(bookRepository, times(1)).findAllBooks(pageable);
    }

    @Test
    void readBook() {
        BookReadResponseDto bookReadResponseDto = mock(BookReadResponseDto.class);
        when(bookRepository.existsById(anyLong())).thenReturn(true);
        when(bookRepository.findByBookId(anyLong())).thenReturn(bookReadResponseDto);

        BookReadResponseDto book = bookService.readBook(1L);

        assertNotNull(book);
        verify(bookRepository, times(1)).existsById(anyLong());
        verify(bookRepository, times(1)).findByBookId(anyLong());
    }

    @Test
    void givenInvalidParameter_whenReadBook_thenThrowNotFoundException() {
        when(bookRepository.existsById(anyLong())).thenReturn(false);

        assertThrows(NotFoundException.class, () -> bookService.readBook(1L));
        verify(bookRepository, times(1)).existsById(anyLong());
    }

    @Test
    void readBooksByAdmin() {
        BooksAdminReadResponseDto booksAdminReadResponseDto = mock(BooksAdminReadResponseDto.class);
        Page<BooksAdminReadResponseDto> resultPage = new PageImpl<>(
            List.of(booksAdminReadResponseDto), pageable, 0);
        when(bookRepository.findAllBooksByAdmin(any(Pageable.class))).thenReturn(resultPage);

        Page<BooksAdminReadResponseDto> page = bookService.readBooksByAdmin(
            pageable);

        assertEquals(1, page.getSize());
        verify(bookRepository, times(1)).findAllBooksByAdmin(pageable);
    }

    @Test
    void readBookByAdmin() {
        BookAdminReadResponseDto bookAdminReadResponseDto = mock(BookAdminReadResponseDto.class);
        when(bookRepository.existsById(anyLong())).thenReturn(true);
        when(bookRepository.findBookByAdminByBookId(anyLong())).thenReturn(
            bookAdminReadResponseDto);

        BookAdminReadResponseDto book = bookService.readBookByAdmin(1L);

        assertNotNull(book);
        verify(bookRepository, times(1)).existsById(anyLong());
        verify(bookRepository, times(1)).findBookByAdminByBookId(anyLong());
    }

    @Test
    void givenInvalidParameter_whenReadBookByAdmin_thenThrowNotFoundException() {
        when(bookRepository.existsById(anyLong())).thenReturn(false);

        assertThrows(NotFoundException.class, () -> bookService.readBookByAdmin(1L));
        verify(bookRepository, times(1)).existsById(anyLong());
    }

    @Test
    void createBook() {
        BookAdminCreateRequestDto bookAdminCreateRequestDto = mock(BookAdminCreateRequestDto.class);
        BookStatus bookStatus = mock(BookStatus.class);
        File file = mock(File.class);
        Publisher publisher = mock(Publisher.class);
        when(bookStatusRepository.findById(anyLong())).thenReturn(Optional.of(bookStatus));
        when(fileRepository.findById(anyLong())).thenReturn(Optional.ofNullable(file));
        when(publisherRepository.findById(anyLong())).thenReturn(Optional.ofNullable(publisher));

        bookService.createBook(bookAdminCreateRequestDto);

        verify(bookRepository, times(1)).save(any(Book.class));
        verify(bookStatusRepository, times(1)).findById(anyLong());
        verify(fileRepository, times(1)).findById(anyLong());
        verify(publisherRepository, times(1)).findById(anyLong());
    }

    @Test
    void givenInvalidBookStatus_whenCreateBook_thenThrowNotFoundException() {
        BookAdminCreateRequestDto bookAdminCreateRequestDto = mock(BookAdminCreateRequestDto.class);
        when(bookStatusRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,
            () -> bookService.createBook(bookAdminCreateRequestDto));
        verify(bookStatusRepository, times(1)).findById(anyLong());
    }

    @Test
    void givenInvalidThumbnailFile_whenCreateBook_thenThrowNotFoundException() {
        BookAdminCreateRequestDto bookAdminCreateRequestDto = mock(BookAdminCreateRequestDto.class);
        when(bookStatusRepository.findById(anyLong())).thenReturn(
            Optional.of(mock(BookStatus.class)));
        when(fileRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,
            () -> bookService.createBook(bookAdminCreateRequestDto));
        verify(bookStatusRepository, times(1)).findById(anyLong());
        verify(fileRepository, times(1)).findById(anyLong());
    }

    @Test
    void givenInvalidPublisher_whenCreateBook_thenThrowNotFoundException() {
        BookAdminCreateRequestDto bookAdminCreateRequestDto = mock(BookAdminCreateRequestDto.class);
        when(bookStatusRepository.findById(anyLong())).thenReturn(
            Optional.of(mock(BookStatus.class)));
        when(fileRepository.findById(anyLong())).thenReturn(Optional.of(mock(File.class)));
        when(publisherRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,
            () -> bookService.createBook(bookAdminCreateRequestDto));
        verify(bookStatusRepository, times(1)).findById(anyLong());
        verify(fileRepository, times(1)).findById(anyLong());
        verify(publisherRepository, times(1)).findById(anyLong());
    }

    @Test
    void updateBookByAdmin() {
        BookAdminUpdateRequestDto bookAdminUpdateRequestDto = mock(BookAdminUpdateRequestDto.class);
        Book book = mock(Book.class);
        BookStatus bookStatus = mock(BookStatus.class);
        File file = mock(File.class);
        Publisher publisher = mock(Publisher.class);
        when(bookRepository.findById(anyLong())).thenReturn(Optional.ofNullable(book));
        when(bookStatusRepository.findById(anyLong())).thenReturn(Optional.of(bookStatus));
        when(fileRepository.findById(anyLong())).thenReturn(Optional.ofNullable(file));
        when(publisherRepository.findById(anyLong())).thenReturn(Optional.ofNullable(publisher));

        BookAdminUpdateResponseDto bookAdminUpdateResponseDto = bookService.updateBookByAdmin(1L,
            bookAdminUpdateRequestDto);

        assertEquals(1, bookAdminUpdateResponseDto.getBookId());
        verify(bookRepository, times(1)).findById(anyLong());
        verify(bookStatusRepository, times(1)).findById(anyLong());
        verify(fileRepository, times(1)).findById(anyLong());
        verify(publisherRepository, times(1)).findById(anyLong());
    }

    @Test
    void givenInvalidBook_whenUpdateBook_thenThrowNotFoundException() {
        BookAdminUpdateRequestDto bookAdminUpdateRequestDto = mock(BookAdminUpdateRequestDto.class);

        when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,
            () -> bookService.updateBookByAdmin(1L, bookAdminUpdateRequestDto));
        verify(bookRepository, times(1)).findById(anyLong());
    }

    @Test
    void givenInvalidPublisher_whenUpdateBook_thenThrowNotFoundException() {
        BookAdminUpdateRequestDto bookAdminUpdateRequestDto = mock(BookAdminUpdateRequestDto.class);
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(mock(Book.class)));
        when(publisherRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,
            () -> bookService.updateBookByAdmin(1L, bookAdminUpdateRequestDto));
        verify(bookRepository, times(1)).findById(anyLong());
        verify(publisherRepository, times(1)).findById(anyLong());
    }

    @Test
    void givenInvalidBookStatus_whenUpdateBook_thenThrowNotFoundException() {
        BookAdminUpdateRequestDto bookAdminUpdateRequestDto = mock(BookAdminUpdateRequestDto.class);

        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(mock(Book.class)));
        when(publisherRepository.findById(anyLong())).thenReturn(
            Optional.of(mock(Publisher.class)));
        when(bookStatusRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,
            () -> bookService.updateBookByAdmin(1L, bookAdminUpdateRequestDto));
        verify(bookRepository, times(1)).findById(anyLong());
        verify(publisherRepository, times(1)).findById(anyLong());
        verify(bookStatusRepository, times(1)).findById(anyLong());
    }

    @Test
    void givenInvalidThumbnailFile_whenUpdateBook_thenThrowNotFoundException() {
        BookAdminUpdateRequestDto bookAdminUpdateRequestDto = mock(BookAdminUpdateRequestDto.class);

        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(mock(Book.class)));
        when(publisherRepository.findById(anyLong())).thenReturn(
            Optional.of(mock(Publisher.class)));
        when(bookStatusRepository.findById(anyLong())).thenReturn(
            Optional.of(mock(BookStatus.class)));
        when(fileRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,
            () -> bookService.updateBookByAdmin(1L, bookAdminUpdateRequestDto));
        verify(bookRepository, times(1)).findById(anyLong());
        verify(publisherRepository, times(1)).findById(anyLong());
        verify(bookStatusRepository, times(1)).findById(anyLong());
        verify(fileRepository, times(1)).findById(anyLong());

    }
}