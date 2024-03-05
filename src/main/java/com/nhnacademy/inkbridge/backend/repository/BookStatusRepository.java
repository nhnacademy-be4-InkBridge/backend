package com.nhnacademy.inkbridge.backend.repository;

import com.nhnacademy.inkbridge.backend.entity.BookStatus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * class: BookStatusRepository.
 *
 * @author minm063
 * @version 2024/02/14
 */
public interface BookStatusRepository extends JpaRepository<BookStatus, Long> {

    List<BookStatus> findAllBy();
}
