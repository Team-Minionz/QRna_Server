package com.minionz.backend.shop.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UseStatus {

    USING("STATUS_USE", "사용중"),
    EMPTY("STATUS_NOT_USE", "미사용중");

    private final String status;
    private final String message;
}
