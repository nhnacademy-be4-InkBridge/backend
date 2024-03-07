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
import static org.mockito.Mockito.doNothing;

import com.nhnacademy.inkbridge.backend.dto.book.BookAdminCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.book.BookAdminDetailReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.book.BookAdminReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.book.BookAdminSelectedReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.book.BookAdminUpdateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.book.BookReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.book.BooksAdminReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.book.BooksReadResponseDto;
import com.nhnacademy.inkbridge.backend.entity.Author;
import com.nhnacademy.inkbridge.backend.entity.Book;
import com.nhnacademy.inkbridge.backend.entity.BookAuthor;
import com.nhnacademy.inkbridge.backend.entity.BookStatus;
import com.nhnacademy.inkbridge.backend.entity.Category;
import com.nhnacademy.inkbridge.backend.entity.File;
import com.nhnacademy.inkbridge.backend.entity.Publisher;
import com.nhnacademy.inkbridge.backend.entity.Tag;
import com.nhnacademy.inkbridge.backend.exception.NotFoundException;
import com.nhnacademy.inkbridge.backend.repository.AuthorRepository;
import com.nhnacademy.inkbridge.backend.repository.BookAuthorRepository;
import com.nhnacademy.inkbridge.backend.repository.BookCategoryRepository;
import com.nhnacademy.inkbridge.backend.repository.BookFileRepository;
import com.nhnacademy.inkbridge.backend.repository.BookRepository;
import com.nhnacademy.inkbridge.backend.repository.BookStatusRepository;
import com.nhnacademy.inkbridge.backend.repository.BookTagRepository;
import com.nhnacademy.inkbridge.backend.repository.CategoryRepository;
import com.nhnacademy.inkbridge.backend.repository.FileRepository;
import com.nhnacademy.inkbridge.backend.repository.PublisherRepository;
import com.nhnacademy.inkbridge.backend.repository.TagRepository;
import com.nhnacademy.inkbridge.backend.service.FileService;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

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
    PublisherRepository publisherRepository;
    @Mock
    AuthorRepository authorRepository;
    @Mock
    TagRepository tagRepository;
    @Mock
    BookFileRepository bookFileRepository;
    @Mock
    CategoryRepository categoryRepository;
    @Mock
    FileRepository fileRepository;
    @Mock
    BookCategoryRepository bookCategoryRepository;
    @Mock
    BookAuthorRepository bookAuthorRepository;
    @Mock
    BookTagRepository bookTagRepository;
    @Mock
    FileService fileService;
    @Mock
    Pageable pageable;


    @Test
    void whenReadBooks_thenReturnPageDtoList() {
        BooksReadResponseDto booksReadResponseDto = mock(BooksReadResponseDto.class);
        Page<BooksReadResponseDto> resultPage = new PageImpl<>(List.of(booksReadResponseDto),
            pageable, 0);

        when(bookRepository.findAllBooks(any(Pageable.class))).thenReturn(resultPage);

        Page<BooksReadResponseDto> page = bookService.readBooks(pageable);

        assertEquals(1, page.getSize());
        verify(bookRepository, times(1)).findAllBooks(pageable);
    }

    @Test
    void whenReadBook_thenReturnDto() {
        BookReadResponseDto bookReadResponseDto = mock(BookReadResponseDto.class);

        when(bookRepository.existsById(anyLong())).thenReturn(true);
        when(bookRepository.findByBookId(anyLong(), anyLong())).thenReturn(
            Optional.ofNullable(bookReadResponseDto));

        BookReadResponseDto book = bookService.readBook(1L, 1L);

        assertNotNull(book);
        verify(bookRepository, times(1)).existsById(anyLong());
        verify(bookRepository, times(1)).findByBookId(anyLong(), anyLong());
    }

    @Test
    void givenInvalidParameter_whenReadBook_thenThrowNotFoundException() {
        when(bookRepository.existsById(anyLong())).thenReturn(false);

        assertThrows(NotFoundException.class, () -> bookService.readBook(1L, 1L));
        verify(bookRepository, times(1)).existsById(anyLong());
    }

    @Test
    void whenReadBooksByAdmin_thenReturnPageDtoList() {
        BooksAdminReadResponseDto booksAdminReadResponseDto = mock(BooksAdminReadResponseDto.class);
        Page<BooksAdminReadResponseDto> resultPage = new PageImpl<>(
            List.of(booksAdminReadResponseDto));

        when(bookRepository.findAllBooksByAdmin(any(Pageable.class))).thenReturn(resultPage);

        Page<BooksAdminReadResponseDto> page = bookService.readBooksByAdmin(
            pageable);

        assertEquals(1, page.getSize());
        verify(bookRepository, times(1)).findAllBooksByAdmin(pageable);
    }

    @Test
    @DisplayName("도서 상세")
    void whenReadBookByAdmin_thenReturnDto() {
        BookAdminSelectedReadResponseDto bookAdminSelectedReadResponseDto = mock(
            BookAdminSelectedReadResponseDto.class);

        when(bookRepository.existsById(anyLong())).thenReturn(true);
        when(bookRepository.findBookByAdminByBookId(anyLong())).thenReturn(
            Optional.of(bookAdminSelectedReadResponseDto));
        when(categoryRepository.findAllByCategoryParentIsNull()).thenReturn(
            List.of(mock(Category.class)));
        when(publisherRepository.findAll()).thenReturn(List.of(mock(Publisher.class)));
        when(authorRepository.findAll()).thenReturn(List.of(mock(Author.class)));
        when(bookStatusRepository.findAll()).thenReturn(List.of(mock(BookStatus.class)));
        when(tagRepository.findAll()).thenReturn(List.of(mock(Tag.class)));

        BookAdminDetailReadResponseDto bookAdminDetailReadResponseDto = bookService.readBookByAdmin(
            1L);

        assertNotNull(bookAdminDetailReadResponseDto);
        verify(bookRepository, times(1)).existsById(anyLong());
        verify(categoryRepository, times(1)).findAllByCategoryParentIsNull();
        verify(publisherRepository, times(1)).findAll();
        verify(authorRepository, times(1)).findAll();
        verify(bookStatusRepository, times(1)).findAll();
        verify(tagRepository, times(1)).findAll();
        verify(bookRepository, times(1)).findBookByAdminByBookId(anyLong());
    }

    @Test
    void givenInvalidParameter_whenReadBookByAdmin_thenThrowNotFoundException() {
        when(bookRepository.existsById(anyLong())).thenReturn(false);

        assertThrows(NotFoundException.class, () -> bookService.readBookByAdmin(1L));
        verify(bookRepository, times(1)).existsById(anyLong());
    }

    @Test
    void givenNotFound_whenReadBookByAdmin_thenThrowNotFoundException() {
        when(bookRepository.existsById(anyLong())).thenReturn(true);
        when(bookRepository.findBookByAdminByBookId(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> bookService.readBookByAdmin(1L));
        verify(bookRepository, times(1)).existsById(anyLong());
        verify(bookRepository, times(1)).findBookByAdminByBookId(anyLong());
    }

    @Test
    @DisplayName("도서 등록 폼")
    void whenReadBookByAdmin_thenReturnFormDto() {
        when(categoryRepository.findAllByCategoryParentIsNull()).thenReturn(
            List.of(mock(Category.class)));
        when(publisherRepository.findAll()).thenReturn(List.of(mock(Publisher.class)));
        when(authorRepository.findAll()).thenReturn(List.of(mock(Author.class)));
        when(bookStatusRepository.findAll()).thenReturn(List.of(mock(BookStatus.class)));
        when(tagRepository.findAll()).thenReturn(List.of(mock(Tag.class)));

        BookAdminReadResponseDto bookAdminReadResponseDto = bookService.readBookByAdmin();

        assertNotNull(bookAdminReadResponseDto);
        verify(categoryRepository, times(1)).findAllByCategoryParentIsNull();
        verify(publisherRepository, times(1)).findAll();
        verify(authorRepository, times(1)).findAll();
        verify(bookStatusRepository, times(1)).findAll();
        verify(tagRepository, times(1)).findAll();
    }

    @Test
    void whenCreateBook() {
        Book book = Book.builder().build();

        BookAdminCreateRequestDto bookAdminCreateRequestDto = mock(BookAdminCreateRequestDto.class);

        when(bookAdminCreateRequestDto.getCategories()).thenReturn(Set.of(1L));
        when(bookAdminCreateRequestDto.getTags()).thenReturn(List.of(1L));
        when(bookAdminCreateRequestDto.getFileIdList()).thenReturn(List.of(1L));
        when(bookAdminCreateRequestDto.getAuthorId()).thenReturn(1L);
        when(bookAdminCreateRequestDto.getPublisherId()).thenReturn(1L);

        when(bookRepository.save(any(Book.class))).thenReturn(book);
        when(categoryRepository.findById(anyLong())).thenReturn(
            Optional.of(mock(Category.class)));
        when(bookCategoryRepository.saveAll(any())).thenReturn(Collections.emptyList());
        when(tagRepository.findById(anyLong())).thenReturn(
            Optional.of(mock(Tag.class)));
        when(bookTagRepository.saveAll(any())).thenReturn(Collections.emptyList());
        when(authorRepository.findById(anyLong())).thenReturn(Optional.of(mock(Author.class)));
        when(bookAuthorRepository.save(any())).thenReturn(null);
        when(publisherRepository.findById(anyLong())).thenReturn(
            Optional.of(mock(Publisher.class)));
        when(fileRepository.findById(anyLong())).thenReturn(Optional.of(mock(File.class)));
        when(bookFileRepository.saveAll(any())).thenReturn(Collections.emptyList());

        MockMultipartFile thumbnail = new MockMultipartFile("thumbnail", "thumbnail",
            MediaType.IMAGE_PNG_VALUE, "thumbnail".getBytes());

        bookService.createBook(thumbnail, bookAdminCreateRequestDto);

        verify(bookRepository, times(1)).save(any(Book.class));
        verify(categoryRepository, times(1)).findById(anyLong());
        verify(tagRepository, times(1)).findById(anyLong());
        verify(fileRepository, times(1)).findById(anyLong());
        verify(publisherRepository, times(1)).findById(anyLong());
        verify(authorRepository, times(1)).findById(anyLong());

        verify(bookFileRepository, times(1)).saveAll(any());
        verify(bookTagRepository, times(1)).saveAll(any());
        verify(bookAuthorRepository, times(1)).save(any());
        verify(bookCategoryRepository, times(1)).saveAll(any());
    }

    @Test
    void givenInvalidPublisher_whenCreateBook_thenThrowNotFoundException() {
        BookAdminCreateRequestDto bookAdminCreateRequestDto = mock(BookAdminCreateRequestDto.class);

        when(publisherRepository.findById(anyLong())).thenReturn(Optional.empty());

        MockMultipartFile thumbnail = new MockMultipartFile("thumbnail", "thumbnail",
            MediaType.IMAGE_PNG_VALUE, "thumbnail".getBytes());

        assertThrows(NotFoundException.class,
            () -> bookService.createBook(thumbnail, bookAdminCreateRequestDto));

        verify(publisherRepository, times(1)).findById(anyLong());
    }

    @Test
    void givenInvalidAuthor_whenCreateBook_thenThrowNotFoundException() {
        BookAdminCreateRequestDto bookAdminCreateRequestDto = mock(BookAdminCreateRequestDto.class);
        when(bookRepository.save(any())).thenReturn(mock(Book.class));
        when(publisherRepository.findById(anyLong())).thenReturn(
            Optional.of(mock(Publisher.class)));
        when(authorRepository.findById(anyLong())).thenReturn(Optional.empty());

        MockMultipartFile thumbnail = new MockMultipartFile("thumbnail", "thumbnail",
            MediaType.IMAGE_PNG_VALUE, "thumbnail".getBytes());

        assertThrows(NotFoundException.class,
            () -> bookService.createBook(thumbnail, bookAdminCreateRequestDto));

        verify(bookRepository, times(1)).save(any());
        verify(publisherRepository, times(1)).findById(anyLong());
        verify(authorRepository, times(1)).findById(anyLong());
    }

    @Test
    void givenInvalidCategory_whenCreateBook_thenThrowNotFoundException() {
        BookAdminCreateRequestDto bookAdminCreateRequestDto = mock(BookAdminCreateRequestDto.class);
        Book book = Book.builder().build();

        when(bookAdminCreateRequestDto.getCategories()).thenReturn(Set.of(1L));
        when(bookAdminCreateRequestDto.getPublisherId()).thenReturn(1L);

        when(bookRepository.save(any(Book.class))).thenReturn(book);
        when(categoryRepository.findById(anyLong())).thenReturn(
            Optional.empty());
        when(publisherRepository.findById(anyLong())).thenReturn(
            Optional.of(mock(Publisher.class)));

        MockMultipartFile thumbnail = new MockMultipartFile("thumbnail", "thumbnail",
            MediaType.IMAGE_PNG_VALUE, "thumbnail".getBytes());

        assertThrows(NotFoundException.class,
            () -> bookService.createBook(thumbnail, bookAdminCreateRequestDto));

        verify(bookRepository, times(1)).save(any(Book.class));
        verify(categoryRepository, times(1)).findById(anyLong());
        verify(publisherRepository, times(1)).findById(anyLong());
    }

    @Test
    void givenInvalidTag_whenCreateBook_thenThrowNotFoundException() {
        BookAdminCreateRequestDto bookAdminCreateRequestDto = mock(BookAdminCreateRequestDto.class);
        Book book = Book.builder().build();

        when(bookAdminCreateRequestDto.getCategories()).thenReturn(Set.of(1L));
        when(bookAdminCreateRequestDto.getTags()).thenReturn(List.of(1L));
        when(bookAdminCreateRequestDto.getPublisherId()).thenReturn(1L);

        when(bookRepository.save(any(Book.class))).thenReturn(book);
        when(categoryRepository.findById(anyLong())).thenReturn(
            Optional.of(mock(Category.class)));
        when(bookCategoryRepository.saveAll(any())).thenReturn(Collections.emptyList());
        when(tagRepository.findById(anyLong())).thenReturn(
            Optional.empty());
        when(publisherRepository.findById(anyLong())).thenReturn(
            Optional.of(mock(Publisher.class)));

        MockMultipartFile thumbnail = new MockMultipartFile("thumbnail", "thumbnail",
            MediaType.IMAGE_PNG_VALUE, "thumbnail".getBytes());

        assertThrows(NotFoundException.class,
            () -> bookService.createBook(thumbnail, bookAdminCreateRequestDto));

        verify(bookRepository, times(1)).save(any(Book.class));
        verify(categoryRepository, times(1)).findById(anyLong());
        verify(tagRepository, times(1)).findById(anyLong());
        verify(publisherRepository, times(1)).findById(anyLong());

        verify(bookCategoryRepository, times(1)).saveAll(any());
    }

    @Test
    void whenUpdateBookByAdmin() {
        BookAdminUpdateRequestDto bookAdminUpdateRequestDto = mock(BookAdminUpdateRequestDto.class);

        when(bookAdminUpdateRequestDto.getCategories()).thenReturn(Set.of(1L));
        when(bookAdminUpdateRequestDto.getTags()).thenReturn(List.of(1L));
        when(bookAdminUpdateRequestDto.getFileIdList()).thenReturn(List.of(1L));
        when(bookAdminUpdateRequestDto.getAuthorId()).thenReturn(1L);
        when(bookAdminUpdateRequestDto.getPublisherId()).thenReturn(1L);

        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(mock(Book.class)));
        when(publisherRepository.findById(anyLong())).thenReturn(
            Optional.of(mock(Publisher.class)));
        when(bookStatusRepository.findById(anyLong())).thenReturn(
            Optional.of(mock(BookStatus.class)));

        when(authorRepository.findById(anyLong())).thenReturn(
            Optional.of(mock(Author.class)));
        when(bookAuthorRepository.findByPk_BookId(anyLong())).thenReturn(mock(BookAuthor.class));

        when(fileRepository.findById(anyLong())).thenReturn(Optional.of(mock(File.class)));
        doNothing().when(bookFileRepository).deleteAllByBook_BookId(anyLong());
        when(bookFileRepository.saveAll(any())).thenReturn(Collections.emptyList());

        when(categoryRepository.findById(anyLong())).thenReturn(
            Optional.of(mock(Category.class)));
        doNothing().when(bookCategoryRepository).deleteByPk_BookId(anyLong());
        when(bookCategoryRepository.saveAll(any())).thenReturn(Collections.emptyList());

        when(tagRepository.findById(anyLong())).thenReturn(Optional.of(mock(Tag.class)));
        doNothing().when(bookTagRepository).deleteAllByPk_BookId(anyLong());
        when(bookTagRepository.saveAll(any())).thenReturn(Collections.emptyList());

        when(fileService.saveThumbnail(any(MultipartFile.class))).thenReturn(mock(File.class));

        MockMultipartFile thumbnail = new MockMultipartFile("thumbnail", "thumbnail",
            MediaType.IMAGE_PNG_VALUE, "thumbnail".getBytes());

        bookService.updateBookByAdmin(1L, thumbnail, bookAdminUpdateRequestDto);

        verify(authorRepository, times(1)).findById(anyLong());

        verify(publisherRepository, times(1)).findById(anyLong());

        verify(bookFileRepository).saveAll(any());
        verify(bookCategoryRepository).saveAll(any());
    }

    @Test
    void givenInvalidBook_whenUpdateBook_thenThrowNotFoundException() {
        BookAdminUpdateRequestDto bookAdminUpdateRequestDto = mock(BookAdminUpdateRequestDto.class);

        when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());

        MockMultipartFile thumbnail = new MockMultipartFile("image", "thumbnail",
            MediaType.IMAGE_PNG_VALUE, "thumbnail".getBytes());

        assertThrows(NotFoundException.class,
            () -> bookService.updateBookByAdmin(1L, thumbnail, bookAdminUpdateRequestDto));
        verify(bookRepository, times(1)).findById(anyLong());
    }

    @Test
    void givenInvalidPublisher_whenUpdateBook_thenThrowNotFoundException() {
        BookAdminUpdateRequestDto bookAdminUpdateRequestDto = mock(BookAdminUpdateRequestDto.class);
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(mock(Book.class)));
        when(publisherRepository.findById(anyLong())).thenReturn(Optional.empty());

        MockMultipartFile thumbnail = new MockMultipartFile("image", "thumbnail",
            MediaType.IMAGE_PNG_VALUE, "thumbnail".getBytes());

        assertThrows(NotFoundException.class,
            () -> bookService.updateBookByAdmin(1L, thumbnail, bookAdminUpdateRequestDto));
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

        MockMultipartFile thumbnail = new MockMultipartFile("image", "thumbnail",
            MediaType.IMAGE_PNG_VALUE, "thumbnail".getBytes());

        assertThrows(NotFoundException.class,
            () -> bookService.updateBookByAdmin(1L, thumbnail, bookAdminUpdateRequestDto));
        verify(bookRepository, times(1)).findById(anyLong());
        verify(publisherRepository, times(1)).findById(anyLong());
        verify(bookStatusRepository, times(1)).findById(anyLong());
    }

    @Test
    void givenInvalidBookFile_whenUpdateBook_thenThrowNotFoundException() {
        BookAdminUpdateRequestDto bookAdminUpdateRequestDto = mock(BookAdminUpdateRequestDto.class);

        when(bookAdminUpdateRequestDto.getCategories()).thenReturn(Set.of(1L));
        when(bookAdminUpdateRequestDto.getTags()).thenReturn(List.of(1L));
        when(bookAdminUpdateRequestDto.getFileIdList()).thenReturn(List.of(1L));
        when(bookAdminUpdateRequestDto.getAuthorId()).thenReturn(1L);
        when(bookAdminUpdateRequestDto.getPublisherId()).thenReturn(1L);

        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(mock(Book.class)));
        when(publisherRepository.findById(anyLong())).thenReturn(
            Optional.of(mock(Publisher.class)));
        when(bookStatusRepository.findById(anyLong())).thenReturn(
            Optional.of(mock(BookStatus.class)));

        when(authorRepository.findById(anyLong())).thenReturn(
            Optional.of(mock(Author.class)));
        when(bookAuthorRepository.findByPk_BookId(anyLong())).thenReturn(mock(BookAuthor.class));

        when(fileRepository.findById(anyLong())).thenReturn(Optional.empty());

        when(categoryRepository.findById(anyLong())).thenReturn(
            Optional.of(mock(Category.class)));
        doNothing().when(bookCategoryRepository).deleteByPk_BookId(anyLong());
        when(bookCategoryRepository.saveAll(any())).thenReturn(Collections.emptyList());

        when(tagRepository.findById(anyLong())).thenReturn(Optional.of(mock(Tag.class)));
        doNothing().when(bookTagRepository).deleteAllByPk_BookId(anyLong());
        when(bookTagRepository.saveAll(any())).thenReturn(Collections.emptyList());

        when(fileService.saveThumbnail(any(MultipartFile.class))).thenReturn(mock(File.class));

        MockMultipartFile thumbnail = new MockMultipartFile("image", "thumbnail",
            MediaType.IMAGE_PNG_VALUE, "thumbnail".getBytes());

        assertThrows(NotFoundException.class,
            () -> bookService.updateBookByAdmin(1L, thumbnail, bookAdminUpdateRequestDto));
        verify(bookRepository, times(1)).findById(anyLong());
        verify(publisherRepository, times(1)).findById(anyLong());
        verify(bookStatusRepository, times(1)).findById(anyLong());
    }

    @Test
    void givenInvalidBookTag_whenUpdateBook_thenThrowNotFoundException() {
        BookAdminUpdateRequestDto bookAdminUpdateRequestDto = mock(BookAdminUpdateRequestDto.class);

        when(bookAdminUpdateRequestDto.getCategories()).thenReturn(Set.of(1L));
        when(bookAdminUpdateRequestDto.getTags()).thenReturn(List.of(1L));
        when(bookAdminUpdateRequestDto.getAuthorId()).thenReturn(1L);
        when(bookAdminUpdateRequestDto.getPublisherId()).thenReturn(1L);

        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(mock(Book.class)));
        when(publisherRepository.findById(anyLong())).thenReturn(
            Optional.of(mock(Publisher.class)));
        when(bookStatusRepository.findById(anyLong())).thenReturn(
            Optional.of(mock(BookStatus.class)));

        when(authorRepository.findById(anyLong())).thenReturn(
            Optional.of(mock(Author.class)));
        when(bookAuthorRepository.findByPk_BookId(anyLong())).thenReturn(mock(BookAuthor.class));

        when(categoryRepository.findById(anyLong())).thenReturn(
            Optional.of(mock(Category.class)));
        doNothing().when(bookCategoryRepository).deleteByPk_BookId(anyLong());
        when(bookCategoryRepository.saveAll(any())).thenReturn(Collections.emptyList());

        when(tagRepository.findById(anyLong())).thenReturn(Optional.empty());

        when(fileService.saveThumbnail(any(MultipartFile.class))).thenReturn(mock(File.class));

        MockMultipartFile thumbnail = new MockMultipartFile("image", "thumbnail",
            MediaType.IMAGE_PNG_VALUE, "thumbnail".getBytes());

        assertThrows(NotFoundException.class,
            () -> bookService.updateBookByAdmin(1L, thumbnail, bookAdminUpdateRequestDto));
        verify(bookRepository, times(1)).findById(anyLong());
        verify(publisherRepository, times(1)).findById(anyLong());
        verify(bookStatusRepository, times(1)).findById(anyLong());
    }

    @Test
    void givenInvalidBookCategory_whenUpdateBook_thenThrowNotFoundException() {
        BookAdminUpdateRequestDto bookAdminUpdateRequestDto = mock(BookAdminUpdateRequestDto.class);

        when(bookAdminUpdateRequestDto.getCategories()).thenReturn(Set.of(1L));
        when(bookAdminUpdateRequestDto.getAuthorId()).thenReturn(1L);
        when(bookAdminUpdateRequestDto.getPublisherId()).thenReturn(1L);

        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(mock(Book.class)));
        when(publisherRepository.findById(anyLong())).thenReturn(
            Optional.of(mock(Publisher.class)));
        when(bookStatusRepository.findById(anyLong())).thenReturn(
            Optional.of(mock(BookStatus.class)));

        when(authorRepository.findById(anyLong())).thenReturn(
            Optional.of(mock(Author.class)));
        when(bookAuthorRepository.findByPk_BookId(anyLong())).thenReturn(mock(BookAuthor.class));

        when(categoryRepository.findById(anyLong())).thenReturn(
            Optional.empty());

        when(fileService.saveThumbnail(any(MultipartFile.class))).thenReturn(mock(File.class));

        MockMultipartFile thumbnail = new MockMultipartFile("image", "thumbnail",
            MediaType.IMAGE_PNG_VALUE, "thumbnail".getBytes());

        assertThrows(NotFoundException.class,
            () -> bookService.updateBookByAdmin(1L, thumbnail, bookAdminUpdateRequestDto));
        verify(bookRepository, times(1)).findById(anyLong());
        verify(publisherRepository, times(1)).findById(anyLong());
        verify(bookStatusRepository, times(1)).findById(anyLong());
    }

    @Test
    void givenInvalidBookAuthor_whenUpdateBook_thenThrowNotFoundException() {
        BookAdminUpdateRequestDto bookAdminUpdateRequestDto = mock(BookAdminUpdateRequestDto.class);

        when(bookAdminUpdateRequestDto.getAuthorId()).thenReturn(1L);
        when(bookAdminUpdateRequestDto.getPublisherId()).thenReturn(1L);

        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(mock(Book.class)));
        when(publisherRepository.findById(anyLong())).thenReturn(
            Optional.of(mock(Publisher.class)));
        when(bookStatusRepository.findById(anyLong())).thenReturn(
            Optional.of(mock(BookStatus.class)));

        when(authorRepository.findById(anyLong())).thenReturn(
            Optional.empty());

        when(fileService.saveThumbnail(any(MultipartFile.class))).thenReturn(mock(File.class));

        MockMultipartFile thumbnail = new MockMultipartFile("image", "thumbnail",
            MediaType.IMAGE_PNG_VALUE, "thumbnail".getBytes());

        assertThrows(NotFoundException.class,
            () -> bookService.updateBookByAdmin(1L, thumbnail, bookAdminUpdateRequestDto));
        verify(bookRepository, times(1)).findById(anyLong());
        verify(publisherRepository, times(1)).findById(anyLong());
        verify(bookStatusRepository, times(1)).findById(anyLong());
    }
}