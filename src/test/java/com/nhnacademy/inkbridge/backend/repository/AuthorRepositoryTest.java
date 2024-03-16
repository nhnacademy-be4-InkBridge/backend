package com.nhnacademy.inkbridge.backend.repository;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.nhnacademy.inkbridge.backend.dto.author.AuthorInfoReadResponseDto;
import com.nhnacademy.inkbridge.backend.entity.Author;
import com.nhnacademy.inkbridge.backend.entity.File;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
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
class AuthorRepositoryTest {

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    FileRepository fileRepository;

    Pageable pageable;

    @BeforeEach
    void setup() {
        pageable = PageRequest.of(0, 5);
        File file = File.builder().fileName("fileName").fileUrl("fileUrl")
            .fileExtension("fileExtension").build();
        Author author = Author.builder().authorName("authorName").authorIntroduce("authorIntroduce")
            .file(file).build();

        fileRepository.save(file);
        authorRepository.save(author);
    }


    @Test
    void findAllAuthors() {
        Page<AuthorInfoReadResponseDto> authors = authorRepository.findAllAuthors(pageable);

        assertAll(
            () -> assertEquals(5, authors.getSize()),
            () -> assertEquals(1, authors.getContent().size()),
            () -> assertEquals("authorName", authors.getContent().get(0).getAuthorName()),
            () -> assertEquals("authorIntroduce", authors.getContent().get(0).getAuthorIntroduce()),
            () -> assertEquals("fileUrl", authors.getContent().get(0).getFileUrl())
        );
    }
}