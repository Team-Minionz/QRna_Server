package com.minionz.backend.shop.controller.dto;

import com.minionz.backend.shop.domain.CongestionStatus;
import lombok.Getter;

@Getter
public class ShopResponseDto {

    private String name;
    private CongestionStatus congestionStatus;

    public ShopResponseDto(String name, CongestionStatus congestionStatus) {
        this.name = name;
        this.congestionStatus = congestionStatus;
    }
}
