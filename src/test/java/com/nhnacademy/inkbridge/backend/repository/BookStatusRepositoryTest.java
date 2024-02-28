package com.nhnacademy.inkbridge.backend.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.nhnacademy.inkbridge.backend.entity.BookStatus;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

/**
 * class: BookStatusRepositoryTest.
 *
 * @author minm063
 * @version 2024/02/28
 */
@DataJpaTest
class BookStatusRepositoryTest {

    @Autowired
    BookStatusRepository bookStatusRepository;

    @BeforeEach
    void setup() {
        BookStatus bookStatus = BookStatus.builder().statusId(1L).statusName("test").build();
        bookStatusRepository.save(bookStatus);
    }

    @Test
    void findAllBy() {

        List<BookStatus> statuses = bookStatusRepository.findAllBy();

        assertEquals(1, statuses.size());
        assertEquals(1, statuses.get(0).getStatusId());
        assertEquals("test", statuses.get(0).getStatusName());
    }
}