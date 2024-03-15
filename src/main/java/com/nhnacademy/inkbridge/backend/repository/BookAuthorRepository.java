package com.nhnacademy.inkbridge.backend.repository;

import com.nhnacademy.inkbridge.backend.entity.BookAuthor;
import com.nhnacademy.inkbridge.backend.entity.BookAuthor.Pk;
import com.nhnacademy.inkbridge.backend.repository.custom.BookAuthorRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * class: BookAuthorRepository.
 *
 * @author minm063
 * @version 2024/02/15
 */
public interface BookAuthorRepository extends JpaRepository<BookAuthor, Pk>,
    BookAuthorRepositoryCustom {

    BookAuthor findByPk_BookId(Long bookId);

    void deleteAllByBook_BookId(Long bookId);
}
