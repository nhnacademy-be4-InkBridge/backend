package com.nhnacademy.inkbridge.backend.repository.impl;

import com.nhnacademy.inkbridge.backend.dto.book.BookAdminReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.book.BooksAdminReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.book.BooksReadResponseDto;
import com.nhnacademy.inkbridge.backend.entity.Book;
import com.nhnacademy.inkbridge.backend.entity.QAuthor;
import com.nhnacademy.inkbridge.backend.entity.QBook;
import com.nhnacademy.inkbridge.backend.entity.QBookAuthor;
import com.nhnacademy.inkbridge.backend.entity.QBookStatus;
import com.nhnacademy.inkbridge.backend.entity.QPublisher;
import com.nhnacademy.inkbridge.backend.repository.BookRepositoryCustom;
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
     * @param pageable
     * @return
     */
    @Override
    public Page<BooksReadResponseDto> findAllBooks(Pageable pageable) {
        QBook qBook = QBook.book;
        QAuthor qAuthor = QAuthor.author;
        QBookAuthor qBookAuthor = QBookAuthor.bookAuthor;
        QPublisher qPublisher = QPublisher.publisher;
        QBookStatus qBookStatus = QBookStatus.bookStatus;

        List<BooksReadResponseDto> content = from(qBook)
            .innerJoin(qPublisher)
            .on(qBook.publisher.eq(qPublisher))
            .innerJoin(qBookAuthor)
            .on(qBook.eq(qBookAuthor.book))
            .innerJoin(qAuthor)
            .on(qBookAuthor.author.eq(qAuthor))
            .innerJoin(qBookStatus)
            .on(qBookStatus.eq(qBook.bookStatus))
            .where(qBookStatus.statusId.eq(1L)) // status가 판매
            .select(Projections.constructor(BooksReadResponseDto.class,
                qBook.bookTitle, qBook.price, qPublisher.publisherName, qAuthor.authorName))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        return new PageImpl<>(content, pageable, content.size());
    }

    /**
     * @param pageable
     * @return
     */
    @Override
    public Page<BooksAdminReadResponseDto> findAllBooksByAdmin(Pageable pageable) {
        QBook qBook = QBook.book;
        QAuthor qAuthor = QAuthor.author;
        QBookAuthor qBookAuthor = QBookAuthor.bookAuthor;
        QPublisher qPublisher = QPublisher.publisher;
        QBookStatus qBookStatus = QBookStatus.bookStatus;

        List<BooksAdminReadResponseDto> content = from(qBook)
            .innerJoin(qBookAuthor)
            .on(qBook.eq(qBookAuthor.book))
            .innerJoin(qAuthor)
            .on(qBookAuthor.author.eq(qAuthor))
            .innerJoin(qPublisher)
            .on(qBook.publisher.eq(qPublisher))
            .innerJoin(qBookStatus)
            .on(qBook.bookStatus.eq(qBookStatus))
            .where(qBookStatus.statusId.in(1L, 2L, 3L, 4L))
            .select(Projections.constructor(BooksAdminReadResponseDto.class, qBook.bookTitle,
                qAuthor.authorName, qPublisher.publisherName, qBookStatus.statusName))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        return new PageImpl<>(content, pageable, content.size());
    }

    /**
     * @param bookId
     * @return
     */
    @Override
    public BookAdminReadResponseDto findBookByAdminByBookId(Long bookId) {
        QBook qBook = QBook.book;
        QBookAuthor qBookAuthor = QBookAuthor.bookAuthor;
        QAuthor qAuthor = QAuthor.author;
        QPublisher qPublisher = QPublisher.publisher;
        QBookStatus qBookStatus = QBookStatus.bookStatus;

        // Tag, category, file을 따로
        return from(qBook)
            .innerJoin(qBookAuthor)
            .on(qBookAuthor.book.eq(qBook))
            .innerJoin(qAuthor)
            .on(qAuthor.eq(qBookAuthor.author))
            .innerJoin(qPublisher)
            .on(qPublisher.eq(qBook.publisher))
            .where(qBook.bookId.eq(bookId))
            .select(Projections.constructor(BookAdminReadResponseDto.class, qBook.bookTitle,
                qBook.bookIndex, qBook.description, qBook.publicatedAt, qBook.isbn,
                qBook.regularPrice, qBook.price, qBook.discountRatio, qBook.stock, qBook.stock,
                qBook.isPackagable, qAuthor.authorName, qBookStatus.statusName))
            .fetchOne();
    }
}
