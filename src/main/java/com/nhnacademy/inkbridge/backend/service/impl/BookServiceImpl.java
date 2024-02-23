package com.nhnacademy.inkbridge.backend.service.impl;

import com.nhnacademy.inkbridge.backend.dto.book.BookAdminCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.book.BookAdminReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.book.BookAdminUpdateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.book.BookAdminUpdateResponseDto;
import com.nhnacademy.inkbridge.backend.dto.book.BookReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.book.BooksAdminReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.book.BooksReadResponseDto;
import com.nhnacademy.inkbridge.backend.entity.Author;
import com.nhnacademy.inkbridge.backend.entity.Book;
import com.nhnacademy.inkbridge.backend.entity.BookAuthor;
import com.nhnacademy.inkbridge.backend.entity.BookCategory;
import com.nhnacademy.inkbridge.backend.entity.BookCategory.Pk;
import com.nhnacademy.inkbridge.backend.entity.BookStatus;
import com.nhnacademy.inkbridge.backend.entity.BookTag;
import com.nhnacademy.inkbridge.backend.entity.Category;
import com.nhnacademy.inkbridge.backend.entity.File;
import com.nhnacademy.inkbridge.backend.entity.Publisher;
import com.nhnacademy.inkbridge.backend.entity.Tag;
import com.nhnacademy.inkbridge.backend.enums.BookMessageEnum;
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
import com.nhnacademy.inkbridge.backend.service.BookService;
import com.nhnacademy.inkbridge.backend.service.FileService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 * class: BookServiceImpl.
 *
 * @author minm063
 * @version 2024/02/14
 */
