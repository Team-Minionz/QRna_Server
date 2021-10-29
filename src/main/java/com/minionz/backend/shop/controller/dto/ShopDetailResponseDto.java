package com.minionz.backend.shop.controller.dto;

import com.minionz.backend.common.domain.Address;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
public class ShopDetailResponseDto {

    private String name;
    private Address address;
    private String telNumber;
    private List<ShopTableCountResponseDto> tableInfoList;
    private int userCongestion;

    public ShopDetailResponseDto(String name, Address address, String telNumber, List<ShopTableCountResponseDto> tableInfoList, int userCongestion) {
        this.name = name;
        this.address = address;
        this.telNumber = telNumber;
        this.tableInfoList = tableInfoList;
        this.userCongestion = userCongestion;
    }
}
