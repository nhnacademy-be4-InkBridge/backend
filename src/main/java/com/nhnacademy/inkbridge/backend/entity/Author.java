package com.nhnacademy.inkbridge.backend.entity;

import javax.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * class: Author.
 *
 * @author nhn
 * @version 2024/02/08
 */

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "author")
public class Author {
    @Id
    @Column(name = "author_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long authorId;

    @Column(name = "author_name")
    private String authorName;

    @Column(name = "author_introduce")
    private String authorIntroduce;

    @OneToOne
    @JoinColumn(name = "file_id")
    private File file;
}
