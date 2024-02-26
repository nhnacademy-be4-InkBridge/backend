package com.nhnacademy.inkbridge.backend.service.impl;

import com.nhnacademy.inkbridge.backend.entity.Book;
import com.nhnacademy.inkbridge.backend.entity.BookFile;
import com.nhnacademy.inkbridge.backend.entity.File;
import com.nhnacademy.inkbridge.backend.entity.Review;
import com.nhnacademy.inkbridge.backend.entity.ReviewFile;
import com.nhnacademy.inkbridge.backend.enums.BookMessageEnum;
import com.nhnacademy.inkbridge.backend.enums.FileMessageEnum;
import com.nhnacademy.inkbridge.backend.exception.FileStorageException;
import com.nhnacademy.inkbridge.backend.exception.NotFoundException;
import com.nhnacademy.inkbridge.backend.repository.BookFileRepository;
import com.nhnacademy.inkbridge.backend.repository.BookRepository;
import com.nhnacademy.inkbridge.backend.repository.FileRepository;
import com.nhnacademy.inkbridge.backend.repository.ReviewFileRepository;
import com.nhnacademy.inkbridge.backend.repository.ReviewRepository;
import com.nhnacademy.inkbridge.backend.service.FileService;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
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
    private final BookRepository bookRepository;
    private final ReviewRepository reviewRepository;
    private final ReviewFileRepository reviewFileRepository;
    private static final String URL_HEAD = "https://inkbridge.store/image/";
    private final String uploadDir = System.getProperty("user.dir") + "/upload";
    private final Path fileStorage = Paths.get("upload");
    private final Path noImageFile = Paths.get("upload/noImage.png");


    @Override
    public File saveFile(MultipartFile file) {
        String fileName = LocalDateTime.now() + file.getOriginalFilename();
        java.io.File saveFile = new java.io.File(uploadDir + fileName);

        try {
            file.transferTo(saveFile);
        } catch (IOException e) {
            throw new FileStorageException(FileMessageEnum.FILE_SAVE_ERROR.getMessage());
        }
        File newFile = File.builder().fileName(fileName).fileUrl(URL_HEAD + fileName).build();

        return fileRepository.save(newFile);
    }

    @Override
    public Resource loadFile(String fileName) {
        Path filePath = fileStorage.resolve(fileName).normalize();

        try {
            Resource resource = new UrlResource(filePath.toUri());
            if (!resource.exists()) {
                return new UrlResource(noImageFile.toUri());
            }
            return resource;

        } catch (MalformedURLException e) {
            throw new FileStorageException(FileMessageEnum.FILE_LOAD_ERROR.getMessage());
        }
    }


    @Override
    public List<File> saveBookFile(Long bookId, List<MultipartFile> files) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new NotFoundException(
            BookMessageEnum.BOOK_NOT_FOUND.getMessage()));
        List<File> fileList = files.stream().map(this::saveFile).collect(Collectors.toList());
        List<BookFile> bookFileList = fileList.stream()
            .map(file -> BookFile.builder().book(book).fileId(file.getFileId()).build()).collect(
                Collectors.toList());
        bookFileRepository.saveAll(bookFileList);
        return fileList;
    }

    @Override
    public List<File> saveReviewFile(Long reviewId, List<MultipartFile> files) {
        Review review = reviewRepository.findById(reviewId).orElseThrow();

        List<File> fileList = files.stream().map(this::saveFile).collect(Collectors.toList());
        List<ReviewFile> reviewFileList = fileList.stream()
            .map(file -> ReviewFile.builder().review(review).fileId(file.getFileId()).build())
            .collect(
                Collectors.toList());
        reviewFileRepository.saveAll(reviewFileList);
        return fileList;
    }


}
