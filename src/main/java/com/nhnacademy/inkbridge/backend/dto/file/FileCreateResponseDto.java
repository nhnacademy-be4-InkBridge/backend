package com.nhnacademy.inkbridge.backend.dto.file;

import lombok.Builder;
import lombok.Getter;

/**
 * class: FileCreateResponseDto.
 *
 * @author minm063
 * @version 2024/02/25
 */
@Getter
public class FileCreateResponseDto {

    private final Long fileId;
    private final String fileName;

    @Builder
    public FileCreateResponseDto(Long fileId, String fileName) {
        this.fileId = fileId;
        this.fileName = fileName;
    }
}
