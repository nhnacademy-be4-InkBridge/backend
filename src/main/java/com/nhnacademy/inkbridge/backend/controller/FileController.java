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


    @GetMapping("/{fileName}")
    Resource getImage(@PathVariable("fileName") String fileName) {
        return fileService.loadFile(fileName);
    }
}
