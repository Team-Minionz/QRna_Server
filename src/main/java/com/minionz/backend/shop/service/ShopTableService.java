package com.minionz.backend.shop.service;

import com.minionz.backend.common.domain.Message;
import com.minionz.backend.common.exception.NotFoundException;
import com.minionz.backend.shop.domain.ShopTable;
import com.minionz.backend.shop.domain.ShopTableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ShopTableService {

    private static final String SHOP_TABLE_EXIT_SUCCESS_MESSAGE = "테이블 퇴장 성공";
    private static final String SHOP_TABLE_NOT_FOUND_MESSAGE = "해당 테이블이 존재하지 않습니다.";

    private final ShopTableRepository shopTableRepository;

    @Transactional
    public Message exitTable(Long tableId) {
        ShopTable shopTable = shopTableRepository.findById(tableId)
                .orElseThrow(() -> new NotFoundException(SHOP_TABLE_NOT_FOUND_MESSAGE));
        shopTable.exit();
        return new Message(SHOP_TABLE_EXIT_SUCCESS_MESSAGE);
    }
}
