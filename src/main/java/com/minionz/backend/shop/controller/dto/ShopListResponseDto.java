package com.minionz.backend.shop.controller.dto;

import com.minionz.backend.shop.domain.CongestionStatus;
import lombok.Getter;

@Getter
public class ShopListResponseDto {

    private String name;
    private CongestionStatus congestionStatus;

    public ShopListResponseDto(String name, CongestionStatus congestionStatus) {
        this.name = name;
        this.congestionStatus = congestionStatus;
    }
}
