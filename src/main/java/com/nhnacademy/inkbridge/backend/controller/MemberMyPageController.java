package com.nhnacademy.inkbridge.backend.controller;

import com.nhnacademy.inkbridge.backend.dto.member.reqeuest.MemberUpdatePasswordRequestDto;
import com.nhnacademy.inkbridge.backend.dto.member.reqeuest.MemberUpdateRequestDto;
import com.nhnacademy.inkbridge.backend.service.MemberService;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * class: MemberMyPageController.
 *
 * @author devminseo
 * @version 3/19/24
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mypage/members")
@Slf4j
public class MemberMyPageController {
    private final MemberService memberService;

    @PutMapping("/{memberId}")
    public ResponseEntity<Void> updateMember(@Valid @RequestBody MemberUpdateRequestDto memberUpdateRequestDto,
                                             @PathVariable Long memberId) {
        memberService.updateMember(memberUpdateRequestDto,memberId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{memberId}/password")
    public ResponseEntity<Boolean> updatePassword(@Valid @RequestBody
                                                  MemberUpdatePasswordRequestDto memberUpdatePasswordRequestDto,
                                                  @PathVariable("memberId") Long memberId) {
        log.info("start update ->");
        Boolean result = memberService.updatePassword(memberUpdatePasswordRequestDto, memberId);
        log.info("result -> {}", result);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(result);
    }

    @GetMapping("/password")
    public ResponseEntity<String> getPassword(HttpServletRequest request) {
        Long memberId = Long.parseLong(request.getHeader("Authorization-Id"));
        log.info("start get Password");

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(memberService.getPassword(memberId));
    }
}
