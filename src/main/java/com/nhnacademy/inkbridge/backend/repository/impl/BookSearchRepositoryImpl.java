package com.nhnacademy.inkbridge.backend.repository.impl;

import com.nhnacademy.inkbridge.backend.entity.BookSearch;
import com.nhnacademy.inkbridge.backend.repository.custom.BookSearchRepositoryCustom;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Repository;

/**
 * class: BookSearchRepositoryImpl.
 *
 * @author choijaehun
 * @version 2024/03/11
 */


@Repository
@RequiredArgsConstructor
public class BookSearchRepositoryImpl implements BookSearchRepositoryCustom {

    private final ElasticsearchOperations elasticsearchOperations;

    @Override
    public Page<BookSearch> searchByText(String text, Pageable pageable) {
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
            .withQuery(QueryBuilders.multiMatchQuery(text,"book_title^100","book_title.nori^100","book_title.ngram^100","description","description.nori^50","description.ngram^50","book_index^10","book_index.nori^10","book_index.ngram^10")).build();

        SearchHits<BookSearch> searchHits = elasticsearchOperations.search(searchQuery,BookSearch.class);



        return (Page<BookSearch>) searchHits.map(SearchHit::getContent);
    }
}
