package com.nhnacademy.inkbridge.backend.service;


import com.nhnacademy.inkbridge.backend.entity.File;
import java.util.List;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

/**
 * class: FileService.
 *
 * @author jeongbyeonghun
 * @version 2/21/24
 */
public interface FileService {

    File saveFile(MultipartFile file);

    Resource loadFile(String fileName);

    List<File> saveBookFile(Long bookId, List<MultipartFile> files);

    List<File> saveReviewFile(Long reviewId, List<MultipartFile> files);
}
