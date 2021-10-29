package com.minionz.backend.shop.controller.dto;

import com.minionz.backend.common.domain.Address;
import com.minionz.backend.shop.domain.CongestionStatus;
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
    private CongestionStatus congestionStatus;
    private int useUser;
    private int maxUser;
    private boolean bookMark;

    public ShopDetailResponseDto(String name, Address address, String telNumber, List<ShopTableCountResponseDto> tableInfoList, int useUser, int maxUser, CongestionStatus congestionStatus, boolean bookMark) {
        this.name = name;
        this.address = address;
        this.telNumber = telNumber;
        this.tableInfoList = tableInfoList;
        this.useUser = useUser;
        this.maxUser = maxUser;
        this.congestionStatus = congestionStatus;
        this.bookMark = bookMark;
    }
}
