package com.nhnacademy.inkbridge.backend.repository;

import com.nhnacademy.inkbridge.backend.entity.BookFile;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * class: BookFileRepository.
 *
 * @author jeongbyeonghun
 * @version 2/22/24
 */
public interface BookFileRepository extends JpaRepository<BookFile, Long> {

}