@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookStatusRepository bookStatusRepository;
    private final FileRepository fileRepository;
    private final PublisherRepository publisherRepository;
    private final CategoryRepository categoryRepository;
    private final TagRepository tagRepository;
    private final AuthorRepository authorRepository;
    private final BookTagRepository bookTagRepository;
    private final BookCategoryRepository bookCategoryRepository;
    private final BookAuthorRepository bookAuthorRepository;
    private final BookFileRepository bookFileRepository;
    private final FileService fileService;


    public BookServiceImpl(BookRepository bookRepository, BookStatusRepository bookStatusRepository,
        FileRepository fileRepository, PublisherRepository publisherRepository,
        CategoryRepository categoryRepository, TagRepository tagRepository,
        AuthorRepository authorRepository, BookTagRepository bookTagRepository,
        BookCategoryRepository bookCategoryRepository,
        BookAuthorRepository bookAuthorRepository, BookFileRepository bookFileRepository,
        FileService fileService) {
        this.bookRepository = bookRepository;
        this.bookStatusRepository = bookStatusRepository;
        this.fileRepository = fileRepository;
        this.publisherRepository = publisherRepository;
        this.categoryRepository = categoryRepository;
        this.tagRepository = tagRepository;
        this.authorRepository = authorRepository;
        this.bookTagRepository = bookTagRepository;
        this.bookCategoryRepository = bookCategoryRepository;
        this.bookAuthorRepository = bookAuthorRepository;
        this.bookFileRepository = bookFileRepository;
        this.fileService = fileService;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public Page<BooksReadResponseDto> readBooks(Pageable pageable) {
        return bookRepository.findAllBooks(pageable);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public BookReadResponseDto readBook(Long bookId) {
        if (!bookRepository.existsById(bookId)) {
            throw new NotFoundException(BookMessageEnum.BOOK_NOT_FOUND.getMessage());
        }

        return bookRepository.findByBookId(bookId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public Page<BooksAdminReadResponseDto> readBooksByAdmin(Pageable pageable) {
        return bookRepository.findAllBooksByAdmin(pageable);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public BookAdminReadResponseDto readBookByAdmin(Long bookId) {
        if (!bookRepository.existsById(bookId)) {
            throw new NotFoundException(BookMessageEnum.BOOK_NOT_FOUND.getMessage());
        }

        return bookRepository.findBookByAdminByBookId(bookId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void createBook(BookAdminCreateRequestDto bookAdminCreateRequestDto) {
        BookStatus bookStatus = bookStatusRepository.findById(
                bookAdminCreateRequestDto.getStatusId())
            .orElseThrow(() -> new NotFoundException(BookMessageEnum.BOOK_NOT_FOUND.getMessage()));
        Publisher publisher = publisherRepository.findById(
                bookAdminCreateRequestDto.getPublisherId())
            .orElseThrow(
                () -> new NotFoundException(BookMessageEnum.BOOK_PUBLISHER_NOT_FOUND.getMessage()));

        MultipartFile thumbnail = bookAdminCreateRequestDto.getThumbnail();
        File savedThumbnail = fileService.saveFile(thumbnail);

        Book book = Book.builder()
            .bookTitle(bookAdminCreateRequestDto.getBookTitle())
            .bookIndex(bookAdminCreateRequestDto.getBookIndex())
            .description(bookAdminCreateRequestDto.getDescription())
            .publicatedAt(bookAdminCreateRequestDto.getPublicatedAt())
            .isbn(bookAdminCreateRequestDto.getIsbn())
            .regularPrice(bookAdminCreateRequestDto.getRegularPrice())
            .price(bookAdminCreateRequestDto.getPrice())
            .discountRatio(bookAdminCreateRequestDto.getDiscountRatio())
            .stock(bookAdminCreateRequestDto.getStock())
            .isPackagable(bookAdminCreateRequestDto.getIsPackagable()).bookStatus(bookStatus)
            .publisher(publisher)
            .thumbnailFile(savedThumbnail)
            .build();

        Book saved = bookRepository.save(book);

        List<BookCategory> bookCategoryList = new ArrayList<>();
        for (Long categoryId : bookAdminCreateRequestDto.getCategoryIdList()) {
            Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException(""));
            BookCategory bookCategory = BookCategory.create()
                .pk(Pk.builder().categoryId(categoryId).bookId(saved.getBookId()).build())
                .category(category)
                .book(saved)
                .build();
            bookCategoryList.add(bookCategory);
        }
        bookCategoryRepository.saveAll(bookCategoryList);

        List<BookTag> bookTagList = new ArrayList<>();
        for (Long tagId : bookAdminCreateRequestDto.getTagIdList()) {
            Tag tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new NotFoundException(""));
            BookTag bookTag = BookTag.builder()
                .pk(BookTag.Pk.builder().bookId(saved.getBookId()).tagId(tagId).build())
                .book(saved)
                .tag(tag)
                .build();
            bookTagList.add(bookTag);
        }
        bookTagRepository.saveAll(bookTagList);

        List<BookAuthor> bookAuthorList = new ArrayList<>();
        for (Long authorId : bookAdminCreateRequestDto.getAuthorIdList()) {
            Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new NotFoundException(""));
            BookAuthor bookAuthor = BookAuthor.builder()
                .pk(BookAuthor.Pk.builder().bookId(saved.getBookId()).authorId(authorId).build())
                .author(author)
                .book(saved)
                .build();
            bookAuthorList.add(bookAuthor);
        }
        bookAuthorRepository.saveAll(bookAuthorList);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public BookAdminUpdateResponseDto updateBookByAdmin(Long bookId,
        BookAdminUpdateRequestDto bookAdminUpdateRequestDto) {
        Book book = bookRepository.findById(bookId)
            .orElseThrow(() -> new NotFoundException(BookMessageEnum.BOOK_NOT_FOUND.getMessage()));
        Publisher publisher = publisherRepository.findById(
                bookAdminUpdateRequestDto.getPublisherId())
            .orElseThrow(
                () -> new NotFoundException(BookMessageEnum.BOOK_PUBLISHER_NOT_FOUND.getMessage()));
        BookStatus bookStatus = bookStatusRepository.findById(
                bookAdminUpdateRequestDto.getStatusId())
            .orElseThrow(
                () -> new NotFoundException(BookMessageEnum.BOOK_STATUS_NOT_FOUND.getMessage()));
        File thumbnail = fileRepository.findById(bookAdminUpdateRequestDto.getThumbnailId())
            .orElseThrow(
                () -> new NotFoundException(BookMessageEnum.BOOK_THUMBNAIL_NOT_FOUND.getMessage()));

        book.updateBook(bookAdminUpdateRequestDto.getBookTitle(),
            bookAdminUpdateRequestDto.getBookIndex(), bookAdminUpdateRequestDto.getDescription(),
            bookAdminUpdateRequestDto.getPublicatedAt(), bookAdminUpdateRequestDto.getIsbn(),
            bookAdminUpdateRequestDto.getRegularPrice(), bookAdminUpdateRequestDto.getPrice(),
            bookAdminUpdateRequestDto.getDiscountRatio(), bookAdminUpdateRequestDto.getStock(),
            bookAdminUpdateRequestDto.getIsPackagable(), bookStatus, publisher, thumbnail);

        return BookAdminUpdateResponseDto.builder().bookId(bookId).build();
    }
}
