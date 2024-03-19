package com.nhnacademy.inkbridge.backend.repository.custom;

import com.nhnacademy.inkbridge.backend.entity.Search;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * class: BookSearchRepositoryCustom.
 *
 * @author choijaehun
 * @version 2024/03/11
 */
public interface BookSearchRepositoryCustom {

    Page<Search> searchByText(String text, Pageable pageable);

    Page<Search> searchByAll(Pageable pageable);

}
