package com.minionz.backend.shop.controller.dto;

import com.minionz.backend.common.dto.AddressDto;
import com.minionz.backend.shop.domain.CongestionStatus;
import com.minionz.backend.shop.domain.Shop;
import com.minionz.backend.user.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
public class ShopDetailResponseDto {

    private String name;
    private AddressDto address;
    private String telNumber;
    private List<ShopTableCountResponseDto> tableInfoList;
    private CongestionStatus congestionStatus;
    private int useUser;
    private int maxUser;
    private boolean bookMark;

    public ShopDetailResponseDto(Shop shop, List<ShopTableCountResponseDto> tableInfoList, User user) {
        this.name = shop.getName();
        this.address = new AddressDto(shop.getAddress());
        this.telNumber = shop.getTelNumber();
        this.tableInfoList = tableInfoList;
        this.useUser = shop.calculateUseUser();
        this.maxUser = shop.calculateMaxUser();
        this.congestionStatus = shop.getCongestionStatus();
        this.bookMark = user.checkBookmark(shop.getId());
    }
}
