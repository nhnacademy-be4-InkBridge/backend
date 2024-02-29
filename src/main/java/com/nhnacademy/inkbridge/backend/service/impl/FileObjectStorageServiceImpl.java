package com.nhnacademy.inkbridge.backend.service.impl;

import com.nhnacademy.inkbridge.backend.entity.File;
import com.nhnacademy.inkbridge.backend.enums.FileMessageEnum;
import com.nhnacademy.inkbridge.backend.exception.NotFoundException;
import com.nhnacademy.inkbridge.backend.repository.FileRepository;
import com.nhnacademy.inkbridge.backend.service.FileService;
import com.nhnacademy.inkbridge.backend.service.ObjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * class: FileObjectStorageServiceImpl.
 *
 * @author jeongbyeonghun
 * @version 2/27/24
 */

@Service
@RequiredArgsConstructor
public class FileObjectStorageServiceImpl implements FileService {

    private final FileRepository fileRepository;
    private final ObjectService objectService;


    @Override
    public File saveFile(MultipartFile file) {
        String fileName = objectService.uploadObject(file);
        String url = "https://inkbridge.store/image-load/";
        return fileRepository.save(
            File.builder().fileName(fileName).fileUrl(url + fileName).build());
    }

    @Override
    public ResponseEntity<byte[]> loadFile(String fileName) {
        return objectService.downloadObject(fileName);
    }

    @Override
    public ResponseEntity<byte[]> loadFileById(Long fileId) {
        File file = fileRepository.findById(fileId).orElseThrow(() -> new NotFoundException(
            FileMessageEnum.FILE_NOT_FOUND_ERROR.getMessage()));
        return objectService.downloadObject(file.getFileName());
    }

}