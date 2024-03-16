package com.nhnacademy.inkbridge.backend.repository.impl;

import com.nhnacademy.inkbridge.backend.dto.author.AuthorInfoReadResponseDto;
import com.nhnacademy.inkbridge.backend.entity.Author;
import com.nhnacademy.inkbridge.backend.entity.QAuthor;
import com.nhnacademy.inkbridge.backend.entity.QFile;
import com.nhnacademy.inkbridge.backend.repository.custom.AuthorRepositoryCustom;
import com.querydsl.core.types.Projections;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

/**
 * class: AuthorRepositoryImpl.
 *
 * @author minm063
 * @version 2024/03/15
 */
public class AuthorRepositoryImpl extends QuerydslRepositorySupport implements
    AuthorRepositoryCustom {

    public AuthorRepositoryImpl() {
        super(Author.class);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public AuthorInfoReadResponseDto findByAuthorId(Long authorId) {
        QAuthor author = QAuthor.author;
        QFile file = QFile.file;

        return from(author)
            .innerJoin(file).on(file.eq(author.file))
            .where(author.authorId.eq(authorId))
            .select(Projections.constructor(AuthorInfoReadResponseDto.class, author.authorName,
                author.authorIntroduce, file.fileUrl))
            .fetchOne();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<AuthorInfoReadResponseDto> findAllAuthors(Pageable pageable) {
        QAuthor author = QAuthor.author;
        QFile file = QFile.file;

        List<AuthorInfoReadResponseDto> authors = from(author)
            .innerJoin(file).on(file.eq(author.file))
            .select(Projections.constructor(AuthorInfoReadResponseDto.class, author.authorId,
                author.authorName, author.authorIntroduce, file.fileUrl))
            .orderBy(author.authorId.asc())
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        return new PageImpl<>(authors, pageable, getCount());
    }

    /**
     * 작가 데이터 개수를 조회하는 메서드입니다.
     *
     * @return Long
     */
    private Long getCount() {
        QAuthor author = QAuthor.author;

        return from(author)
            .select(author.count())
            .fetchOne();
    }
}
