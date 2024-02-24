package com.nhnacademy.inkbridge.backend.enums;

/**
 * class: BookMessageEnum.
 *
 * @author minm063
 * @version 2024/02/15
 */
public enum BookMessageEnum {
    BOOK_NOT_FOUND("존재하지 않는 도서입니다."),
    BOOK_THUMBNAIL_NOT_FOUND("도서에 해당하는 썸네일을 찾지 못했습니다."),
    BOOK_PUBLISHER_NOT_FOUND("도서에 해당하는 출판사를 찾지 못했습니다."),
    BOOK_AUTHOR_NOT_FOUND("도서에 해당하는 작가를 찾지 못했습니다."),
    BOOK_TAG_NOT_FOUND("도서에 해당하는 태그를 찾지 못했습니다."),
    BOOK_CATEGORY_NOT_FOUND("도서에 해당하는 카테고리를 찾지 못했습니다."),
    BOOK_STATUS_NOT_FOUND("해당하는 도서 상태를 찾지 못했습니다."),
    BOOK_TITLE_VALID_FAIL("도서 제목은 한 글자 이상이여야 합니다."),
    BOOK_ISBN_VALID_FAIL("isbn은 숫자 13자로 구성되어야 합니다.");

    private final String message;

    BookMessageEnum(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
