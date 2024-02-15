package com.nhnacademy.inkbridge.backend.service.impl;

import com.nhnacademy.inkbridge.backend.dto.book.BookCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.book.BookCreateResponseDto;
import com.nhnacademy.inkbridge.backend.entity.Book;
import com.nhnacademy.inkbridge.backend.entity.BookStatus;
import com.nhnacademy.inkbridge.backend.entity.File;
import com.nhnacademy.inkbridge.backend.entity.Publisher;
import com.nhnacademy.inkbridge.backend.repository.BookRepository;
import com.nhnacademy.inkbridge.backend.repository.BookStatusRepository;
import com.nhnacademy.inkbridge.backend.repository.FileRepository;
import com.nhnacademy.inkbridge.backend.repository.PublisherRepository;
import com.nhnacademy.inkbridge.backend.service.BookService;
import org.springframework.stereotype.Service;

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
     * @param bookCreateRequestDto
     * @return
     */
    @Override
    public BookCreateResponseDto createBook(BookCreateRequestDto bookCreateRequestDto) {
        BookStatus bookStatus = bookStatusRepository.findById(bookCreateRequestDto.getStatusId())
            .orElseThrow();
        File file = fileRepository.findById(bookCreateRequestDto.getThumbnailId()).orElseThrow();
        Publisher publisher = publisherRepository.findById(bookCreateRequestDto.getThumbnailId())
            .orElseThrow();

        Book book = Book.builder()
            .bookTitle(bookCreateRequestDto.getBookTitle())
            .bookIndex(bookCreateRequestDto.getBookIndex())
            .description(bookCreateRequestDto.getDescription())
            .publicatedAt(bookCreateRequestDto.getPublicatedAt())
            .isbn(bookCreateRequestDto.getIsbn())
            .regularPrice(bookCreateRequestDto.getRegularPrice())
            .price(bookCreateRequestDto.getPrice())
            .discountRatio(bookCreateRequestDto.getDiscountRatio())
            .stock(bookCreateRequestDto.getStock())
            .isPackagable(bookCreateRequestDto.getIsPackagable())
            .bookStatus(bookStatus)
            .publisher(publisher)
            .thumbnailFile(file)
            .build();
        bookRepository.save(book);
        return null;
    }
}
