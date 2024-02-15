package com.nhnacademy.inkbridge.backend.repository;

import com.nhnacademy.inkbridge.backend.dto.book.BooksReadResponseDto;
import com.nhnacademy.inkbridge.backend.entity.Book;
import java.awt.print.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * class: BookRepository.
 *
 * @author minm063
 * @version 2024/02/14
 */
public interface BookRepository extends JpaRepository<Book, Long> {
    Page<BooksReadResponseDto> findAll(Pageable pageable);
}
