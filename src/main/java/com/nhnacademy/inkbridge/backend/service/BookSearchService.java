package com.nhnacademy.inkbridge.backend.service;

import com.nhnacademy.inkbridge.backend.entity.Search;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * class: BookSearchService.
 *
 * @author choijaehun
 * @version 2024/03/10
 */
public interface BookSearchService {

    Page<Search> searchByText(String text, Pageable pageable);

    Page<Search> searchByAll(Pageable pageable);

    Page<Search> searchByCategory(String category,Pageable pageable);
}
