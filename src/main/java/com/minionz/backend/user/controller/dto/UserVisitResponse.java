package com.minionz.backend.user.controller.dto;

import com.minionz.backend.common.domain.Address;
import com.minionz.backend.shop.domain.Shop;
import com.minionz.backend.user.domain.User;
import com.minionz.backend.user.domain.UserBaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class UserVisitResponse extends UserBaseEntity {

    private String userName;
    private Address userAddress;
    private String userTelNumber;
    private String shopName;
    private Address shopAddress;
    private String shopTelNumber;
    private LocalDateTime visitedDate;

    public UserVisitResponse(User user, Shop shop, LocalDateTime visitedDate) {
        this.userName = user.getName();
        this.userAddress = user.getAddress();
        this.userTelNumber =user. getTelNumber();
        this.shopName = shop.getName();
        this.shopAddress = shop.getAddress();
        this.shopTelNumber = shop.getTelNumber();
        this.visitedDate = visitedDate;
    }
}