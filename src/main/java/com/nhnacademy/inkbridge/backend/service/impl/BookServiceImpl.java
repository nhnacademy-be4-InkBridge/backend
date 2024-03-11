package com.nhnacademy.inkbridge.backend.service.impl;

import com.nhnacademy.inkbridge.backend.dto.book.AuthorReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.book.BookAdminCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.book.BookAdminDetailReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.book.BookAdminReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.book.BookAdminSelectedReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.book.BookAdminUpdateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.book.BookReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.book.BooksAdminReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.book.BooksReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.book.PublisherReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.bookstatus.BookStatusReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.category.ParentCategoryReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.tag.TagReadResponseDto;
import com.nhnacademy.inkbridge.backend.entity.Author;
import com.nhnacademy.inkbridge.backend.entity.Book;
import com.nhnacademy.inkbridge.backend.entity.BookAuthor;
import com.nhnacademy.inkbridge.backend.entity.BookCategory;
import com.nhnacademy.inkbridge.backend.entity.BookCategory.Pk;
import com.nhnacademy.inkbridge.backend.entity.BookFile;
import com.nhnacademy.inkbridge.backend.entity.BookStatus;
import com.nhnacademy.inkbridge.backend.entity.BookTag;
import com.nhnacademy.inkbridge.backend.entity.Category;
import com.nhnacademy.inkbridge.backend.entity.File;
import com.nhnacademy.inkbridge.backend.entity.Publisher;
import com.nhnacademy.inkbridge.backend.entity.Tag;
import com.nhnacademy.inkbridge.backend.enums.BookMessageEnum;
import com.nhnacademy.inkbridge.backend.enums.FileMessageEnum;
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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
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
    public Page<BooksReadResponseDto> readBooksByCategory(Long categoryId, Pageable pageable) {
        return bookRepository.findAllBooksByCategory(pageable, categoryId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public BookReadResponseDto readBook(Long bookId, Long memberId) {
        if (!bookRepository.existsById(bookId)) {
            throw new NotFoundException(BookMessageEnum.BOOK_NOT_FOUND.getMessage());
        }

        return bookRepository.findByBookId(bookId, memberId)
            .orElseThrow(() -> new NotFoundException(BookMessageEnum.BOOK_NOT_FOUND.getMessage()));
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
    @Transactional
    public BookAdminDetailReadResponseDto readBookByAdmin(Long bookId) {
        if (!bookRepository.existsById(bookId)) {
            throw new NotFoundException(BookMessageEnum.BOOK_NOT_FOUND.getMessage());
        }

        BookAdminSelectedReadResponseDto bookAdminSelectedReadResponseDto =
            bookRepository.findBookByAdminByBookId(bookId)
                .orElseThrow(
                    () -> new NotFoundException(BookMessageEnum.BOOK_NOT_FOUND.getMessage()));
        List<ParentCategoryReadResponseDto> parentCategoryReadResponseDtoList = readAllCategory();
        List<PublisherReadResponseDto> publisherReadResponseDtoList = getPublisherList();
        List<AuthorReadResponseDto> authorReadResponseDtoList = getAuthorList();
        List<BookStatusReadResponseDto> bookStatusReadResponseDtoList = getStatuses();
        List<TagReadResponseDto> tagReadResponseDtoList = getTagList();

        return BookAdminDetailReadResponseDto.builder()
            .adminSelectedReadResponseDto(bookAdminSelectedReadResponseDto)
            .parentCategoryReadResponseDtoList(parentCategoryReadResponseDtoList)
            .publisherReadResponseDtoList(publisherReadResponseDtoList)
            .authorReadResponseDtoList(authorReadResponseDtoList)
            .bookStatusReadResponseDtoList(bookStatusReadResponseDtoList)
            .tagReadResponseDtoList(tagReadResponseDtoList)
            .build();
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public BookAdminReadResponseDto readBookByAdmin() {
        List<ParentCategoryReadResponseDto> parentCategoryReadResponseDtoList = readAllCategory();
        List<PublisherReadResponseDto> publisherReadResponseDtoList = getPublisherList();
        List<AuthorReadResponseDto> authorReadResponseDtoList = getAuthorList();
        List<BookStatusReadResponseDto> bookStatusReadResponseDtoList = getStatuses();
        List<TagReadResponseDto> tagReadResponseDtoList = getTagList();

        return BookAdminReadResponseDto.builder()
            .parentCategoryReadResponseDtoList(parentCategoryReadResponseDtoList)
            .publisherReadResponseDtoList(publisherReadResponseDtoList)
            .authorReadResponseDtoList(authorReadResponseDtoList)
            .bookStatusReadResponseDtoList(bookStatusReadResponseDtoList)
            .tagReadResponseDtoList(tagReadResponseDtoList)
            .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void createBook(MultipartFile thumbnail,
        BookAdminCreateRequestDto bookAdminCreateRequestDto) {
        Publisher publisher = publisherRepository.findById(
                bookAdminCreateRequestDto.getPublisherId())
            .orElseThrow(
                () -> new NotFoundException(BookMessageEnum.BOOK_PUBLISHER_NOT_FOUND.getMessage()));

        File savedThumbnail = fileService.saveThumbnail(thumbnail);

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
            .isPackagable(bookAdminCreateRequestDto.getIsPackagable())
            .updatedAt(LocalDateTime.now())
            .bookStatus(BookStatus.builder().statusId(1L).build())
            .publisher(publisher)
            .thumbnailFile(savedThumbnail)
            .build();

        Book saved = bookRepository.save(book);

        saveBookCategory(bookAdminCreateRequestDto.getCategories(), saved);
        saveBookTag(bookAdminCreateRequestDto.getTags(), saved);
        saveBookAuthor(bookAdminCreateRequestDto.getAuthorId(), saved);
        saveBookFile(bookAdminCreateRequestDto.getFileIdList(), saved);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void updateBookByAdmin(Long bookId, MultipartFile thumbnail,
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
        File savedThumbnail =
            (thumbnail != null) ? fileService.saveThumbnail(thumbnail) : book.getThumbnailFile();

        book.updateBook(bookAdminUpdateRequestDto.getBookTitle(),
            bookAdminUpdateRequestDto.getBookIndex(), bookAdminUpdateRequestDto.getDescription(),
            bookAdminUpdateRequestDto.getPublicatedAt(), bookAdminUpdateRequestDto.getIsbn(),
            bookAdminUpdateRequestDto.getRegularPrice(), bookAdminUpdateRequestDto.getPrice(),
            bookAdminUpdateRequestDto.getDiscountRatio(), bookAdminUpdateRequestDto.getStock(),
            bookAdminUpdateRequestDto.getIsPackagable(), bookStatus, publisher, savedThumbnail);

        updateBookAuthor(bookAdminUpdateRequestDto.getAuthorId(), bookId);
        updateBookCategory(bookAdminUpdateRequestDto.getCategories(), book);
        updateBookTag(bookAdminUpdateRequestDto.getTags(), book);
        updateBookFile(bookAdminUpdateRequestDto.getFileIdList(), book);
    }

    /**
     * BookFile 리스트를 저장하는 메서드입니다. File 테이블에 fileId와 일치하는 데이터가 없으면 NotFoundException을 던집니다.
     *
     * @param fileIdList fileId list
     * @param book       Book
     * @throws NotFoundException 주어진 File ID로 찾을 수 없는 경우
     */
    private void saveBookFile(List<Long> fileIdList, Book book) {
        List<File> fileList = fileIdList.stream()
            .map(fileId -> fileRepository.findById(fileId).orElseThrow(() -> new NotFoundException(
                FileMessageEnum.FILE_VALID_FAIL.getMessage()))).collect(Collectors.toList());
        List<BookFile> bookFileList = fileList.stream()
            .map(file -> BookFile.builder().book(book).fileId(file.getFileId()).build()).collect(
                Collectors.toList());
        bookFileRepository.saveAll(bookFileList);
    }

    /**
     * BookAuthor을 저장하는 메서드입니다. Author 테이블에 authorId와 일치하는 데이터가 없으면 NotFoundException을 던집니다.
     *
     * @param authorId Long
     * @param book     Book
     * @throws NotFoundException 주어진 Author ID로 찾을 수 없는 경우
     */
    private void saveBookAuthor(Long authorId, Book book) {
        Author author = authorRepository.findById(authorId)
            .orElseThrow(() -> new NotFoundException(
                BookMessageEnum.BOOK_AUTHOR_NOT_FOUND.getMessage()));
        BookAuthor bookAuthor = BookAuthor.builder()
            .pk(BookAuthor.Pk.builder().bookId(book.getBookId()).authorId(authorId).build())
            .author(author)
            .book(book)
            .build();
        bookAuthorRepository.save(bookAuthor);
    }

    /**
     * BookTag 리스트를 저장하는 메서드입니다. Tag 테이블에 tagId와 일치하는 데이터가 없으면 NotFoundException을 던집니다.
     *
     * @param tags nullable, tagId list
     * @param book Book
     * @throws NotFoundException 주어진 Tag ID로 찾을 수 없는 경우
     */
    private void saveBookTag(List<Long> tags, Book book) {
        if (!tags.isEmpty()) {
            List<BookTag> bookTagList = new ArrayList<>();
            for (Long tagId : tags) {
                Tag tag = tagRepository.findById(tagId)
                    .orElseThrow(() -> new NotFoundException(
                        BookMessageEnum.BOOK_TAG_NOT_FOUND.getMessage()));
                BookTag bookTag = BookTag.builder()
                    .pk(BookTag.Pk.builder().bookId(book.getBookId()).tagId(tagId).build())
                    .book(book)
                    .tag(tag)
                    .build();
                bookTagList.add(bookTag);
            }
            bookTagRepository.saveAll(bookTagList);
        }
    }

    /**
     * BookCategory 리스트를 저장하는 메서드입니다. Category 테이블에 categoryId와 일치하는 데이터가 없으면 NotFoundException을
     * 던집니다.
     *
     * @param categories categoryId set
     * @param book       Book
     * @throws NotFoundException 주어진 Category ID로 찾을 수 없는 경우
     */
    private void saveBookCategory(Set<Long> categories, Book book) {
        List<BookCategory> bookCategoryList = new ArrayList<>();
        for (Long categoryId : categories) {
            Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException(
                    BookMessageEnum.BOOK_CATEGORY_NOT_FOUND.getMessage()));
            BookCategory bookCategory = BookCategory.create()
                .pk(Pk.builder().categoryId(categoryId).bookId(book.getBookId()).build())
                .category(category)
                .book(book)
                .build();
            bookCategoryList.add(bookCategory);
        }
        bookCategoryRepository.saveAll(bookCategoryList);
    }

    /**
     * BookFile 리스트를 수정하는 메서드입니다. File 테이블에 fileId와 일치하는 데이터가 없으면 NotFoundException을 던집니다.
     *
     * @param newFiles fileId list
     * @param book     Book
     * @throws NotFoundException 주어진 File ID로 찾을 수 없는 경우
     */
    private void updateBookFile(List<Long> newFiles, Book book) {
        List<File> fileIdList = newFiles.stream()
            .map(x -> fileRepository.findById(x)
                .orElseThrow(
                    () -> new NotFoundException(
                        BookMessageEnum.BOOK_FILE_NOT_FOUND.getMessage())))
            .collect(
                Collectors.toList());
        bookFileRepository.deleteAllByBook_BookId(book.getBookId());
        List<BookFile> bookFileList = fileIdList.stream()
            .map(file -> BookFile.builder().book(book).fileId(file.getFileId()).build()).collect(
                Collectors.toList());
        bookFileRepository.saveAll(bookFileList);
    }

    /**
     * BookFile 리스트를 수정하는 메서드입니다. File 테이블에 fileId와 일치하는 데이터가 없으면 NotFoundException을 던집니다.
     *
     * @param newTags tagId list
     * @param book    Book
     * @throws NotFoundException 주어진 Tag ID로 찾을 수 없는 경우
     */
    private void updateBookTag(List<Long> newTags, Book book) {
        if (!newTags.isEmpty()) {
            List<Tag> tags = newTags.stream()
                .map(x -> tagRepository.findById(x)
                    .orElseThrow(
                        () -> new NotFoundException(
                            BookMessageEnum.BOOK_TAG_NOT_FOUND.getMessage())))
                .collect(
                    Collectors.toList());
            bookTagRepository.deleteAllByPk_BookId(book.getBookId());
            List<BookTag> bookTagList = new ArrayList<>();
            for (Tag tag : tags) {
                BookTag bookTag = BookTag.builder()
                    .pk(BookTag.Pk.builder().bookId(book.getBookId()).tagId(tag.getTagId()).build())
                    .book(book)
                    .tag(tag)
                    .build();
                bookTagList.add(bookTag);
            }
            bookTagRepository.saveAll(bookTagList);
        }
    }

    /**
     * BookCategory 리스트를 수정하는 메서드입니다. Category 테이블에 categoryId와 일치하는 데이터가 없으면 NotFoundException을
     * 던집니다.
     *
     * @param newCategories categoryId list
     * @param book          Book
     * @throws NotFoundException 주어진 Category ID로 찾을 수 없는 경우
     */
    private void updateBookCategory(Set<Long> newCategories, Book book) {
        Set<Category> categories = newCategories.stream()
            .map(x -> categoryRepository.findById(x)
                .orElseThrow(
                    () -> new NotFoundException(
                        BookMessageEnum.BOOK_CATEGORY_NOT_FOUND.getMessage())))
            .collect(
                Collectors.toSet());
        bookCategoryRepository.deleteByPk_BookId(book.getBookId()); // delete prev
        List<BookCategory> bookCategoryList = new ArrayList<>();
        for (Category category : categories) {
            BookCategory bookCategory = BookCategory.create()
                .pk(Pk.builder().categoryId(category.getCategoryId()).bookId(book.getBookId())
                    .build())
                .category(category)
                .book(book)
                .build();
            bookCategoryList.add(bookCategory);
        }
        bookCategoryRepository.saveAll(bookCategoryList);
    }

    /**
     * BookAuthor를 수정하는 메서드입니다. Author 테이블에 authorId와 일치하는 데이터가 없으면 NotFoundException을 던집니다.
     *
     * @param authorId Long
     * @param bookId   Long
     * @throws NotFoundException 주어진 Author ID로 찾을 수 없는 경우
     */
    private void updateBookAuthor(Long authorId, Long bookId) {
        Author author = authorRepository.findById(authorId)
            .orElseThrow(
                () -> new NotFoundException(BookMessageEnum.BOOK_AUTHOR_NOT_FOUND.getMessage()));
        BookAuthor prevBookAuthor = bookAuthorRepository.findByPk_BookId(bookId);
        prevBookAuthor.updateBookAuthor(author);
    }

    /**
     * 전체 카테고리를 조회하는 메서드입니다.
     *
     * @return ParentCategoryReadResponseDto
     */
    private List<ParentCategoryReadResponseDto> readAllCategory() {
        List<Category> parentCategory = categoryRepository.findAllByCategoryParentIsNull();
        return parentCategory.stream()
            .map(ParentCategoryReadResponseDto::new)
            .collect(Collectors.toList());
    }

    /**
     * 전체 출판사를 조회하는 메서드입니다.
     *
     * @return PublisherReadResponseDto
     */
    private List<PublisherReadResponseDto> getPublisherList() {
        return publisherRepository.findAll().stream().map(
            publisher -> PublisherReadResponseDto.builder().publisherId(publisher.getPublisherId())
                .publisherName(publisher.getPublisherName()).build()).collect(
            Collectors.toList());
    }

    /**
     * 전체 작가를 조회하는 메서드입니다.
     *
     * @return AuthorReadResponseDto
     */
    private List<AuthorReadResponseDto> getAuthorList() {
        return authorRepository.findAll().stream().map(
            author -> AuthorReadResponseDto.builder().authorId(author.getAuthorId())
                .authorName(author.getAuthorName()).build()).collect(
            Collectors.toList());
    }

    /**
     * 전체 도서 상태를 조회하는 메서드입니다.
     *
     * @return BookStatusReadResponseDto
     */
    private List<BookStatusReadResponseDto> getStatuses() {
        return bookStatusRepository.findAll().stream().map(
            status -> BookStatusReadResponseDto.builder().statusId(status.getStatusId())
                .statusName(status.getStatusName()).build()).collect(
            Collectors.toList());
    }

    /**
     * 전체 태그를 조회하는 메서드입니다.
     *
     * @return TagReadResponseDto
     */
    private List<TagReadResponseDto> getTagList() {
        return tagRepository.findAll().stream().map(TagReadResponseDto::new)
            .collect(Collectors.toList());
    }
}
