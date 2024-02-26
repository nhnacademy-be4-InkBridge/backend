package com.nhnacademy.inkbridge.backend.service;

import com.nhnacademy.inkbridge.backend.dto.tag.TagCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.tag.TagCreateResponseDto;
import com.nhnacademy.inkbridge.backend.dto.tag.TagDeleteResponseDto;
import com.nhnacademy.inkbridge.backend.dto.tag.TagReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.tag.TagUpdateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.tag.TagUpdateResponseDto;
import com.nhnacademy.inkbridge.backend.exception.AlreadyExistException;
import com.nhnacademy.inkbridge.backend.exception.NotFoundException;
import java.util.List;

/**
 * class: TagService.
 *
 * @author jeongbyeonghun
 * @version 2/15/24
 */
public interface TagService {

    /**
     * {@inheritDoc}
     * 새로운 태그를 생성합니다. 태그의 이름이 이미 존재할 경우 {@link AlreadyExistException}을 발생시킵니다.
     *
     * @param newTag 생성할 태그의 정보를 담고 있는 {@link TagCreateRequestDto} 객체
     * @return 생성된 태그의 정보를 담은 {@link TagCreateResponseDto}
     */
    TagCreateResponseDto createTag(TagCreateRequestDto newTag);

    /**
     * 저장된 모든 태그의 리스트를 조회합니다.
     *
     * @return 조회된 태그 리스트를 담고 있는 {@link TagReadResponseDto} 객체의 리스트
     */
    List<TagReadResponseDto> getTagList();

    /**
     * 지정된 ID를 가진 태그를 업데이트합니다. 태그 ID에 해당하는 태그가 존재하지 않거나, 업데이트할 정보의 태그 이름이 이미 존재할 경우 예외를 발생시킵니다.
     *
     * @param tagId  업데이트할 태그의 ID
     * @param newTag 업데이트할 태그의 정보를 담고 있는 {@link TagUpdateRequestDto} 객체
     * @return 업데이트된 태그의 정보를 담은 {@link TagUpdateResponseDto}
     */
    TagUpdateResponseDto updateTag(Long tagId, TagUpdateRequestDto newTag);

    /**
     * 지정된 ID를 가진 태그를 삭제합니다. 태그 ID에 해당하는 태그가 존재하지 않을 경우 {@link NotFoundException}을 발생시킵니다.
     *
     * @param tagId 삭제할 태그의 ID
     * @return 삭제 성공 메시지를 담은 {@link TagDeleteResponseDto}
     */
    TagDeleteResponseDto deleteTag(Long tagId);
}
