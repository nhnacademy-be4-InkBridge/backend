package com.nhnacademy.inkbridge.backend.repository.custom;

import org.springframework.data.repository.NoRepositoryBean;

/**
 * class: TagCustomRepository.
 *
 * @author jeongbyeonghun
 * @version 2/16/24
 */

@NoRepositoryBean
public interface TagCustomRepository {

    void deleteTag(Long tagId);
}
