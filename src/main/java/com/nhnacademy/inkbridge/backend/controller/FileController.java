package com.nhnacademy.inkbridge.backend.controller;

import com.nhnacademy.inkbridge.backend.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * class: FileController.
 *
 * @author jeongbyeonghun
 * @version 2/25/24
 */

@RestController
@RequestMapping("/api/image")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    /**
     * 지정된 파일 이름에 해당하는 이미지 리소스를 반환합니다.
     * 파일 이름을 통해 파일 서비스를 호출하고, 로드된 리소스를 클라이언트에 제공합니다.
     *
     * @param fileName 클라이언트로부터 요청받은 파일 이름
     * @return 로드된 이미지 파일에 해당하는 {@link Resource}
     */

    @GetMapping("/{fileName}")
    Resource getImage(@PathVariable("fileName") String fileName) {
        return fileService.loadFile(fileName);
    }
}
