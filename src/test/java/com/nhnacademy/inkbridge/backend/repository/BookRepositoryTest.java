//package com.nhnacademy.inkbridge.backend.repository;
//
//import static org.junit.jupiter.api.Assertions.assertAll;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertFalse;
//
//import com.nhnacademy.inkbridge.backend.dto.book.BookAdminSelectedReadResponseDto;
//import com.nhnacademy.inkbridge.backend.dto.book.BookReadResponseDto;
//import com.nhnacademy.inkbridge.backend.dto.book.BooksAdminReadResponseDto;
//import com.nhnacademy.inkbridge.backend.dto.book.BooksPaginationReadResponseDto;
//import com.nhnacademy.inkbridge.backend.entity.Author;
//import com.nhnacademy.inkbridge.backend.entity.Book;
//import com.nhnacademy.inkbridge.backend.entity.BookAuthor;
//import com.nhnacademy.inkbridge.backend.entity.BookAuthor.Pk;
//import com.nhnacademy.inkbridge.backend.entity.BookCategory;
//import com.nhnacademy.inkbridge.backend.entity.BookFile;
//import com.nhnacademy.inkbridge.backend.entity.BookStatus;
//import com.nhnacademy.inkbridge.backend.entity.BookTag;
//import com.nhnacademy.inkbridge.backend.entity.Category;
//import com.nhnacademy.inkbridge.backend.entity.File;
//import com.nhnacademy.inkbridge.backend.entity.Member;
//import com.nhnacademy.inkbridge.backend.entity.Publisher;
//import com.nhnacademy.inkbridge.backend.entity.Tag;
//import com.nhnacademy.inkbridge.backend.entity.Wish;
//import java.math.BigDecimal;
//import java.time.LocalDate;
//import java.util.Optional;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
//import org.junit.jupiter.api.Order;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.TestMethodOrder;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//
///**
// * class: BookRepositoryTest.
// *
// * @author minm063
// * @version 2024/03/06
// */
//@DataJpaTest
//@TestMethodOrder(OrderAnnotation.class)
//class BookRepositoryTest {
//
//    @Autowired
//    TestEntityManager testEntityManager;
//    @Autowired
//    BookRepository bookRepository;
//    @Autowired
//    BookStatusRepository bookStatusRepository;
//    @Autowired
//    FileRepository fileRepository;
//    @Autowired
//    BookFileRepository bookFileRepository;
//    @Autowired
//    PublisherRepository publisherRepository;
//    @Autowired
//    AuthorRepository authorRepository;
//    @Autowired
//    BookAuthorRepository bookAuthorRepository;
//    @Autowired
//    CategoryRepository categoryRepository;
//    @Autowired
//    BookCategoryRepository bookCategoryRepository;
//    @Autowired
//    TagRepository tagRepository;
//    @Autowired
//    BookTagRepository bookTagRepository;
//    @Autowired
//    WishRepository wishRepository;
//    @Autowired
//    MemberRepository memberRepository;
//
//    Pageable pageable;
//    Book book;
//    Publisher publisher;
//    Author author;
//    File file;
//    Wish wish;
//    BookStatus bookStatus;
//
//    @BeforeEach
//    void setUp() {
//        pageable = PageRequest.of(0, 5);
//        bookStatus = BookStatus.builder().statusId(1L).statusName("status").build();
//        publisher = Publisher.builder().publisherName("publisher").build();
//        file = File.builder().fileName("file").fileExtension("extension").fileExtension("png")
//            .build();
//        author = Author.builder().authorName("author").authorIntroduce("introduce").file(file)
//            .build();
//        book = Book.builder().bookTitle("title").bookIndex("index").description("description")
//            .publicatedAt(LocalDate.parse("2024-03-06")).isbn("1234567890123").regularPrice(1L)
//            .price(1L)
//            .discountRatio(BigDecimal.valueOf(3.33)).stock(1).isPackagable(true)
//            .bookStatus(bookStatus)
//            .publisher(publisher).thumbnailFile(file).build();
//        Member member = Member.create().memberName("member").build();
//        Category category = Category.create().categoryName("category").build();
//        Tag tag = Tag.builder().tagName("tag").build();
//
//        bookStatusRepository.save(bookStatus);
//        publisherRepository.save(publisher);
//        fileRepository.save(file);
//        authorRepository.save(author);
//        categoryRepository.save(category);
//        tagRepository.save(tag);
//        bookRepository.save(book);
//        memberRepository.save(member);
//
//        BookAuthor bookAuthor = BookAuthor.builder()
//            .pk(Pk.builder().bookId(book.getBookId()).authorId(
//                author.getAuthorId()).build()).book(book).author(author).build();
//        bookAuthorRepository.save(bookAuthor);
//        BookCategory bookCategory = BookCategory.create()
//            .pk(BookCategory.Pk.builder().bookId(book.getBookId()).categoryId(
//                category.getCategoryId()).build()).book(book)
//            .category(category).build();
//        bookCategoryRepository.save(bookCategory);
//        BookTag bookTag = BookTag.builder()
//            .pk(BookTag.Pk.builder().bookId(book.getBookId()).tagId(tag.getTagId()).build())
//            .tag(tag).book(book)
//            .build();
//        bookTagRepository.save(bookTag);
//        wish = Wish.builder().pk(Wish.Pk.builder().memberId(1L).bookId(1L).build()).member(member)
//            .book(book).build();
//        wishRepository.save(wish);
//        BookFile bookFile = BookFile.builder().fileId(file.getFileId()).book(book).build();
//        bookFileRepository.save(bookFile);
//    }
//
//    @Test
//    @Order(2)
//    void findAllBooks() {
//        Page<BooksPaginationReadResponseDto> books = bookRepository.findAllBooks(pageable);
//
//        assertAll(
//            () -> assertEquals(5, books.getSize()),
//            () -> assertEquals(1, books.getContent().size()),
//            () -> assertEquals(book.getBookId(), books.getContent().get(0).getBookId()),
//            () -> assertEquals(book.getBookTitle(), books.getContent().get(0).getBookTitle()),
//            () -> assertEquals(book.getPrice(), books.getContent().get(0).getPrice()),
//            () -> assertEquals(publisher.getPublisherName(),
//                books.getContent().get(0).getPublisherName()),
//            () -> assertEquals(author.getAuthorName(),
//                books.getContent().get(0).getAuthorName().get(0)),
//            () -> assertEquals(file.getFileUrl(), books.getContent().get(0).getFileUrl())
//        );
//    }
//
//    @Test
//    @Order(1)
//    void findByBookId() {
//        Optional<BookReadResponseDto> bookReadResponseDtoOptional = bookRepository.findByBookId(1L,
//            1L);
//        if (bookReadResponseDtoOptional.isPresent()) {
//            BookReadResponseDto bookReadResponseDto = bookReadResponseDtoOptional.get();
//            assertAll(
//                () -> assertEquals(bookReadResponseDto.getBookTitle(), book.getBookTitle()),
//                () -> assertEquals(bookReadResponseDto.getBookIndex(), book.getBookIndex()),
//                () -> assertEquals(bookReadResponseDto.getDescription(), book.getDescription()),
//                () -> assertEquals(bookReadResponseDto.getPublicatedAt(), book.getPublicatedAt()),
//                () -> assertEquals(bookReadResponseDto.getIsbn(), book.getIsbn()),
//                () -> assertEquals(bookReadResponseDto.getRegularPrice(), book.getRegularPrice()),
//                () -> assertEquals(bookReadResponseDto.getPrice(), book.getPrice()),
//                () -> assertEquals(bookReadResponseDto.getDiscountRatio(), book.getDiscountRatio()),
//                () -> assertEquals(bookReadResponseDto.getIsPackagable(), book.getIsPackagable()),
//                () -> assertEquals(bookReadResponseDto.getPublisherId(),
//                    publisher.getPublisherId()),
//                () -> assertEquals(bookReadResponseDto.getPublisherName(),
//                    publisher.getPublisherName()),
//                () -> assertEquals(1, bookReadResponseDto.getAuthors().size()),
//                () -> assertEquals(bookReadResponseDto.getWish(), wish.getPk().getMemberId()),
//                () -> assertEquals(1, bookReadResponseDto.getFileUrl().size()),
//                () -> assertEquals(1, bookReadResponseDto.getTagName().size()),
//                () -> assertEquals(1, bookReadResponseDto.getCategoryName().size())
//            );
//        }
//    }
//
//    @Test
//    @Order(6)
//    void givenInvalidBookId_findByBookId() {
//        Optional<BookReadResponseDto> bookReadResponseDto = bookRepository.findByBookId(7L, 7L);
//
//        assertFalse(bookReadResponseDto.isPresent());
//    }
//
//    @Test
//    @Order(3)
//    void findAllBooksByAdmin() {
//        Page<BooksAdminReadResponseDto> allBooksByAdmin = bookRepository.findAllBooksByAdmin(
//            pageable);
//
//        assertAll(
//            () -> assertEquals(5, allBooksByAdmin.getSize()),
//            () -> assertEquals(1, allBooksByAdmin.getContent().size()),
//            () -> assertEquals(allBooksByAdmin.getContent().get(0).getBookId(), book.getBookId()),
//            () -> assertEquals(allBooksByAdmin.getContent().get(0).getBookTitle(),
//                book.getBookTitle()),
//            () -> assertEquals(allBooksByAdmin.getContent().get(0).getAuthorName().get(0),
//                author.getAuthorName()),
//            () -> assertEquals(allBooksByAdmin.getContent().get(0).getPublisherName(),
//                publisher.getPublisherName()),
//            () -> assertEquals(allBooksByAdmin.getContent().get(0).getStatusName(),
//                bookStatus.getStatusName())
//        );
//    }
//
//    @Test
//    @Order(4)
//    void findBookByAdminBookId() {
//        Optional<BookAdminSelectedReadResponseDto> bookByAdminByBookIdOptional = bookRepository.findBookByAdminByBookId(
//            4L);
//
//        if (bookByAdminByBookIdOptional.isPresent()) {
//            BookAdminSelectedReadResponseDto bookByAdminByBookId = bookByAdminByBookIdOptional.get();
//            assertAll(
//                () -> assertEquals(bookByAdminByBookId.getBookTitle(), book.getBookTitle()),
//                () -> assertEquals(bookByAdminByBookId.getBookIndex(), book.getBookIndex()),
//                () -> assertEquals(bookByAdminByBookId.getDescription(), book.getDescription()),
//                () -> assertEquals(bookByAdminByBookId.getPublicatedAt(), book.getPublicatedAt()),
//                () -> assertEquals(bookByAdminByBookId.getIsbn(), book.getIsbn()),
//                () -> assertEquals(bookByAdminByBookId.getRegularPrice(), book.getRegularPrice()),
//                () -> assertEquals(bookByAdminByBookId.getPrice(), book.getPrice()),
//                () -> assertEquals(bookByAdminByBookId.getDiscountRatio(), book.getDiscountRatio()),
//                () -> assertEquals(bookByAdminByBookId.getStock(), book.getStock()),
//                () -> assertEquals(bookByAdminByBookId.getIsPackagable(), book.getIsPackagable()),
//                () -> assertEquals(bookByAdminByBookId.getAuthorIdList().get(0),
//                    author.getAuthorId()),
//                () -> assertEquals(bookByAdminByBookId.getPublisherId(),
//                    publisher.getPublisherId()),
//                () -> assertEquals(bookByAdminByBookId.getStatusId(), bookStatus.getStatusId()),
//                () -> assertEquals(bookByAdminByBookId.getUrl(), file.getFileUrl()),
//                () -> assertEquals(1, bookByAdminByBookId.getCategoryIdList().size()),
//                () -> assertEquals(1, bookByAdminByBookId.getTagIdList().size())
//            );
//        }
//    }
//
//    @Test
//    @Order(5)
//    void givenInvalidBookId_findBookByAdminBookId() {
//        Optional<BookAdminSelectedReadResponseDto> bookByAdminByBookId = bookRepository.findBookByAdminByBookId(
//            4L);
//
//        assertFalse(bookByAdminByBookId.isPresent());
//    }
//}