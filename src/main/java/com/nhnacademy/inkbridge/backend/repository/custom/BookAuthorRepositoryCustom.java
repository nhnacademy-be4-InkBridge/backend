package com.nhnacademy.inkbridge.backend.repository.custom;

import com.nhnacademy.inkbridge.backend.dto.book.AuthorPaginationReadResponseDto;
import java.util.List;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * class: BookAuthorRepositoryCustom.
 *
 * @author minm063
 * @version 2024/03/13
 */
@NoRepositoryBean
public interface BookAuthorRepositoryCustom {

    List<AuthorPaginationReadResponseDto> findAuthorNameByBookId(List<Long> bookId);
}
