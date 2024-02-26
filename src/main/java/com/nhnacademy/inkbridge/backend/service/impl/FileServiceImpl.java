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
 *  * 파일 관련 서비스를 제공하는 구현 클래스입니다.
 *  * 파일 저장 및 조회, 책과 리뷰에 대한 파일 연동 기능을 포함합니다.
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


    /**
     * 업로드된 파일을 서버에 저장하고, 파일 정보를 데이터베이스에 기록합니다.
     *
     * @param file 저장할 파일
     * @return 저장된 파일의 정보를 담은 {@link File} 객체
     * @throws FileStorageException 파일 저장 중 발생한 예외 처리
     */
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

    /**
     * 지정된 파일 이름으로 저장된 파일을 로드합니다. 파일이 존재하지 않을 경우, 대체 이미지를 반환합니다.
     *
     * @param fileName 로드할 파일의 이름
     * @return 파일 리소스
     * @throws FileStorageException 파일 로드 중 발생한 예외 처리
     */
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

    /**
     * 지정된 책 ID에 대해 업로드된 파일들을 저장하고, 책과 파일의 연동 정보를 데이터베이스에 기록합니다.
     *
     * @param bookId 책의 ID
     * @param files 저장할 파일 리스트
     * @return 저장된 파일들의 정보 리스트
     * @throws NotFoundException 지정된 책 ID가 존재하지 않을 경우 발생
     */
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

    /**
     * 지정된 리뷰 ID에 대해 업로드된 파일들을 저장하고, 리뷰와 파일의 연동 정보를 데이터베이스에 기록합니다.
     *
     * @param reviewId 리뷰의 ID
     * @param files 저장할 파일 리스트
     * @return 저장된 파일들의 정보 리스트
     * @throws NotFoundException 지정된 리뷰 ID가 존재하지 않을 경우 발생
     */
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
