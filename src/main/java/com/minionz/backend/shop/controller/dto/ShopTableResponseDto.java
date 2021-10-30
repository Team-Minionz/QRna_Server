package com.minionz.backend.shop.controller.dto;

import com.minionz.backend.shop.domain.UseStatus;
import lombok.Getter;

@Getter
public class ShopTableResponseDto {

    private Long tableId;
    private int tableNumber;
    private int maxUser;
    private int countUser;
    private UseStatus useStatus;

    public ShopTableResponseDto(Long tableId, int tableNumber, int maxUser, int countUser, UseStatus useStatus) {
        this.tableId = tableId;
        this.tableNumber = tableNumber;
        this.maxUser = maxUser;
        this.countUser = countUser;
        this.useStatus = useStatus;
    }
}
