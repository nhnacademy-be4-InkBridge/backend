package com.nhnacademy.inkbridge.backend.entity;

import java.awt.print.Book;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * class: review.
 *
 * @author minseo
 * @version 2/8/24
 */
@Entity
@Table(name = "review")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long reviewId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

//    @ManyToOne
//    @JoinColumn(name = "book_id")
//    private Book book;

//    @OneToOne
//    @JoinColumn(name = "file_id")
//    private File file;

    @Column(name = "review_title")
    private String reviewTitle;

    @Column(name = "review_content")
    private String reviewContent;

    @Column(name = "registered_at")
    private LocalDateTime registeredAt;

    @Column(name = "score")
    private Integer score;


}
