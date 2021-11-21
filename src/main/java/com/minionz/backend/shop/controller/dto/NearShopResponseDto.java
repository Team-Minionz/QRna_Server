package com.minionz.backend.shop.controller.dto;

import com.minionz.backend.common.dto.AddressDto;
import com.minionz.backend.shop.domain.CongestionStatus;
import com.minionz.backend.shop.domain.Shop;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NearShopResponseDto {

    private Long id;
    private String name;
    private AddressDto address;
    private CongestionStatus congestionStatus;
    private int numberOfTables;
    private int useTables;
    private double latitude;
    private double longitude;

    public NearShopResponseDto(Shop shop) {
        this.id = shop.getId();
        this.name = shop.getName();
        this.address = new AddressDto(shop.getAddress());
        this.congestionStatus = shop.getCongestionStatus();
        this.numberOfTables = shop.getNumberOfTables();
        this.useTables = shop.getNumberOfUsingTables();
        this.latitude = shop.getAddress().getLatitude();
        this.longitude = shop.getAddress().getLongitude();
    }
}
