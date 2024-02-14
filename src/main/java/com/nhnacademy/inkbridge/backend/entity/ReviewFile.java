package com.nhnacademy.inkbridge.backend.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * class: ReviewFile.
 *
 * @author brihgtclo
 * @version 2024/02/14
 */

@Entity
@Table(name = "review_file")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReviewFile {

    @Id
    @OneToOne
    @JoinColumn(name = "file_id")
    private File file;

    @ManyToOne
    @JoinColumn(name = "review_id")
    private Book book;
}
