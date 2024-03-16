package com.nhnacademy.inkbridge.backend.entity;

import java.time.LocalDateTime;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * class: BookSearch.
 *
 * @author choijaehun
 * @version 2024/03/10
 */

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@Document(indexName="#{elasticsearch.index-name}")
@Document(indexName = "inkbridge_book_dev")
public class BookSearch {
    @Id
    private Long id;
    private String bookTitle;
    private String description;
    private LocalDateTime publicatedAt;
    private Long regularPrice;
    private Long price;
    private Double discountRatio;
    private Long view;
    private Double score;
    private Long reviewQuantity;
    private Long publisherId;
    private String publisherName;
    private String statusName;
    private String fileName;
    private Long authorId;
    private String authorName;
    private Long tagId;
    private String tagName;
}
