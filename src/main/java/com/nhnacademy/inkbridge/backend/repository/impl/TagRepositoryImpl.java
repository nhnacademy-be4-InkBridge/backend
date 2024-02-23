package com.nhnacademy.inkbridge.backend.repository.impl;

import com.nhnacademy.inkbridge.backend.entity.QBookTag;
import com.nhnacademy.inkbridge.backend.entity.QTag;
import com.nhnacademy.inkbridge.backend.entity.Tag;
import com.nhnacademy.inkbridge.backend.repository.custom.TagCustomRepository;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

/**
 * class: TagRepositoryImpl.
 *
 * @author jeongbyeonghun
 * @version 2/16/24
 */
public class TagRepositoryImpl extends QuerydslRepositorySupport implements TagCustomRepository {

    public TagRepositoryImpl() {
        super(Tag.class);
    }


    @Override
    public void deleteTag(Long tagId) {
        QTag tag = QTag.tag;
        QBookTag bookTag = QBookTag.bookTag;

        delete(bookTag).where(bookTag.pk.tagId.eq(tagId));
        delete(tag).where(tag.tagId.eq(tagId));
    }
}
