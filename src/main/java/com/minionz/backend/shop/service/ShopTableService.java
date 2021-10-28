package com.minionz.backend.shop.service;

import com.minionz.backend.common.domain.Message;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@NoArgsConstructor
@Service
public class ShopTableService {

    @Transactional
    public Message exitTable(Long tableId) {
        return null;
    }
}
