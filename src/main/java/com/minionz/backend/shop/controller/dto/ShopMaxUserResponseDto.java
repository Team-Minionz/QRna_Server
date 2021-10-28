package com.minionz.backend.shop.controller.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ShopMaxUserResponseDto {

    private double maxUser;
    private double liveUser;
    private double populateUser;

    public ShopMaxUserResponseDto(double maxUser, double liveUser, double populateUser) {
        this.maxUser = maxUser;
        this.liveUser = liveUser;
        this.populateUser = populateUser;
    }
}
