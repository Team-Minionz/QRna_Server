package com.minionz.backend.user.controller.dto;

import com.minionz.backend.common.domain.Address;
import com.minionz.backend.shop.domain.Shop;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class UserVisitResponseDto {

    private String shopName;
    private Address shopAddress;
    private String shopTelNumber;
    private LocalDateTime visitedDate;

    public UserVisitResponseDto(Shop shop, LocalDateTime visitedDate) {
        this.shopName = shop.getName();
        this.shopAddress = shop.getAddress();
        this.shopTelNumber = shop.getTelNumber();
        this.visitedDate = visitedDate;
    }
}
