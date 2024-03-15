package com.nhnacademy.inkbridge.backend.dto.book;

import com.nhnacademy.inkbridge.backend.dto.bookstatus.BookStatusReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.category.ParentCategoryReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.tag.TagReadResponseDto;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

/**
 * class: BookAdminReadResponse.
 *
 * @author minm063
 * @version 2024/02/29
 */
@Getter
public class BookAdminDetailReadResponseDto {

    BookAdminSelectedReadResponseDto adminSelectedReadResponseDto;
    List<ParentCategoryReadResponseDto> parentCategoryReadResponseDtoList;
    List<PublisherReadResponseDto> publisherReadResponseDtoList;
    List<AuthorReadResponseDto> authorReadResponseDtoList;
    List<BookStatusReadResponseDto> bookStatusReadResponseDtoList;
    List<TagReadResponseDto> tagReadResponseDtoList;

    @Builder
    public BookAdminDetailReadResponseDto(
        BookAdminSelectedReadResponseDto adminSelectedReadResponseDto,
        List<ParentCategoryReadResponseDto> parentCategoryReadResponseDtoList,
        List<PublisherReadResponseDto> publisherReadResponseDtoList,
        List<AuthorReadResponseDto> authorReadResponseDtoList,
        List<BookStatusReadResponseDto> bookStatusReadResponseDtoList,
        List<TagReadResponseDto> tagReadResponseDtoList) {
        this.adminSelectedReadResponseDto = adminSelectedReadResponseDto;
        this.parentCategoryReadResponseDtoList = parentCategoryReadResponseDtoList;
        this.publisherReadResponseDtoList = publisherReadResponseDtoList;
        this.authorReadResponseDtoList = authorReadResponseDtoList;
        this.bookStatusReadResponseDtoList = bookStatusReadResponseDtoList;
        this.tagReadResponseDtoList = tagReadResponseDtoList;
    }
}
