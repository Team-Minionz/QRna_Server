package com.minionz.backend.user.controller.dto;

import com.minionz.backend.common.domain.Address;
import com.minionz.backend.shop.domain.Shop;
import com.minionz.backend.user.domain.UserBaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class UserVisitResponse extends UserBaseEntity {

    private String name;
    private Address address;
    private String telNumber;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public UserVisitResponse(Shop shop) {
        this.name = shop.getName();
        this.address = shop.getAddress();
        this.telNumber = shop.getTelNumber();
        this.createdDate = shop.getCreatedDate();
        this.modifiedDate = shop.getModifiedDate();
    }
}