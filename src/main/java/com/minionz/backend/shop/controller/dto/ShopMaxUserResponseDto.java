package com.minionz.backend.shop.controller.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ShopMaxUserResponseDto {

    private int maxUser;
    private int liveUser;
    private double populateUser;

    public ShopMaxUserResponseDto(int maxUser, int liveUser, double populateUser) {
        this.maxUser = maxUser;
        this.liveUser = liveUser;
        this.populateUser = populateUser;
    }
}
