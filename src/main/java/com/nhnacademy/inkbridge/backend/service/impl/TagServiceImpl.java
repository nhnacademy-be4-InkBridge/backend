package com.nhnacademy.inkbridge.backend.service.impl;

import com.nhnacademy.inkbridge.backend.dto.tag.TagCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.tag.TagCreateResponseDto;
import com.nhnacademy.inkbridge.backend.dto.tag.TagDeleteResponseDto;
import com.nhnacademy.inkbridge.backend.dto.tag.TagReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.tag.TagUpdateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.tag.TagUpdateResponseDto;
import com.nhnacademy.inkbridge.backend.entity.Tag;
import com.nhnacademy.inkbridge.backend.enums.TagMessageEnum;
import com.nhnacademy.inkbridge.backend.exception.AlreadyExistException;
import com.nhnacademy.inkbridge.backend.exception.NotFoundException;
import com.nhnacademy.inkbridge.backend.repository.TagRepository;
import com.nhnacademy.inkbridge.backend.service.TagService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * class: TagServiceImpl.
 *
 * @author jeongbyeonghun
 * @version 2/15/24
 */
@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

    @Override
    @Transactional
    public TagCreateResponseDto createTag(TagCreateRequestDto newTag) {
        if (Boolean.TRUE.equals(tagRepository.existsByTagName(newTag.getTagName()))) {
            throw new AlreadyExistException(TagMessageEnum.TAG_ALREADY_EXIST.name());
        }
        Tag tag = Tag.builder().tagName(newTag.getTagName()).build();
        Tag savedTag = tagRepository.save(tag);
        return TagCreateResponseDto.builder().tag(savedTag).build();
    }

    @Override
    @Transactional(readOnly = true)
    public List<TagReadResponseDto> getTagList() {
        return tagRepository.findAll().stream().map(TagReadResponseDto::new)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public TagUpdateResponseDto updateTag(Long tagId, TagUpdateRequestDto newTag) {
        if (!tagRepository.existsById(tagId)) {
            throw new NotFoundException(TagMessageEnum.TAG_NOT_FOUND.name());
        }
        if (Boolean.TRUE.equals(tagRepository.existsByTagName(newTag.getTagName()))) {
            throw new AlreadyExistException(TagMessageEnum.TAG_ALREADY_EXIST.name());
        }
        Tag tag = Tag.builder().tagId(tagId).tagName(newTag.getTagName()).build();
        return TagUpdateResponseDto.builder().tag(tagRepository.save(tag)).build();
    }

    @Override
    @Transactional
    public TagDeleteResponseDto deleteTag(Long tagId) {
        if (!tagRepository.existsById(tagId)) {
            throw new NotFoundException(TagMessageEnum.TAG_NOT_FOUND.name());
        }
        return TagDeleteResponseDto.builder().message(tagId + " is deleted").build();
    }
}
