package com.nhnacademy.inkbridge.backend.repository;

import com.nhnacademy.inkbridge.backend.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * class: BookRepository.
 *
 * @author choijaehun
 * @version 2/19/24
 */
public interface BookRepository extends JpaRepository<Book, Long> {

}
