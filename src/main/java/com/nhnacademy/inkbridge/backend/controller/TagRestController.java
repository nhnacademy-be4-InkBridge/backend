package com.nhnacademy.inkbridge.backend.controller;

import com.nhnacademy.inkbridge.backend.dto.tag.TagCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.tag.TagCreateResponseDto;
import com.nhnacademy.inkbridge.backend.dto.tag.TagDeleteResponseDto;
import com.nhnacademy.inkbridge.backend.dto.tag.TagReadResponseDto;
import com.nhnacademy.inkbridge.backend.dto.tag.TagUpdateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.tag.TagUpdateResponseDto;
import com.nhnacademy.inkbridge.backend.service.TagService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * class: TagRestController.
 *
 * @author jeongbyeonghun
 * @version 2/15/24
 */

@RestController
@RequestMapping(value = "/tag")
@RequiredArgsConstructor
public class TagRestController {

    private final TagService tagService;

    @PostMapping("/register")
    public ResponseEntity<TagCreateResponseDto> createTag(@RequestBody TagCreateRequestDto newTag) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tagService.createTag(newTag));
    }

    @GetMapping
    public ResponseEntity<List<TagReadResponseDto>> getTagList() {
        return ResponseEntity.ok(tagService.getTagList());
    }

    @PutMapping("/{tagId}/modify")
    public ResponseEntity<TagUpdateResponseDto> modifyTag(@PathVariable(name = "tagId") Long tagId,
        @RequestBody TagUpdateRequestDto newTag) {
        return ResponseEntity.ok(tagService.updateTag(tagId, newTag));
    }

    @DeleteMapping("/{tagId}/delete")
    public ResponseEntity<TagDeleteResponseDto> deleteTag(
        @PathVariable(name = "tagId") Long tagId) {
        return ResponseEntity.ok(tagService.deleteTag(tagId));
    }

}
