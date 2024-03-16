package com.nhnacademy.inkbridge.backend.repository.custom;

import com.nhnacademy.inkbridge.backend.entity.BookSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * class: BookSearchRepositoryCustom.
 *
 * @author choijaehun
 * @version 2024/03/11
 */
public interface BookSearchRepositoryCustom {

    Page<BookSearch> searchByText(String text, Pageable pageable);

}
