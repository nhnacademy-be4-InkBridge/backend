package com.nhnacademy.inkbridge.backend.repository;

import com.nhnacademy.inkbridge.backend.entity.BookSearch;
import com.nhnacademy.inkbridge.backend.repository.custom.BookSearchRepositoryCustom;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * class: BookSearchRepository.
 *
 * @author choijaehun
 * @version 2024/03/10
 */
public interface BookSearchRepository extends ElasticsearchRepository<BookSearch, Long>,
    BookSearchRepositoryCustom {

}
