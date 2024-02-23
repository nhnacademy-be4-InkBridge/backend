package com.nhnacademy.inkbridge.backend.controller;

import com.nhnacademy.inkbridge.backend.dto.member.MemberCreateRequestDto;
import com.nhnacademy.inkbridge.backend.exception.ValidationException;
import com.nhnacademy.inkbridge.backend.service.impl.MemberServiceImpl;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * class: MemberController.
 *
 * @author minseo
 * @version 2/15/24
 */
@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberServiceImpl memberService;
    @PostMapping("/create")
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid MemberCreateRequestDto memberCreateRequestDto,
                                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult.toString());
        }

        memberService.createMember(memberCreateRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
