package com.nhnacademy.inkbridge.backend.service;


import com.nhnacademy.inkbridge.backend.entity.Book;
import com.nhnacademy.inkbridge.backend.entity.File;
import com.nhnacademy.inkbridge.backend.entity.Review;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

/**
 * class: FileService.
 *
 * @author jeongbyeonghun
 * @version 2/21/24
 */
public interface FileService {

    File saveFile(MultipartFile file);

    MultipartFile loadFIle(Long fileId);

    List<File> bookFile(Book book, List<MultipartFile> files);

    List<File> reviewFile(Review review, List<MultipartFile> files);
}