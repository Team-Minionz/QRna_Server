package com.minionz.backend.shop.controller.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ShopTableCountResponseDto {

    private int maxUser;
    private int numberOfTable;

    public ShopTableCountResponseDto(int maxUser, int numberOfTable) {
        this.maxUser = maxUser;
        this.numberOfTable = numberOfTable;
    }

    private final List<Integer> tableCount = new ArrayList<Integer>();

    public ShopTableCountResponseDto(List<ShopTableRequestDto> shopTableRequestDtoList) {
        int a = 0;
        int b = 0;
        int c = 0;
        for (ShopTableRequestDto i : shopTableRequestDtoList) {
            if (i.getMaxUser() == 2) {
                a++;
            }
            if (i.getMaxUser() == 3) {
                b++;
            }
            if (i.getMaxUser() == 4) {
                c++;
            }
        }

        this.tableCount.add(a);
        this.tableCount.add(b);
        this.tableCount.add(c);
    }
}
