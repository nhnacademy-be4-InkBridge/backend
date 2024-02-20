package com.nhnacademy.inkbridge.backend.repository;

import com.nhnacademy.inkbridge.backend.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * class: BookRepository.
 *
 * @author JBum
 * @version 2024/02/20
 */
public interface BookRepository extends JpaRepository<Book,Long> {

}
