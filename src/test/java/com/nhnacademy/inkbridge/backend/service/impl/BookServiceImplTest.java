//package com.nhnacademy.inkbridge.backend.service.impl;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.ArgumentMatchers.anyLong;
//import static org.mockito.BDDMockito.any;
//import static org.mockito.BDDMockito.mock;
//import static org.mockito.BDDMockito.times;
//import static org.mockito.BDDMockito.verify;
//import static org.mockito.BDDMockito.when;
//
//import com.nhnacademy.inkbridge.backend.dto.book.BookAdminCreateRequestDto;
//import com.nhnacademy.inkbridge.backend.dto.book.BookAdminSelectedReadResponseDto;
//import com.nhnacademy.inkbridge.backend.dto.book.BookAdminUpdateRequestDto;
//import com.nhnacademy.inkbridge.backend.dto.book.BookReadResponseDto;
//import com.nhnacademy.inkbridge.backend.dto.book.BooksAdminReadResponseDto;
//import com.nhnacademy.inkbridge.backend.dto.book.BooksReadResponseDto;
//import com.nhnacademy.inkbridge.backend.entity.Author;
//import com.nhnacademy.inkbridge.backend.entity.Book;
//import com.nhnacademy.inkbridge.backend.entity.BookAuthor;
//import com.nhnacademy.inkbridge.backend.entity.BookStatus;
//import com.nhnacademy.inkbridge.backend.entity.File;
//import com.nhnacademy.inkbridge.backend.entity.Publisher;
//import com.nhnacademy.inkbridge.backend.exception.NotFoundException;
//import com.nhnacademy.inkbridge.backend.repository.AuthorRepository;
//import com.nhnacademy.inkbridge.backend.repository.BookAuthorRepository;
//import com.nhnacademy.inkbridge.backend.repository.BookCategoryRepository;
//import com.nhnacademy.inkbridge.backend.repository.BookFileRepository;
//import com.nhnacademy.inkbridge.backend.repository.BookRepository;
//import com.nhnacademy.inkbridge.backend.repository.BookStatusRepository;
//import com.nhnacademy.inkbridge.backend.repository.PublisherRepository;
//import com.nhnacademy.inkbridge.backend.service.FileService;
//import java.util.Collections;
//import java.util.List;
//import java.util.Optional;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.Pageable;
//import org.springframework.http.MediaType;
//import org.springframework.mock.web.MockMultipartFile;
//
///**
// * class: BookServiceImplTest.
// *
// * @author minm063
// * @version 2024/02/18
// */
//@ExtendWith(MockitoExtension.class)
//class BookServiceImplTest {
//
//    @InjectMocks
//    BookServiceImpl bookService;
//
//    @Mock
//    BookRepository bookRepository;
//    @Mock
//    BookStatusRepository bookStatusRepository;
//    @Mock
//    PublisherRepository publisherRepository;
//    @Mock
//    AuthorRepository authorRepository;
//    @Mock
//    BookCategoryRepository bookCategoryRepository;
//    @Mock
//    BookAuthorRepository bookAuthorRepository;
//    @Mock
//    BookFileRepository bookFileRepository;
//    @Mock
//    FileService fileService;
//    @Mock
//    Pageable pageable;
//
//
//    @Test
//    void whenReadBooks_thenReturnPageDtoList() {
//        BooksReadResponseDto booksReadResponseDto = mock(BooksReadResponseDto.class);
//        Page<BooksReadResponseDto> resultPage = new PageImpl<>(List.of(booksReadResponseDto),
//            pageable, 0);
//
//        when(bookRepository.findAllBooks(any(Pageable.class))).thenReturn(resultPage);
//
//        Page<BooksReadResponseDto> page = bookService.readBooks(pageable);
//
//        assertEquals(1, page.getSize());
//        verify(bookRepository, times(1)).findAllBooks(pageable);
//    }
//
//    @Test
//    void whenReadBook_thenReturnDto() {
//        BookReadResponseDto bookReadResponseDto = mock(BookReadResponseDto.class);
//
//        when(bookRepository.existsById(anyLong())).thenReturn(true);
//        when(bookRepository.findByBookId(anyLong())).thenReturn(bookReadResponseDto);
//
//        BookReadResponseDto book = bookService.readBook(1L);
//
//        assertNotNull(book);
//        verify(bookRepository, times(1)).existsById(anyLong());
//        verify(bookRepository, times(1)).findByBookId(anyLong());
//    }
//
//    @Test
//    void givenInvalidParameter_whenReadBook_thenThrowNotFoundException() {
//        when(bookRepository.existsById(anyLong())).thenReturn(false);
//
//        assertThrows(NotFoundException.class, () -> bookService.readBook(1L));
//        verify(bookRepository, times(1)).existsById(anyLong());
//    }
//
//    @Test
//    void whenReadBooksByAdmin_thenReturnPageDtoList() {
//        BooksAdminReadResponseDto booksAdminReadResponseDto = mock(BooksAdminReadResponseDto.class);
//        Page<BooksAdminReadResponseDto> resultPage = new PageImpl<>(
//            List.of(booksAdminReadResponseDto), pageable, 0);
//
//        when(bookRepository.findAllBooksByAdmin(any(Pageable.class))).thenReturn(resultPage);
//
//        Page<BooksAdminReadResponseDto> page = bookService.readBooksByAdmin(
//            pageable);
//
//        assertEquals(1, page.getSize());
//        verify(bookRepository, times(1)).findAllBooksByAdmin(pageable);
//    }
//
////    @Test
////    void whenReadBookByAdmin_thenReturnDto() {
////        BookAdminSelectedReadResponseDto bookAdminSelectedReadResponseDto = mock(
////            BookAdminSelectedReadResponseDto.class);
////
////        when(bookRepository.existsById(anyLong())).thenReturn(true);
////        when(bookRepository.findBookByAdminByBookId(anyLong())).thenReturn(
////            bookAdminSelectedReadResponseDto);
////
////        BookAdminSelectedReadResponseDto book = bookService.readBookByAdmin(1L);
////
////        assertNotNull(book);
////        verify(bookRepository, times(1)).existsById(anyLong());
////        verify(bookRepository, times(1)).findBookByAdminByBookId(anyLong());
////    }
//
//    @Test
//    void givenInvalidParameter_whenReadBookByAdmin_thenThrowNotFoundException() {
//        when(bookRepository.existsById(anyLong())).thenReturn(false);
//
//        assertThrows(NotFoundException.class, () -> bookService.readBookByAdmin(1L));
//        verify(bookRepository, times(1)).existsById(anyLong());
//    }
//
//    @Test
//    void whenCreateBook() {
//        BookAdminCreateRequestDto bookAdminCreateRequestDto = mock(BookAdminCreateRequestDto.class);
//        Book book = Book.builder().build();
//        Author author = mock(Author.class);
//        Publisher publisher = mock(Publisher.class);
//
//        when(bookRepository.save(any(Book.class))).thenReturn(book);
//        when(authorRepository.findById(anyLong())).thenReturn(Optional.of(author));
//        when(publisherRepository.findById(anyLong())).thenReturn(Optional.of(publisher));
//        when(bookFileRepository.saveAll(any())).thenReturn(Collections.emptyList());
//        when(bookCategoryRepository.saveAll(any())).thenReturn(Collections.emptyList());
//
//        MockMultipartFile thumbnail = new MockMultipartFile("thumbnail", "thumbnail",
//            MediaType.IMAGE_PNG_VALUE, "thumbnail".getBytes());
//
//        bookService.createBook(thumbnail, bookAdminCreateRequestDto);
//
//        verify(bookRepository, times(1)).save(any(Book.class));
//
//        verify(publisherRepository, times(1)).findById(anyLong());
//        verify(authorRepository, times(1)).findById(anyLong());
//
//        verify(bookFileRepository).saveAll(any());
//        verify(bookCategoryRepository).saveAll(any());
//    }
//
//    @Test
//    void givenInvalidPublisher_whenCreateBook_thenThrowNotFoundException() {
//        BookAdminCreateRequestDto bookAdminCreateRequestDto = mock(BookAdminCreateRequestDto.class);
//
//        when(publisherRepository.findById(anyLong())).thenReturn(Optional.empty());
//
//        MockMultipartFile thumbnail = new MockMultipartFile("thumbnail", "thumbnail",
//            MediaType.IMAGE_PNG_VALUE, "thumbnail".getBytes());
//
//        assertThrows(NotFoundException.class,
//            () -> bookService.createBook(thumbnail, bookAdminCreateRequestDto));
//        verify(publisherRepository, times(1)).findById(anyLong());
//    }
//
//    @Test
//    void whenUpdateBookByAdmin() {
//        BookAdminUpdateRequestDto bookAdminUpdateRequestDto = mock(BookAdminUpdateRequestDto.class);
//        Long bookId = 1L;
//        Book book = mock(Book.class);
//        Publisher publisher = mock(Publisher.class);
//        BookStatus bookStatus = mock(BookStatus.class);
//        File file = mock(File.class);
//        Author author = mock(Author.class);
//        BookAuthor bookAuthor = mock(BookAuthor.class);
//
//        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(book));
//        when(publisherRepository.findById(anyLong())).thenReturn(Optional.of(publisher));
//        when(bookStatusRepository.findById(anyLong())).thenReturn(Optional.of(bookStatus));
//        when(fileService.saveFile(any())).thenReturn(file);
//        when(bookAuthorRepository.findByPk_BookId(anyLong())).thenReturn(bookAuthor);
//
//        when(authorRepository.findById(anyLong())).thenReturn(Optional.of(author));
//        when(bookFileRepository.saveAll(any())).thenReturn(Collections.emptyList());
//        when(bookCategoryRepository.saveAll(any())).thenReturn(Collections.emptyList());
//
//        MockMultipartFile thumbnail = new MockMultipartFile("thumbnail", "thumbnail",
//            MediaType.IMAGE_PNG_VALUE, "thumbnail".getBytes());
//
//        bookService.updateBookByAdmin(bookId, thumbnail, bookAdminUpdateRequestDto);
//
//        verify(authorRepository, times(1)).findById(anyLong());
//
//        verify(publisherRepository, times(1)).findById(anyLong());
//
//        verify(bookFileRepository).saveAll(any());
//        verify(bookCategoryRepository).saveAll(any());
//    }
//
//    @Test
//    void givenInvalidBook_whenUpdateBook_thenThrowNotFoundException() {
//        BookAdminUpdateRequestDto bookAdminUpdateRequestDto = mock(BookAdminUpdateRequestDto.class);
//
//        when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());
//
//        MockMultipartFile thumbnail = new MockMultipartFile("image", "thumbnail",
//            MediaType.IMAGE_PNG_VALUE, "thumbnail".getBytes());
//
//        assertThrows(NotFoundException.class,
//            () -> bookService.updateBookByAdmin(1L, thumbnail, bookAdminUpdateRequestDto));
//        verify(bookRepository, times(1)).findById(anyLong());
//    }
//
//    @Test
//    void givenInvalidPublisher_whenUpdateBook_thenThrowNotFoundException() {
//        BookAdminUpdateRequestDto bookAdminUpdateRequestDto = mock(BookAdminUpdateRequestDto.class);
//        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(mock(Book.class)));
//        when(publisherRepository.findById(anyLong())).thenReturn(Optional.empty());
//
//        MockMultipartFile thumbnail = new MockMultipartFile("image", "thumbnail",
//            MediaType.IMAGE_PNG_VALUE, "thumbnail".getBytes());
//
//        assertThrows(NotFoundException.class,
//            () -> bookService.updateBookByAdmin(1L, thumbnail, bookAdminUpdateRequestDto));
//        verify(bookRepository, times(1)).findById(anyLong());
//        verify(publisherRepository, times(1)).findById(anyLong());
//    }
//
//    @Test
//    void givenInvalidBookStatus_whenUpdateBook_thenThrowNotFoundException() {
//        BookAdminUpdateRequestDto bookAdminUpdateRequestDto = mock(BookAdminUpdateRequestDto.class);
//
//        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(mock(Book.class)));
//        when(publisherRepository.findById(anyLong())).thenReturn(
//            Optional.of(mock(Publisher.class)));
//        when(bookStatusRepository.findById(anyLong())).thenReturn(Optional.empty());
//
//        MockMultipartFile thumbnail = new MockMultipartFile("image", "thumbnail",
//            MediaType.IMAGE_PNG_VALUE, "thumbnail".getBytes());
//
//        assertThrows(NotFoundException.class,
//            () -> bookService.updateBookByAdmin(1L, thumbnail, bookAdminUpdateRequestDto));
//        verify(bookRepository, times(1)).findById(anyLong());
//        verify(publisherRepository, times(1)).findById(anyLong());
//        verify(bookStatusRepository, times(1)).findById(anyLong());
//    }
//}