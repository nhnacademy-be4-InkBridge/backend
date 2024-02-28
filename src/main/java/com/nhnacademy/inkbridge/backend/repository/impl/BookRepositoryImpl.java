package com.nhnacademy.inkbridge.backend.repository.impl;

import com.nhnacademy.inkbridge.backend.dto.book.BookAdminReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.book.BookReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.book.BooksAdminReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.book.BooksReadResponseDto;
import com.nhnacademy.inkbridge.backend.entity.Book;
import com.nhnacademy.inkbridge.backend.entity.QAuthor;
import com.nhnacademy.inkbridge.backend.entity.QBook;
import com.nhnacademy.inkbridge.backend.entity.QBookAuthor;
import com.nhnacademy.inkbridge.backend.entity.QBookStatus;
import com.nhnacademy.inkbridge.backend.entity.QPublisher;
import com.nhnacademy.inkbridge.backend.repository.custom.BookRepositoryCustom;
import com.querydsl.core.types.Projections;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

/**
 * class: BookRepositoryImpl.
 *
 * @author minm063
 * @version 2024/02/15
 */
public class BookRepositoryImpl extends QuerydslRepositorySupport implements BookRepositoryCustom {

    public BookRepositoryImpl() {
        super(Book.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<BooksReadResponseDto> findAllBooks(Pageable pageable) {
        QBook book = QBook.book;
        QAuthor author = QAuthor.author;
        QBookAuthor bookAuthor = QBookAuthor.bookAuthor;
        QPublisher publisher = QPublisher.publisher;
        QBookStatus bookStatus = QBookStatus.bookStatus;

        List<BooksReadResponseDto> content = from(book)
            .innerJoin(publisher)
            .on(book.publisher.eq(publisher))
            .innerJoin(bookStatus)
            .on(bookStatus.eq(book.bookStatus))
            .innerJoin(bookAuthor)
            .on(bookAuthor.book.eq(book))
            .innerJoin(author)
            .on(author.eq(bookAuthor.author))
            .where(bookStatus.statusId.eq(1L))
            .select(Projections.constructor(BooksReadResponseDto.class,
                book.bookId, book.bookTitle, book.price, publisher.publisherName,
                author.authorName))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        return new PageImpl<>(content, pageable, content.size());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BookReadResponseDto findByBookId(Long bookId) {
        QBook book = QBook.book;
        QPublisher publisher = QPublisher.publisher;
        QBookStatus bookStatus = QBookStatus.bookStatus;

        return from(book)
            .innerJoin(publisher)
            .on(publisher.eq(book.publisher))
            .innerJoin(bookStatus)
            .on(bookStatus.eq(book.bookStatus))
            .where(bookStatus.statusId.eq(1L).and(book.bookId.eq(bookId)))
            .select(
                Projections.constructor(BookReadResponseDto.class, book.bookTitle, book.bookIndex,
                    book.description, book.publicatedAt, book.isbn, book.regularPrice,
                    book.price, book.discountRatio, book.isPackagable, publisher.publisherName))
            .fetchOne();
    }

    /**
     * 도서 개수를 조회하는 메서드입니다.
     *
     * @return Book Count
     */
    private Long getCount() {
        QBook book = QBook.book;

        return from(book)
            .select(book.count())
            .fetchOne();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<BooksAdminReadResponseDto> findAllBooksByAdmin(Pageable pageable) {
        QBook book = QBook.book;
        QAuthor author = QAuthor.author;
        QBookAuthor bookAuthor = QBookAuthor.bookAuthor;
        QPublisher publisher = QPublisher.publisher;
        QBookStatus bookStatus = QBookStatus.bookStatus;

        List<BooksAdminReadResponseDto> content = from(book)
            .innerJoin(bookAuthor)
            .on(book.eq(bookAuthor.book))
            .innerJoin(author)
            .on(bookAuthor.author.eq(author))
            .innerJoin(publisher)
            .on(book.publisher.eq(publisher))
            .innerJoin(bookStatus)
            .on(book.bookStatus.eq(bookStatus))
            .where(bookStatus.statusId.in(1L, 2L, 3L, 4L))
            .orderBy(book.bookId.asc())
            .select(Projections.constructor(BooksAdminReadResponseDto.class, book.bookId,
                book.bookTitle,
                author.authorName, publisher.publisherName, bookStatus.statusName))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        Long count = getCount();

        return new PageImpl<>(content, pageable, count);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BookAdminReadResponseDto findBookByAdminByBookId(Long bookId) {
        QBook book = QBook.book;
        QAuthor author = QAuthor.author;
        QBookAuthor bookAuthor = QBookAuthor.bookAuthor;
        QPublisher publisher = QPublisher.publisher;
        QBookStatus bookStatus = QBookStatus.bookStatus;

        return from(book)
            .innerJoin(publisher)
            .on(publisher.eq(book.publisher))
            .where(book.bookId.eq(bookId))
            .innerJoin(bookAuthor)
            .on(book.eq(bookAuthor.book))
            .innerJoin(author)
            .on(bookAuthor.author.eq(author))
            .innerJoin(bookStatus)
            .on(book.bookStatus.eq(bookStatus))
            .where(book.bookId.eq(bookId).and(bookStatus.statusId.in(1L, 2L, 3L, 4L)))
            .select(Projections.constructor(BookAdminReadResponseDto.class, book.bookTitle,
                book.bookIndex, book.description, book.publicatedAt, book.isbn,
                book.regularPrice, book.price, book.discountRatio, book.stock, book.isPackagable,
                author.authorId, publisher.publisherId, bookStatus.statusId,
                book.thumbnailFile.fileUrl))
            .fetchOne();
    }
}
