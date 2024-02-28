package com.nhnacademy.inkbridge.backend.service;

import com.nhnacademy.inkbridge.backend.dto.book.BookAdminCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.book.BookAdminReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.book.BookAdminUpdateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.book.BookReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.book.BooksAdminReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.book.BooksReadResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

/**
 * class: BookService.
 *
 * @author minm063
 * @version 2024/02/14
 */
public interface BookService {

    /**
     * page에 따른 전체 도서를 가져오는 메서드입니다.
     *
     * @param pageable pageable
     * @return BooksReadResponseDto
     */
    Page<BooksReadResponseDto> readBooks(Pageable pageable);

    /**
     * Book Id값으로 dto에 대한 데이터를 가져오는 메서드입니다. parameter가 데이터베이스에 저장되어 있지 않을 시 NotFoundException을
     * 던집니다.
     *
     * @return BookReadResponseDto
     */
    BookReadResponseDto readBook(Long bookId);

    /**
     * admin 페이지에서 필요한 전체 도서 관련 데이터를 가져오는 메서드입니다.
     *
     * @param pageable pageable
     * @return BooksAdminReadResponseDto
     */
    Page<BooksAdminReadResponseDto> readBooksByAdmin(Pageable pageable);

    /**
     * admin 페이지에서 필요한 상세 도서 관련 데이터를 가져오는 메서드입니다. parameter가 데이터베이스에 저장되어 있지 않을 시 NotFoundException을
     * 던진다.
     *
     * @param bookId 도서 id, 0보다 커야 한다
     * @return BookAdminReadResponseDto
     */
    BookAdminReadResponseDto readBookByAdmin(Long bookId);

    /**
     * 입력값에 대해 새로운 Book을 데이터베이스에 추가하는 메서드입니다. 해당하는 BookStatus, File, Publisher가 데이터베이스에 저장되어 있지 않을 시
     * NotFoundException을 던진다.
     *
     * @param thumbnail                 MultipartFile
     * @param bookAdminCreateRequestDto BookAdminCreateRequestDto
     */
    void createBook(MultipartFile thumbnail, BookAdminCreateRequestDto bookAdminCreateRequestDto);

    /**
     * 입력값에 대해 도서 정보를 수정하는 메서드입니다. 해당하는 BookStatus, File, Publisher가 데이터베이스에 저장되어 있지 않을 시
     * NotFoundException을 던진다.
     *
     * @param bookId                    Long
     * @param bookAdminUpdateRequestDto BookAdminUpdateResponseDto
     */
    void updateBookByAdmin(Long bookId, MultipartFile thumbnail,
        BookAdminUpdateRequestDto bookAdminUpdateRequestDto);
}
