package com.nhnacademy.inkbridge.backend.dto.file;

import lombok.Builder;
import lombok.Getter;

/**
 * class: FileCreateResponseDto.
 *
 * @author jeongbyeonghun
 * @version 2/28/24
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