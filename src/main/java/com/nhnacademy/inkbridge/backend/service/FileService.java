package com.nhnacademy.inkbridge.backend.service;

import com.nhnacademy.inkbridge.backend.entity.File;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

/**
 * class: FileService.
 *
 * @author jeongbyeonghun
 * @version 2/27/24
 */
public interface FileService {

    File saveFile(MultipartFile file);

    ResponseEntity<byte[]> loadFile(String fileName);

    ResponseEntity<byte[]> loadFileById(Long fileId);

    List<File> saveBookFile(Long bookId, List<MultipartFile> files);

    List<File> saveReviewFile(Long reviewId, List<MultipartFile> files);
}
