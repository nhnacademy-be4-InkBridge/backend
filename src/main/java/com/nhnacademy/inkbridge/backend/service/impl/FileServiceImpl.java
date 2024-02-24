package com.nhnacademy.inkbridge.backend.service.impl;

import com.nhnacademy.inkbridge.backend.entity.Book;
import com.nhnacademy.inkbridge.backend.entity.BookFile;
import com.nhnacademy.inkbridge.backend.entity.File;
import com.nhnacademy.inkbridge.backend.entity.Review;
import com.nhnacademy.inkbridge.backend.entity.ReviewFile;
import com.nhnacademy.inkbridge.backend.enums.FileMessageEnum;
import com.nhnacademy.inkbridge.backend.exception.FileStorageException;
import com.nhnacademy.inkbridge.backend.repository.BookFileRepository;
import com.nhnacademy.inkbridge.backend.repository.FileRepository;
import com.nhnacademy.inkbridge.backend.repository.ReviewFileRepository;
import com.nhnacademy.inkbridge.backend.service.FileService;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * class: FileServiceImpl.
 *
 * @author jeongbyeonghun
 * @version 2/21/24
 */

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;
    private final BookFileRepository bookFileRepository;
    private final ReviewFileRepository reviewFileRepository;

    private final String uploadDir = System.getProperty("user.dir") + "/upload";


    @Override
    public File saveFile(MultipartFile file) {
        String fileName = LocalDateTime.now() + file.getOriginalFilename();
        java.io.File loadFile = new java.io.File(uploadDir + fileName);

        try {
            file.transferTo(loadFile);
        } catch (IOException e) {
            throw new FileStorageException(FileMessageEnum.FILE_SAVE_ERROR.getMessage());
        }
        File newFile = File.builder().fileUrl("url").fileName(fileName).build();

        return fileRepository.save(newFile);
    }

    @Override
    public MultipartFile loadFIle(Long fileId) {
        return null;
    }

    @Override
    public List<File> bookFile(Book book, List<MultipartFile> files) {
        List<File> fileList = files.stream().map(this::saveFile).collect(Collectors.toList());
        List<BookFile> bookFileList = fileList.stream()
            .map(file -> BookFile.builder().book(book).fileId(file.getFileId()).build()).collect(
                Collectors.toList());
        bookFileRepository.saveAll(bookFileList);
        return fileList;
    }

    @Override
    public List<File> reviewFile(Review review, List<MultipartFile> files) {
        List<File> fileList = files.stream().map(this::saveFile).collect(Collectors.toList());
        List<ReviewFile> reviewFileList = fileList.stream()
            .map(file -> ReviewFile.builder().review(review).fileId(file.getFileId()).build()).collect(
                Collectors.toList());
        reviewFileRepository.saveAll(reviewFileList);
        return fileList;
    }


}