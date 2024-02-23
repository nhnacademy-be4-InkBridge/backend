package com.nhnacademy.inkbridge.backend.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import com.nhnacademy.inkbridge.backend.service.TagService;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

/**
 * class: TagRestControllerTest.
 *
 * @author jeongbyeonghun
 * @version 2/16/24
 */

@AutoConfigureRestDocs
@WebMvcTest(TagRestController.class)
class TagRestControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    TagService tagService;

    ObjectMapper objectMapper = new ObjectMapper();

    private static Tag testTag1;
    private static Tag testTag2;

    private static String testTagName1;
    private static String testTagName2;
    private static Long testTagId1;
    private static Long testTagId2;

    @BeforeAll
    static void setTest() {
        testTagName1 = "testTag1";
        testTagName2 = "testTag2";
        testTagId1 = 1L;
        testTagId2 = 2L;
        testTag1 = Tag.builder().tagId(testTagId1).tagName(testTagName1).build();
        testTag2 = Tag.builder().tagId(testTagId2).tagName(testTagName2).build();
    }

    @Test
    @WithMockUser
    void createTag() throws Exception {
        TagCreateRequestDto tagCreateRequestDto = new TagCreateRequestDto();
        tagCreateRequestDto.setTagName("testTag");
        TagCreateResponseDto tagCreateResponseDto = new TagCreateResponseDto(
            Tag.builder().tagName(tagCreateRequestDto.getTagName()).build());
        given(tagService.createTag(any())).willReturn(tagCreateResponseDto);
        mvc.perform(post("/api/tags")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(tagCreateRequestDto)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("tagName", equalTo("testTag")))
            .andDo(document("docs"));
    }

    @Test
    @WithMockUser
    void createTagWhenValidationFailed() throws Exception {
        TagCreateRequestDto tagCreateRequestDto = new TagCreateRequestDto();
        tagCreateRequestDto.setTagName("");
        mvc.perform(post("/api/tags")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(tagCreateRequestDto)))
            .andExpect(status().isUnprocessableEntity())
            .andExpect(
                jsonPath("message", equalTo(TagMessageEnum.TAG_TYPE_VALID_FAIL.getMessage())))
            .andDo(document("docs"));
    }

    @Test
    @WithMockUser
    void createTagWhenAlreadyExist() throws Exception {
        TagCreateRequestDto newTestTag = new TagCreateRequestDto();
        newTestTag.setTagName(testTagName1);
        when(tagService.createTag(newTestTag)).thenThrow(AlreadyExistException.class);
        doThrow(new AlreadyExistException(TagMessageEnum.TAG_ALREADY_EXIST.name()))
            .when(tagService).createTag(any());
        mvc.perform(post("/api/tags")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newTestTag)))
            .andExpect(status().isConflict())
            .andExpect(jsonPath("message", equalTo(TagMessageEnum.TAG_ALREADY_EXIST.name())))
            .andDo(document("docs"));
    }

    @Test
    @WithMockUser
    void getTagList() throws Exception {
        List<Tag> tagList = new ArrayList<>();
        tagList.add(testTag1);
        tagList.add(testTag2);
        List<TagReadResponseDto> result = tagList.stream().map(TagReadResponseDto::new)
            .collect(Collectors.toList());
        when(tagService.getTagList()).thenReturn(result);
        mvc.perform(get("/api/tags")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].tagName", equalTo(tagList.get(0).getTagName())))
            .andExpect(jsonPath("$[0].tagId", equalTo(tagList.get(0).getTagId().intValue())))
            .andExpect(jsonPath("$[1].tagName", equalTo(tagList.get(1).getTagName())))
            .andExpect(jsonPath("$[1].tagId", equalTo(tagList.get(1).getTagId().intValue())))
            .andDo(document("docs"));
    }

    @Test
    @WithMockUser
    void updateTag() throws Exception {
        TagUpdateRequestDto tagUpdateRequestDto = new TagUpdateRequestDto();
        tagUpdateRequestDto.setTagName(testTagName2);
        TagUpdateResponseDto tagUpdateResponseDto = new TagUpdateResponseDto(
            Tag.builder().tagId(testTagId1).tagName(testTagName2).build());
        when(tagService.updateTag(any(), any())).thenReturn(
            tagUpdateResponseDto);
        mvc.perform(put("/api/tags/{tagId}", testTagId1)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(tagUpdateRequestDto)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("tagId", equalTo(testTagId1.intValue())))
            .andExpect(jsonPath("tagName", equalTo(testTagName2)))
            .andDo(document("docs"));

    }

    @Test
    @WithMockUser
    void updateTagWhenValidationFailed() throws Exception {
        TagUpdateRequestDto tagUpdateRequestDto = new TagUpdateRequestDto();

        mvc.perform(put("/api/tags/{tagId}", testTagId1)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(tagUpdateRequestDto)))
            .andExpect(status().isUnprocessableEntity())
            .andExpect(
                jsonPath("message", equalTo(TagMessageEnum.TAG_TYPE_VALID_FAIL.getMessage())))
            .andDo(document("docs"));
    }

    @Test
    @WithMockUser
    void updateTagWhenNotFoundTag() throws Exception {
        TagUpdateRequestDto tagUpdateRequestDto = new TagUpdateRequestDto();
        tagUpdateRequestDto.setTagName(testTagName2);
        when(tagService.updateTag(any(), any())).thenThrow(
            new NotFoundException(TagMessageEnum.TAG_NOT_FOUND.getMessage()));

        mvc.perform(put("/api/tags/{tagId}", testTagId1)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(tagUpdateRequestDto)))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("message", equalTo(TagMessageEnum.TAG_NOT_FOUND.getMessage())))
            .andDo(document("docs"));
    }

    @Test
    @WithMockUser
    void updateTagWhenAlreadyExist() throws Exception {
        TagUpdateRequestDto tagUpdateRequestDto = new TagUpdateRequestDto();
        tagUpdateRequestDto.setTagName(testTagName1);
        when(tagService.updateTag(any(), any())).thenThrow(
            new AlreadyExistException(TagMessageEnum.TAG_ALREADY_EXIST.getMessage()));

        mvc.perform(put("/api/tags/{tagId}", testTagId1)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(tagUpdateRequestDto)))
            .andExpect(status().isConflict())
            .andExpect(jsonPath("message", equalTo(TagMessageEnum.TAG_ALREADY_EXIST.getMessage())))
            .andDo(document("docs"));
    }

    @Test
    @WithMockUser
    void deleteTag() throws Exception {
        when(tagService.deleteTag(testTagId1)).thenReturn(
            new TagDeleteResponseDto(testTag1 + " is deleted"));
        mvc.perform(delete("/api/tags/{tagId}", testTagId1)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("message", equalTo(testTag1 + " is deleted")))
            .andDo(document("docs"));
    }

    @Test
    @WithMockUser
    void deleteTagWhenNotFound() throws Exception {
        when(tagService.deleteTag(testTagId1)).thenThrow(
            new NotFoundException(TagMessageEnum.TAG_NOT_FOUND.name()));
        mvc.perform(delete("/api/tags/{tagId}", testTagId1)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("message", equalTo(TagMessageEnum.TAG_NOT_FOUND.name())))
            .andDo(document("docs"));
    }
}