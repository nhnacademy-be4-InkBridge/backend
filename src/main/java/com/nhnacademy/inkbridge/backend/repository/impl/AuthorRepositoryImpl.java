package com.nhnacademy.inkbridge.backend.repository.impl;

import com.nhnacademy.inkbridge.backend.dto.author.AuthorInfoReadResponseDto;
import com.nhnacademy.inkbridge.backend.entity.Author;
import com.nhnacademy.inkbridge.backend.entity.QAuthor;
import com.nhnacademy.inkbridge.backend.entity.QFile;
import com.nhnacademy.inkbridge.backend.repository.custom.AuthorRepositoryCustom;
import com.querydsl.core.types.Projections;
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
     * @param authorId
     * @return
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
}
