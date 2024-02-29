package com.nhnacademy.inkbridge.backend.controller;

import com.nhnacademy.inkbridge.backend.dto.file.FileCreateResponseDto;
import com.nhnacademy.inkbridge.backend.entity.File;
import com.nhnacademy.inkbridge.backend.service.FileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * class: FileController.
 *
 * @author minm063
 * @version 2024/02/25
 */
@RestController
@RequestMapping("/api/admin/images")
public class FileRestController {

    private final FileService fileService;

    public FileRestController(FileService fileService) {
        this.fileService = fileService;
    }

    /**
     * 이미지를 서버에 저장하는 api입니다.
     *
     * @param image MultipartFile
     * @return FileCreateResponseDto
     */
    @PostMapping
    public ResponseEntity<FileCreateResponseDto> uploadBookImages(
        @RequestPart MultipartFile image) {
        File file = fileService.saveFile(image);
        FileCreateResponseDto fileCreateResponseDto = FileCreateResponseDto.builder()
            .fileId(file.getFileId())
            .fileName(file.getFileName())
            .build();

        return new ResponseEntity<>(fileCreateResponseDto, HttpStatus.CREATED);
    }

    /**
     * 이미지를 클라이언트에 보여주는 api입니다.
     *
     * @param fileName RequestParam, String
     * @return byte[]
     */
    @GetMapping
    public ResponseEntity<byte[]> loadBookImage(@RequestParam String fileName) {
        return new ResponseEntity<>(fileService.loadFileByByte(fileName), HttpStatus.OK);
    }
}
