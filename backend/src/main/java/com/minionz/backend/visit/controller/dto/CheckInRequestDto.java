package com.minionz.backend.visit.controller.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CheckInRequestDto {

    private String userEmail;
    private String shopTelNumber;

    @Builder
    public CheckInRequestDto(String userEmail, String shopTelNumber) {
        this.userEmail = userEmail;
        this.shopTelNumber = shopTelNumber;
    }
}
