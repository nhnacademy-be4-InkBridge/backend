package com.nhnacademy.inkbridge.backend.controller;

import com.nhnacademy.inkbridge.backend.dto.publisher.PublisherCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.publisher.PublisherReadResponseDto;
import com.nhnacademy.inkbridge.backend.enums.PublisherMessageEnum;
import com.nhnacademy.inkbridge.backend.service.PublisherService;
import javax.validation.Valid;
import javax.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * class: PublisherController.
 *
 * @author choijaehun
 * @version 2024/03/20
 */

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class PublisherController {
    private final PublisherService publisherService;

    @PostMapping("/publisher")
    public ResponseEntity<HttpStatus> createPublisher(@Valid @RequestBody PublisherCreateRequestDto request,
        BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new ValidationException(PublisherMessageEnum.PUBLISHER_VALID_FAIL.getMessage());
        }

        publisherService.createPublisher(request);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/publishers")
    public ResponseEntity<Page<PublisherReadResponseDto>> readPublishers(Pageable pageable){
        Page<PublisherReadResponseDto> response = publisherService.readPublishers(pageable);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

}
