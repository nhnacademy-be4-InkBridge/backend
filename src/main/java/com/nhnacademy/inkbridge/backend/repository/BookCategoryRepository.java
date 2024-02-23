package com.nhnacademy.inkbridge.backend.repository;

import com.nhnacademy.inkbridge.backend.entity.BookCategory;
import com.nhnacademy.inkbridge.backend.entity.BookCategory.Pk;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * class: BookCategoryRepository.
 *
 * @author minm063
 * @version 2024/02/21
 */
public interface BookCategoryRepository extends JpaRepository<BookCategory, Pk> {

}
