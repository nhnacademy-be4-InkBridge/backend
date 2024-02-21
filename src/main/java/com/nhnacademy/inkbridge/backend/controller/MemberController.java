package com.nhnacademy.inkbridge.backend.controller;

import com.nhnacademy.inkbridge.backend.dto.member.MemberCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.member.MemberCreateResponseDto;
import com.nhnacademy.inkbridge.backend.dto.member.MemberGetLoginRequestDto;
import com.nhnacademy.inkbridge.backend.dto.member.MemberGetLoginResponseDto;
import com.nhnacademy.inkbridge.backend.exception.ValidationException;
import com.nhnacademy.inkbridge.backend.service.MemberService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
@RequestMapping("/api")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    
    @PostMapping("/member/create/{otherLogin}")
    public ResponseEntity<MemberCreateResponseDto> create(
        @RequestBody @Valid MemberCreateRequestDto memberCreateRequestDto,
        @PathVariable(name = "otherLogin", required = false) String otherLogin,
        BindingResult bindingResult) {

        if (otherLogin != null) {
            memberCreateRequestDto.setPassword(otherLogin);
        }

        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult.toString());
        }

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(memberService.createMember(memberCreateRequestDto));
    }

    @GetMapping("/member/login/{otherLogin}")
    public ResponseEntity<MemberGetLoginResponseDto> login(
        @RequestBody @Valid MemberGetLoginRequestDto memberGetLoginRequestDto,
        @PathVariable(name = "otherLogin", required = false) String otherLogin,
        BindingResult bindingResult) {

        if (otherLogin != null) {
            memberGetLoginRequestDto.setPassword(otherLogin);
        }

        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult.toString());
        }

        return ResponseEntity.ok(memberService.login(memberGetLoginRequestDto));
    }

    @PutMapping("/admin")
    public ResponseEntity<HttpStatus> modifyMemberAuth() {
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
