package com.nhnacademy.inkbridge.backend.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * class: BookFile.
 *
 * @author jangjaehun
 * @version 2024/02/14
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "book_file")
public class BookFile {

    @Id
    @Column(name = "file_id")
    private Long fileId;

    @MapsId("fileId")
    @OneToOne
    @JoinColumn(name = "file_id")
    private File file;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;
}
