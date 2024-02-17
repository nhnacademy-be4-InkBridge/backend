package com.nhnacademy.inkbridge.backend.repository;

import com.nhnacademy.inkbridge.backend.entity.BookCategory;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * class: BookCategoryRepository.
 *
 * @author choijaehun
 * @version 2/17/24
 */
public interface BookCategoryRepository extends JpaRepository<BookCategory, BookCategory.Pk> {
    List<BookCategory> findByPk_BookId(Long bookId);
}
