package com.nhnacademy.inkbridge.backend.repository.impl;

import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;
import static com.querydsl.core.group.GroupBy.set;

import com.nhnacademy.inkbridge.backend.dto.book.BookAdminSelectedReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.book.BookOrderReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.book.BookReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.book.BooksAdminReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.book.BooksReadResponseDto;
import com.nhnacademy.inkbridge.backend.entity.Book;
import com.nhnacademy.inkbridge.backend.entity.QAuthor;
import com.nhnacademy.inkbridge.backend.entity.QBook;
import com.nhnacademy.inkbridge.backend.entity.QBookAuthor;
import com.nhnacademy.inkbridge.backend.entity.QBookCategory;
import com.nhnacademy.inkbridge.backend.entity.QBookFile;
import com.nhnacademy.inkbridge.backend.entity.QBookStatus;
import com.nhnacademy.inkbridge.backend.entity.QBookTag;
import com.nhnacademy.inkbridge.backend.entity.QCategory;
import com.nhnacademy.inkbridge.backend.entity.QFile;
import com.nhnacademy.inkbridge.backend.entity.QPublisher;
import com.nhnacademy.inkbridge.backend.entity.QTag;
import com.nhnacademy.inkbridge.backend.entity.QWish;
import com.nhnacademy.inkbridge.backend.repository.custom.BookRepositoryCustom;
import com.querydsl.core.types.Projections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
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
        QFile file = QFile.file;

        List<BooksReadResponseDto> content = from(book)
            .innerJoin(publisher)
            .on(book.publisher.eq(publisher))
            .innerJoin(bookStatus)
            .on(bookStatus.eq(book.bookStatus))
            .innerJoin(bookAuthor)
            .on(bookAuthor.book.eq(book))
            .innerJoin(author)
            .on(author.eq(bookAuthor.author))
            .innerJoin(file)
            .on(book.thumbnailFile.eq(file))
            .where(bookStatus.statusId.in(1L, 3L, 4L))
            .select(Projections.constructor(BooksReadResponseDto.class,
                book.bookId, book.bookTitle, book.price, publisher.publisherName,
                author.authorName, file.fileUrl))
            .orderBy(book.bookId.desc())
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        return new PageImpl<>(content, pageable, content.size());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<BooksReadResponseDto> findAllBooksByCategory(Pageable pageable, Long categoryId) {
        QBook book = QBook.book;
        QAuthor author = QAuthor.author;
        QBookAuthor bookAuthor = QBookAuthor.bookAuthor;
        QPublisher publisher = QPublisher.publisher;
        QBookStatus bookStatus = QBookStatus.bookStatus;
        QFile file = QFile.file;
        QBookCategory bookCategory = QBookCategory.bookCategory;

        List<BooksReadResponseDto> content = from(book)
            .innerJoin(publisher)
            .on(book.publisher.eq(publisher))
            .innerJoin(bookStatus)
            .on(bookStatus.eq(book.bookStatus))
            .innerJoin(bookAuthor)
            .on(bookAuthor.book.eq(book))
            .innerJoin(author)
            .on(author.eq(bookAuthor.author))
            .innerJoin(file)
            .on(book.thumbnailFile.eq(file))
            .innerJoin(bookCategory)
            .on(bookCategory.pk.bookId.eq(book.bookId))
            .where(
                bookStatus.statusId.in(1L, 3L, 4L).and(bookCategory.pk.categoryId.eq(categoryId)))
            .select(Projections.constructor(BooksReadResponseDto.class,
                book.bookId, book.bookTitle, book.price, publisher.publisherName,
                author.authorName, file.fileUrl))
            .orderBy(book.bookId.desc())
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        return new PageImpl<>(content, pageable, content.size());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<BookReadResponseDto> findByBookId(Long bookId, Long memberId) {
        QBook book = QBook.book;
        QPublisher publisher = QPublisher.publisher;
        QBookStatus bookStatus = QBookStatus.bookStatus;
        QAuthor author = QAuthor.author;
        QBookAuthor bookAuthor = QBookAuthor.bookAuthor;
        QFile thumbnail = new QFile("thumbnail");
        QFile bookImage = new QFile("bookImage");
        QBookFile bookFile = QBookFile.bookFile;
        QCategory category = QCategory.category;
        QBookCategory bookCategory = QBookCategory.bookCategory;
        QTag tag = QTag.tag;
        QBookTag bookTag = QBookTag.bookTag;
        QWish wish = QWish.wish;

        List<BookReadResponseDto> result = from(book)
            .innerJoin(publisher).on(publisher.publisherId.eq(book.publisher.publisherId))
            .innerJoin(bookStatus).on(bookStatus.statusId.eq(book.bookStatus.statusId))
            .innerJoin(bookAuthor).on(bookAuthor.book.bookId.eq(book.bookId))
            .innerJoin(author).on(bookAuthor.author.authorId.eq(author.authorId))
            .innerJoin(thumbnail).on(thumbnail.fileId.eq(book.thumbnailFile.fileId))
            .leftJoin(bookFile).on(bookFile.book.bookId.eq(book.bookId))
            .leftJoin(bookImage).on(bookImage.fileId.eq(bookFile.file.fileId))
            .leftJoin(bookTag).on(bookTag.pk.bookId.eq(book.bookId))
            .leftJoin(tag).on(tag.tagId.eq(bookTag.tag.tagId))
            .innerJoin(bookCategory).on(bookCategory.pk.bookId.eq(book.bookId))
            .innerJoin(category).on(category.categoryId.eq(bookCategory.category.categoryId))
            .leftJoin(wish).on(wish.pk.bookId.eq(book.bookId).and(wish.pk.memberId.eq(memberId)))
            .where(bookStatus.statusId.in(1L, 3L, 4L).and(book.bookId.eq(bookId)))
            .select(
                Projections.constructor(BookReadResponseDto.class, book.bookTitle, book.bookIndex,
                    book.description, book.publicatedAt, book.isbn, book.regularPrice, book.price,
                    book.discountRatio, book.isPackagable, thumbnail.fileUrl, bookStatus.statusName,
                    publisher.publisherId, publisher.publisherName, author.authorId,
                    author.authorName, wish.pk.memberId.coalesce(0L),
                    set(bookImage.fileUrl.coalesce("")),
                    set(tag.tagName.coalesce("")),
                    set(category.categoryName)))
            .transform(groupBy(book.bookId).list(Projections.constructor(BookReadResponseDto.class,
                book.bookTitle, book.bookIndex, book.description, book.publicatedAt, book.isbn,
                book.regularPrice, book.price, book.discountRatio, book.isPackagable,
                thumbnail.fileUrl, bookStatus.statusName, publisher.publisherId,
                publisher.publisherName, author.authorId, author.authorName,
                wish.pk.memberId.coalesce(0L),
                set(Projections.constructor(String.class, bookImage.fileUrl.coalesce(""))),
                set(Projections.constructor(String.class, tag.tagName.coalesce(""))),
                set(Projections.constructor(String.class, category.categoryName)))));
        if (result.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(result.get(0));
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
                book.bookTitle, author.authorName, publisher.publisherName, bookStatus.statusName))
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
    public Optional<BookAdminSelectedReadResponseDto> findBookByAdminByBookId(Long bookId) {
        QBook book = QBook.book;
        QAuthor author = QAuthor.author;
        QBookAuthor bookAuthor = QBookAuthor.bookAuthor;
        QBookCategory bookCategory = QBookCategory.bookCategory;
        QBookTag bookTag = QBookTag.bookTag;
        QFile file = QFile.file;

        List<BookAdminSelectedReadResponseDto> result = from(book)
            .innerJoin(bookAuthor).on(book.eq(bookAuthor.book))
            .innerJoin(author).on(bookAuthor.author.eq(author))
            .innerJoin(bookCategory).on(bookCategory.book.eq(book))
            .leftJoin(bookTag).on(bookTag.book.eq(book))
            .innerJoin(file).on(file.eq(book.thumbnailFile))
            .where(book.bookId.eq(bookId).and(book.bookStatus.statusId.in(1L, 2L, 3L, 4L)))
            .select(Projections.constructor(BookAdminSelectedReadResponseDto.class, book.bookTitle,
                book.bookIndex, book.description, book.publicatedAt, book.isbn,
                book.regularPrice, book.price, book.discountRatio, book.stock, book.isPackagable,
                author.authorId, book.publisher.publisherId, book.bookStatus.statusId,
                file.fileUrl,
                list(bookCategory.pk.categoryId),
                list(bookTag.pk.tagId)))
            .transform(groupBy(book.bookId).list(
                Projections.constructor(BookAdminSelectedReadResponseDto.class,
                    book.bookTitle, book.bookIndex, book.description, book.publicatedAt, book.isbn,
                    book.regularPrice, book.price, book.discountRatio, book.stock,
                    book.isPackagable,
                    author.authorId, book.publisher.publisherId, book.bookStatus.statusId,
                    file.fileUrl,
                    list(Projections.constructor(Long.class, bookCategory.pk.categoryId)),
                    list(Projections.constructor(Long.class, bookTag.pk.tagId)))
            ));
        if (result.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(result.get(0));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<BookOrderReadResponseDto> findByBookIdIn(Set<Long> bookIdList) {
        QBook book = QBook.book;
        QFile file = QFile.file;

        return from(book)
            .innerJoin(file)
            .on(file.fileId.eq(book.thumbnailFile.fileId))
            .where(book.bookId.in(bookIdList))
            .select(
                Projections.constructor(BookOrderReadResponseDto.class, book.bookId, book.bookTitle,
                    book.regularPrice, book.price, book.discountRatio, book.stock,
                    book.isPackagable,
                    file.fileUrl))
            .fetch();
    }
}
