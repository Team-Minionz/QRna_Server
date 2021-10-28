package com.minionz.backend.shop.controller.dto;

import com.minionz.backend.common.domain.Address;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
public class ShopDetailsResponseDto {

    private Long shopId;
    private String name;
    private Address address;
    private String telNumber;
    private List<ShopTableRequestDto> tableList;
    private List<ShopTableCountResponseDto> tableCount;
    private ShopMaxUserResponseDto MaxUser;
    private double Populate;
    private double User;

    public ShopDetailsResponseDto(String name, Address address, String telNumber, List<ShopTableRequestDto> tableList, List<ShopTableCountResponseDto> tableCount, Long shopId, double User, ShopMaxUserResponseDto MaxUser, double Populate) {
        this.shopId = shopId;
        this.name = name;
        this.address = address;
        this.telNumber = telNumber;
        this.tableList = tableList;
        this.tableCount = tableCount;
        this.User = User;
        this.MaxUser = MaxUser;
        this.Populate = Populate;
    }
}
