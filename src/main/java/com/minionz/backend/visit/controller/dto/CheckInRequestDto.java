package com.minionz.backend.visit.controller.dto;

import com.minionz.backend.shop.domain.Shop;
import com.minionz.backend.user.domain.User;
import com.minionz.backend.visit.domain.Visit;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CheckInRequestDto {

    private Long userId;
    public Long tableId;

    @Builder
    public CheckInRequestDto(Long userId, Long tableId) {
        this.userId = userId;
        this.tableId = tableId;
    }

    public Visit toEntity(User user, Shop shop) {
        return Visit.builder()
                .user(user)
                .shop(shop)
                .build();
    }
}
