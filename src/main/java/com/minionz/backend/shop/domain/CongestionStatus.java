package com.minionz.backend.shop.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CongestionStatus {

    SMOOTH("STATUS_SMOOTH", "원활"),
    NORMAL("STATUS_NORMAL", "보통"),
    CONGESTED("STATUS_CONGESTED", "혼잡");

    private final String status;
    private final String message;
}
