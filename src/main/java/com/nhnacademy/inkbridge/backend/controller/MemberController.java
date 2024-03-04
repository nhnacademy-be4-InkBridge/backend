package com.nhnacademy.inkbridge.backend.controller;

import com.nhnacademy.inkbridge.backend.dto.member.reqeuest.MemberAuthLoginRequestDto;
import com.nhnacademy.inkbridge.backend.dto.member.reqeuest.MemberCreateRequestDto;
import com.nhnacademy.inkbridge.backend.dto.member.response.MemberAuthLoginResponseDto;
import com.nhnacademy.inkbridge.backend.dto.member.response.MemberInfoResponseDto;
import com.nhnacademy.inkbridge.backend.exception.ValidationException;
import com.nhnacademy.inkbridge.backend.service.impl.MemberServiceImpl;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
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
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberServiceImpl memberService;

    @PostMapping("/members")
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid MemberCreateRequestDto memberCreateRequestDto,
                                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult.toString());
        }

        memberService.createMember(memberCreateRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/members/login")
    public ResponseEntity<MemberAuthLoginResponseDto> authLogin(
            @RequestBody @Valid MemberAuthLoginRequestDto memberAuthLoginRequestDto,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult.toString());
        }
        MemberAuthLoginResponseDto memberAuthLoginResponseDto =
                memberService.loginInfoMember(memberAuthLoginRequestDto);

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(memberAuthLoginResponseDto);
    }

    @GetMapping("/auth/info")
    public ResponseEntity<MemberInfoResponseDto> getMemberInfo(HttpServletRequest request) {

        Long memberId = Long.parseLong(request.getHeader("Authorization-Id"));

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(memberService.getMemberInfo(memberId));
    }

}
