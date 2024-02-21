package com.nhnacademy.inkbridge.backend.service.impl;

import com.nhnacademy.inkbridge.backend.dto.book.BookAdminCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.book.BookAdminReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.book.BookAdminUpdateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.book.BookAdminUpdateResponseDto;
import com.nhnacademy.inkbridge.backend.dto.book.BookReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.book.BooksAdminReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.book.BooksReadResponseDto;
import com.nhnacademy.inkbridge.backend.entity.Book;
import com.nhnacademy.inkbridge.backend.entity.BookStatus;
import com.nhnacademy.inkbridge.backend.entity.File;
import com.nhnacademy.inkbridge.backend.entity.Publisher;
import com.nhnacademy.inkbridge.backend.enums.BookMessageEnum;
import com.nhnacademy.inkbridge.backend.exception.NotFoundException;
import com.nhnacademy.inkbridge.backend.repository.BookRepository;
import com.nhnacademy.inkbridge.backend.repository.BookStatusRepository;
import com.nhnacademy.inkbridge.backend.repository.FileRepository;
import com.nhnacademy.inkbridge.backend.repository.PublisherRepository;
import com.nhnacademy.inkbridge.backend.service.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * class: BookServiceImpl.
 *
 * @author minm063
 * @version 2024/02/14
 */
