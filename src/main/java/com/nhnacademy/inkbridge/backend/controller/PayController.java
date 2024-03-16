package com.nhnacademy.inkbridge.backend.controller;

import com.nhnacademy.inkbridge.backend.dto.PayCreateRequestDto;
import com.nhnacademy.inkbridge.backend.facade.PayFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * class: PayController.
 *
 * @author jangjaehun
 * @version 2024/03/16
 */
@RestController
@RequestMapping("/api/pays")
@Slf4j
@RequiredArgsConstructor
public class PayController {

    private final PayFacade payFacade;

    /**
     * 결제를 진행하는 메소드입니다.
     *
     * @param requestDto 결제 정보
     * @return void
     */
    @PostMapping
    public ResponseEntity<Void> doPay(@RequestBody PayCreateRequestDto requestDto) {
        log.debug("payController Create {}", requestDto);

        payFacade.doPay(requestDto);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
