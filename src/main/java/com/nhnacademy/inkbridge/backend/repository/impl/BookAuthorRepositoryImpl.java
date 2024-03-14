package com.nhnacademy.inkbridge.backend.repository.impl;

import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.types.Projections.constructor;
import static com.querydsl.core.types.Projections.list;

import com.nhnacademy.inkbridge.backend.dto.book.AuthorPaginationReadResponseDto;
import com.nhnacademy.inkbridge.backend.entity.BookAuthor;
import com.nhnacademy.inkbridge.backend.entity.QAuthor;
import com.nhnacademy.inkbridge.backend.entity.QBook;
import com.nhnacademy.inkbridge.backend.entity.QBookAuthor;
import com.nhnacademy.inkbridge.backend.repository.custom.BookAuthorRepositoryCustom;
import java.util.List;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

/**
 * class: BookAuthorRepositoryImpl.
 *
 * @author minm063
 * @version 2024/03/13
 */
public class BookAuthorRepositoryImpl extends QuerydslRepositorySupport implements
    BookAuthorRepositoryCustom {

    public BookAuthorRepositoryImpl() {
        super(BookAuthor.class);
    }


    /**
     * @param bookId
     * @return
     */
    @Override
    public List<AuthorPaginationReadResponseDto> findAuthorNameByBookId(List<Long> bookId) {
        QBook book = QBook.book;
        QAuthor author = QAuthor.author;
        QBookAuthor bookAuthor = QBookAuthor.bookAuthor;

        return from(book)
            .innerJoin(bookAuthor).on(bookAuthor.pk.bookId.eq(book.bookId))
            .innerJoin(author).on(author.authorId.eq(bookAuthor.pk.authorId))
            .where(book.bookId.in(bookId))
            .orderBy(book.bookId.desc())
            .select(constructor(AuthorPaginationReadResponseDto.class, book.bookId,
                list(author.authorName)))
            .transform(groupBy(book.bookId).list(
                constructor(AuthorPaginationReadResponseDto.class, book.bookId,
                    list(constructor(String.class, author.authorName)))));
    }
}
