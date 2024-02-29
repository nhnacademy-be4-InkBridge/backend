package com.nhnacademy.inkbridge.backend.repository;

import com.nhnacademy.inkbridge.backend.dto.book.AuthorReadResponseDto;
import com.nhnacademy.inkbridge.backend.entity.Author;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * class: AuthorRepository.
 *
 * @author minm063
 * @version 2024/02/23
 */
public interface AuthorRepository extends JpaRepository<Author, Long> {

    List<AuthorReadResponseDto> findAllBy();
}
