package com.minionz.backend.visit.controller.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CheckInRequestDto {

    private String userEmail;
    private String shopTelNumber;

    @Builder
    public CheckInRequestDto(String userEmail, String shopTelNumber) {
        this.userEmail = userEmail;
        this.shopTelNumber = shopTelNumber;
    }
}
