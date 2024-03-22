package com.nhnacademy.inkbridge.backend.repository;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.nhnacademy.inkbridge.backend.dto.author.AuthorInfoReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.author.AuthorPaginationReadResponseDto;
import com.nhnacademy.inkbridge.backend.entity.Author;
import com.nhnacademy.inkbridge.backend.entity.Book;
import com.nhnacademy.inkbridge.backend.entity.BookAuthor;
import com.nhnacademy.inkbridge.backend.entity.BookAuthor.Pk;
import com.nhnacademy.inkbridge.backend.entity.BookStatus;
import com.nhnacademy.inkbridge.backend.entity.File;
import com.nhnacademy.inkbridge.backend.entity.Publisher;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

/**
 * class: AuthorRepositoryTest.
 *
 * @author minm063
 * @version 2024/03/17
 */
@DataJpaTest
@TestMethodOrder(OrderAnnotation.class)
class AuthorRepositoryTest {

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    TestEntityManager testEntityManager;

    Pageable pageable;
    File file;
    Author author;

    @BeforeEach
    void setup() {
        pageable = PageRequest.of(0, 5);
        file = File.builder().fileName("fileName").fileUrl("fileUrl")
            .fileExtension("fileExtension").build();
        author = Author.builder().authorName("authorName").authorIntroduce("authorIntroduce")
            .file(file).build();

        testEntityManager.persist(file);
        testEntityManager.persist(author);
    }

    @AfterEach
    public void teardown() {
        this.authorRepository.deleteAll();
        this.testEntityManager
            .getEntityManager()
            .createNativeQuery("ALTER TABLE author ALTER COLUMN `author_id` RESTART WITH 1")
            .executeUpdate();
    }

    @Test
    @DisplayName("작가 아이디로 작가 정보 조회")
    @Order(1)
    void findByAuthorId() {
        AuthorInfoReadResponseDto byAuthorId = authorRepository.findByAuthorId(1L);

        assertAll(
            () -> assertEquals(author.getAuthorName(), byAuthorId.getAuthorName()),
            () -> assertEquals(author.getAuthorIntroduce(), byAuthorId.getAuthorIntroduce()),
            () -> assertEquals(author.getAuthorId(), byAuthorId.getAuthorId()),
            () -> assertEquals(author.getFile().getFileUrl(), byAuthorId.getFileUrl())
        );
    }

    @Test
    @DisplayName("작가 이름으로 작가 정보 조회")
    @Order(2)
    void findByAuthorName() {
        List<AuthorInfoReadResponseDto> dto = authorRepository.findByAuthorName(
            "authorName");

        assertAll(
            () -> assertEquals(1, dto.size()),
            () -> assertEquals(author.getAuthorName(), dto.get(0).getAuthorName()),
            () -> assertEquals(author.getAuthorIntroduce(), dto.get(0).getAuthorIntroduce()),
            () -> assertEquals(author.getAuthorId(), dto.get(0).getAuthorId()),
            () -> assertEquals(author.getFile().getFileUrl(), dto.get(0).getFileUrl())
        );
    }

    @Test
    @DisplayName("작가 전체 목록 조회")
    @Order(3)
    void findAllAuthors() {
        Page<AuthorInfoReadResponseDto> authors = authorRepository.findAllAuthors(pageable);

        assertAll(
            () -> assertEquals(5, authors.getSize()),
            () -> assertEquals(1, authors.getContent().size()),
            () -> assertEquals(author.getAuthorName(), authors.getContent().get(0).getAuthorName()),
            () -> assertEquals(author.getAuthorIntroduce(),
                authors.getContent().get(0).getAuthorIntroduce()),
            () -> assertEquals(author.getFile().getFileUrl(),
                authors.getContent().get(0).getFileUrl())
        );
    }

    @Disabled
    @Test
    @DisplayName("도서 아이디로 작가 이름 조회")
    @Order(4)
    void findAuthorNameByBookId() {
        BookStatus bookStatus = BookStatus.builder().statusId(1L).statusName("").build();
        Publisher publisher = Publisher.builder().publisherName("").build();
        testEntityManager.persist(bookStatus);
        testEntityManager.persist(publisher);

        Book book = Book.builder().bookTitle("").bookIndex("").description("").publicatedAt(
                LocalDate.now()).isbn("").regularPrice(1L).price(1L)
            .discountRatio(BigDecimal.valueOf(3.3)).stock(1).updatedAt(
                LocalDateTime.now()).bookStatus(bookStatus).publisher(publisher).thumbnailFile(file)
            .build();
        testEntityManager.persist(book);
        BookAuthor bookAuthor = BookAuthor.builder().author(author).book(book)
            .pk(Pk.builder().bookId(1L).authorId(1L).build())
            .build();
        testEntityManager.persist(bookAuthor);

        List<AuthorPaginationReadResponseDto> dto = authorRepository.findAuthorNameByBookId(
            List.of(1L));

        assertAll(
            () -> assertEquals(1, dto.size()),
            () -> assertEquals(1, dto.get(0).getAuthorName().size()),
            () -> assertEquals(author.getAuthorName(), dto.get(0).getAuthorName().get(0))
        );
    }
}