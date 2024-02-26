package com.nhnacademy.inkbridge.backend.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.nhnacademy.inkbridge.backend.entity.Book;
import com.nhnacademy.inkbridge.backend.entity.File;
import com.nhnacademy.inkbridge.backend.entity.Review;
import com.nhnacademy.inkbridge.backend.exception.NotFoundException;
import com.nhnacademy.inkbridge.backend.repository.BookFileRepository;
import com.nhnacademy.inkbridge.backend.repository.BookRepository;
import com.nhnacademy.inkbridge.backend.repository.FileRepository;
import com.nhnacademy.inkbridge.backend.repository.ReviewFileRepository;
import com.nhnacademy.inkbridge.backend.repository.ReviewRepository;
import com.nhnacademy.inkbridge.backend.service.impl.FileServiceImpl.FileUtil;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

@ExtendWith(MockitoExtension.class)
class FileServiceImplTest {

    @Mock
    private FileRepository fileRepository;
    @Mock
    private BookFileRepository bookFileRepository;
    @Mock
    private ReviewFileRepository reviewFileRepository;
    @Mock
    private BookRepository bookRepository;
    @Mock
    private ReviewRepository reviewRepository;

    @InjectMocks
    private FileServiceImpl fileService;

    private MultipartFile multipartFile;
    private File file;
    private Book book;
    private Review review;

    @BeforeEach
    void setUp() {
        multipartFile = new MockMultipartFile("file", "filename.txt", "text/plain",
            "some xml".getBytes());
        file = new File("fileUrl", "filename.txt", "txt");
        book = Book.builder().build();
        review = Review.builder().build();

    }

    @Test
    void saveFile() {
        when(fileRepository.save(any(File.class))).thenReturn(file);

        File savedFile = fileService.saveFile(multipartFile);

        assertNotNull(savedFile);
        assertEquals(file.getFileName(), savedFile.getFileName());
        verify(fileRepository).save(any(File.class));
    }

    @Test
    void testSaveFile() {
        MockMultipartFile multipartFile = new MockMultipartFile("file", "filename.txt",
            "text/plain", "some text".getBytes());
        File savedFile = new File("http://localhost/filename.txt", "filename.txt", "txt");
        when(fileRepository.save(any(File.class))).thenReturn(savedFile);

        File result = fileService.saveFile(multipartFile);

        assertNotNull(result);
        assertEquals("filename.txt", result.getFileName());
        verify(fileRepository, times(1)).save(any(File.class));
    }


    @Test
    void saveBookFile() {
        Long bookId = 1L;
        when(bookRepository.findById(bookId)).thenReturn(java.util.Optional.of(book));
        when(fileRepository.save(any(File.class))).thenReturn(file);

        List<File> savedFiles = fileService.saveBookFile(bookId, Arrays.asList(multipartFile));

        assertNotNull(savedFiles);
        assertEquals(1, savedFiles.size());
        verify(bookFileRepository).saveAll(any());
    }

    @Test
    void saveBookFileWhenBookNotFound() {
        Long bookId = 1L;
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class,
            () -> fileService.saveBookFile(bookId, new ArrayList<>()));
    }

    @Test
    void loadFile() throws MalformedURLException {
        String testFileName = "testfile.txt";
        Path fileStorage = Paths.get("upload");
        UrlResource urlResource = new UrlResource(fileStorage.resolve(testFileName).toUri());

        assertEquals(urlResource, fileService.loadFile(testFileName));
    }

    @Test
    void loadFileWhenFileNoExist() throws MalformedURLException {
        String testFileName = "noExistTestFile.txt";
        Path fileStorage = Paths.get("upload");
        Resource result = new UrlResource(fileStorage.resolve("noImage.png").toUri());
        UrlResource urlResource = new UrlResource(fileStorage.resolve(testFileName).toUri());

        assertEquals(result, fileService.loadFile(testFileName));
    }


    @Test
    void saveReviewFile() {
        Long reviewId = 1L;
        when(reviewRepository.findById(reviewId)).thenReturn(java.util.Optional.of(review));
        when(fileRepository.save(any(File.class))).thenReturn(file);

        List<File> savedFiles = fileService.saveReviewFile(reviewId, Arrays.asList(multipartFile));

        assertNotNull(savedFiles);
        assertEquals(1, savedFiles.size());
        verify(reviewFileRepository).saveAll(any());
    }

}
