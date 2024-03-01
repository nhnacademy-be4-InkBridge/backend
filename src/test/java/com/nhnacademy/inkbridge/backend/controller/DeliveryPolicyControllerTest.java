package com.nhnacademy.inkbridge.backend.controller;


import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.nhnacademy.inkbridge.backend.dto.deliverypolicy.DeliveryPolicyReadResponseDto;
import com.nhnacademy.inkbridge.backend.service.DeliveryPolicyService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

/**
 * class: DeliveryPolicyControllerTest.
 *
 * @author jangjaehun
 * @version 2024/03/01
 */
@WebMvcTest(DeliveryPolicyController.class)
class DeliveryPolicyControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    DeliveryPolicyService deliveryPolicyService;

    @Test
    @DisplayName("적용중인 배송비 정책 조회")
    void testGetCurrentDeliveryPolicy() throws Exception {
        DeliveryPolicyReadResponseDto responseDto = new DeliveryPolicyReadResponseDto(1L, 5000L, 50000L);

        given(deliveryPolicyService.getCurrentDeliveryPolicy()).willReturn(responseDto);

        mockMvc.perform(get("/api/delivery-policies/current"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.deliveryPolicyId").value(responseDto.getDeliveryPolicyId()))
            .andExpect(jsonPath("$.deliveryPrice").value(responseDto.getDeliveryPrice()))
            .andExpect(jsonPath("$.freeDeliveryPrice").value(responseDto.getFreeDeliveryPrice()));
    }
}
