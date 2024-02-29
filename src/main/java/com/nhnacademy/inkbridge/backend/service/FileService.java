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
 * @modifiedBy minm063
 * @modifiedAt 2/28/24
 *  @modificationReason - loadFile 추가
 */
public interface FileService {

    File saveFile(MultipartFile file);

    Resource loadFile(String fileName);

    Resource loadFileById(Long fileId);

    byte[] loadFileByByte(String fileName);

    List<File> saveBookFile(Long bookId, List<Long> files);

//    List<File> saveReviewFile(Long reviewId, List<MultipartFile> files);
}