@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookStatusRepository bookStatusRepository;
    private final FileRepository fileRepository;
    private final PublisherRepository publisherRepository;

    public BookServiceImpl(BookRepository bookRepository, BookStatusRepository bookStatusRepository,
        FileRepository fileRepository, PublisherRepository publisherRepository) {
        this.bookRepository = bookRepository;
        this.bookStatusRepository = bookStatusRepository;
        this.fileRepository = fileRepository;
        this.publisherRepository = publisherRepository;
    }


    /**
     * page에 따른 전체 도서를 가져오는 메서드입니다.
     *
     * @param pageable pageable
     * @return BooksReadResponseDto
     */
    @Override
    @Transactional(readOnly = true)
    public Page<BooksReadResponseDto> readBooks(Pageable pageable) {
        return bookRepository.findAllBooks(pageable);
    }

    /**
     * id값으로 dto에 대한 데이터를 가져오는 메서드입니다. parameter가 데이터베이스에 저장되어 있지 않을 시 NotFoundException을 던진다.
     *
     * @return BookReadResponseDto
     */
    @Override
    @Transactional(readOnly = true)
    public BookReadResponseDto readBook(Long bookId) {
        if (!bookRepository.existsById(bookId)) {
            throw new NotFoundException(BookMessageEnum.BOOK_NOT_FOUND.toString());
        }

        return bookRepository.findByBookId(bookId);
    }

    /**
     * admin 페이지에서 필요한 전체 도서 관련 데이터를 가져오는 메서드입니다.
     *
     * @param pageable pageable
     * @return BooksAdminReadResponseDto
     */
    @Override
    @Transactional(readOnly = true)
    public Page<BooksAdminReadResponseDto> readBooksByAdmin(Pageable pageable) {
        return bookRepository.findAllBooksByAdmin(pageable);
    }

    /**
     * admin 페이지에서 필요한 상세 도서 관련 데이터를 가져오는 메서드입니다. parameter가 데이터베이스에 저장되어 있지 않을 시 NotFoundException을
     * 던진다.
     *
     * @param bookId 도서 id, 0보다 커야 한다
     * @return BookAdminReadResponseDto
     */
    @Override
    @Transactional(readOnly = true)
    public BookAdminReadResponseDto readBookByAdmin(Long bookId) {
        if (!bookRepository.existsById(bookId)) {
            throw new NotFoundException(BookMessageEnum.BOOK_NOT_FOUND.toString());
        }

        return bookRepository.findBookByAdminByBookId(bookId);
    }

    /**
     * 입력값에 대해 새로운 Book을 데이터베이스에 추가하는 메서드입니다. 해당하는 BookStatus, File, Publisher가 데이터베이스에 저장되어 있지 않을 시
     * NotFoundException을 던진다.
     *
     * @param bookAdminCreateRequestDto BookCreateRequestDto
     */
    @Override
    @Transactional
    public void createBook(BookAdminCreateRequestDto bookAdminCreateRequestDto) {
        BookStatus bookStatus = bookStatusRepository.findById(
                bookAdminCreateRequestDto.getStatusId())
            .orElseThrow(() -> new NotFoundException(BookMessageEnum.BOOK_NOT_FOUND.toString()));
        File file = fileRepository.findById(bookAdminCreateRequestDto.getThumbnailId()).orElseThrow(
            () -> new NotFoundException(BookMessageEnum.BOOK_THUMBNAIL_NOT_FOUND.toString()));
        Publisher publisher = publisherRepository.findById(
                bookAdminCreateRequestDto.getThumbnailId())
            .orElseThrow(
                () -> new NotFoundException(BookMessageEnum.BOOK_PUBLISHER_NOT_FOUND.toString()));

        Book book = Book.builder()
            .bookTitle(bookAdminCreateRequestDto.getBookTitle())
            .bookIndex(bookAdminCreateRequestDto.getBookIndex())
            .description(bookAdminCreateRequestDto.getDescription())
            .publicatedAt(bookAdminCreateRequestDto.getPublicatedAt())
            .isbn(bookAdminCreateRequestDto.getIsbn())
            .regularPrice(bookAdminCreateRequestDto.getRegularPrice())
            .price(bookAdminCreateRequestDto.getPrice())
            .discountRatio(bookAdminCreateRequestDto.getDiscountRatio())
            .stock(bookAdminCreateRequestDto.getStock())
            .isPackagable(bookAdminCreateRequestDto.getIsPackagable())
            .bookStatus(bookStatus)
            .publisher(publisher)
            .thumbnailFile(file)
            .build();

        bookRepository.save(book);
    }

    /**
     * 입력값에 대해 도서 정보를 수정하는 메서드입니다. 해당하는 BookStatus, File, Publisher가 데이터베이스에 저장되어 있지 않을 시
     * NotFoundException을 던진다.
     *
     * @param bookId                    Long
     * @param bookAdminUpdateRequestDto BookAdminUpdateResponseDto
     */
    @Transactional
    @Override
    public BookAdminUpdateResponseDto updateBookByAdmin(Long bookId,
        BookAdminUpdateRequestDto bookAdminUpdateRequestDto) {
        Book book = bookRepository.findById(bookId)
            .orElseThrow(() -> new NotFoundException(BookMessageEnum.BOOK_NOT_FOUND.toString()));
        Publisher publisher = publisherRepository.findById(
            bookAdminUpdateRequestDto.getPublisherId()).orElseThrow(
            () -> new NotFoundException(BookMessageEnum.BOOK_PUBLISHER_NOT_FOUND.toString()));
        BookStatus bookStatus = bookStatusRepository.findById(
            bookAdminUpdateRequestDto.getStatusId()).orElseThrow(
            () -> new NotFoundException(BookMessageEnum.BOOK_STATUS_NOT_FOUND.toString()));
        File thumbnail = fileRepository.findById(bookAdminUpdateRequestDto.getThumbnailId())
            .orElseThrow(
                () -> new NotFoundException(BookMessageEnum.BOOK_THUMBNAIL_NOT_FOUND.toString()));

        book.updateBook(bookAdminUpdateRequestDto.getBookTitle(),
            bookAdminUpdateRequestDto.getBookIndex(), bookAdminUpdateRequestDto.getDescription(),
            bookAdminUpdateRequestDto.getPublicatedAt(), bookAdminUpdateRequestDto.getIsbn(),
            bookAdminUpdateRequestDto.getRegularPrice(), bookAdminUpdateRequestDto.getPrice(),
            bookAdminUpdateRequestDto.getDiscountRatio(), bookAdminUpdateRequestDto.getStock(),
            bookAdminUpdateRequestDto.getIsPackagable(), bookStatus, publisher, thumbnail);

        return BookAdminUpdateResponseDto.builder().bookId(bookId).build();
    }
}
