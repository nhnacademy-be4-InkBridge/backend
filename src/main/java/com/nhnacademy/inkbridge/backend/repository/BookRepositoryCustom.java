package com.nhnacademy.inkbridge.backend.repository;

import com.nhnacademy.inkbridge.backend.dto.book.BookAdminReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.book.BooksAdminReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.book.BooksReadResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * class: BookRepositoryCustom.
 *
 * @author minm063
 * @version 2024/02/15
 */
@NoRepositoryBean
public interface BookRepositoryCustom {

    Page<BooksReadResponseDto> findAllBooks(Pageable pageable);

    Page<BooksAdminReadResponseDto> findAllBooksByAdmin(Pageable pageable);

    BookAdminReadResponseDto findBookByAdminByBookId(Long bookId);
}
