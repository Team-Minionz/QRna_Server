package com.minionz.backend.shop.controller.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ShopMaxUserResponseDto {

    private int maxUser;
    private int userUser;
    private int congestUser;

    public ShopMaxUserResponseDto(int maxUser, int userUser, int congestUser) {
        this.maxUser = maxUser;
        this.userUser = userUser;
        this.congestUser = congestUser;
    }
}
