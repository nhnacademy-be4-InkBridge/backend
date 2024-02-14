package com.nhnacademy.inkbridge.backend.entity;

import javax.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "review_file")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewFile {
    @Id
    @Column(name = "file_id")
    private Long fileId;

    @MapsId("fileId")
    @OneToOne
    @JoinColumn(name = "file_id")
    private File file;

    @ManyToOne
    @JoinColumn(name = "review_id")
    private Book book;
}
