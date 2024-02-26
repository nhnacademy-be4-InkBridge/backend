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

    /**
     * 업로드된 파일을 서버에 저장하고, 파일 정보를 데이터베이스에 기록합니다.
     *
     * @param file 저장할 파일
     * @return 저장된 파일의 정보를 담은 {@link File} 객체
     */
    File saveFile(MultipartFile file);

    /**
     * 지정된 파일 이름으로 저장된 파일을 로드합니다. 파일이 존재하지 않을 경우, 대체 이미지를 반환합니다.
     *
     * @param fileName 로드할 파일의 이름
     * @return 파일 리소스
     */
    Resource loadFile(String fileName);

    /**
     * 지정된 책 ID에 대해 업로드된 파일들을 저장하고, 책과 파일의 연동 정보를 데이터베이스에 기록합니다.
     *
     * @param bookId 책의 ID
     * @param files 저장할 파일 리스트
     * @return 저장된 파일들의 정보 리스트
     */
    List<File> saveBookFile(Long bookId, List<MultipartFile> files);

    /**
     * 지정된 리뷰 ID에 대해 업로드된 파일들을 저장하고, 리뷰와 파일의 연동 정보를 데이터베이스에 기록합니다.
     *
     * @param reviewId 리뷰의 ID
     * @param files 저장할 파일 리스트
     * @return 저장된 파일들의 정보 리스트
     */
    List<File> saveReviewFile(Long reviewId, List<MultipartFile> files);
}
