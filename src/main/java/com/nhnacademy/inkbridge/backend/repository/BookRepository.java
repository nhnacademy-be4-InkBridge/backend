package com.nhnacademy.inkbridge.backend.repository;

import com.nhnacademy.inkbridge.backend.entity.Book;
import com.nhnacademy.inkbridge.backend.repository.custom.BookRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * class: BookRepository.
 *
 * @author minm063
 * @version 2024/02/14
 */
public interface BookRepository extends JpaRepository<Book, Long>, BookRepositoryCustom {
}
