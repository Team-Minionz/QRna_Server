package com.minionz.backend.visit.service;

import com.minionz.backend.common.domain.Message;
import com.minionz.backend.common.exception.NotFoundException;
import com.minionz.backend.shop.domain.Shop;
import com.minionz.backend.shop.domain.ShopTable;
import com.minionz.backend.shop.domain.ShopTableRepository;
import com.minionz.backend.user.domain.User;
import com.minionz.backend.user.domain.UserRepository;
import com.minionz.backend.visit.controller.dto.CheckInRequestDto;
import com.minionz.backend.visit.domain.VisitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class VisitService {

    private static final String NO_USER_ERROR_MESSAGE = "해당 유저가 존재하지 않습니다.";
    private static final String NO_SHOP_ERROR_MESSAGE = "해당 업체가 존재하지 않습니다.";
    private static final String NO_TABLE_ERROR_MESSAGE = "해당 테이블이 존재하지 않습니다.";
    private static final String CHECKIN_SUCCESS_MESSAGE = "방문 기록 성공";

    private final UserRepository userRepository;
    private final VisitRepository visitRepository;
    private final ShopTableRepository shopTableRepository;

    @Transactional
    public Message checkIn(CheckInRequestDto checkInRequestDto) {
        User user = userRepository.findById(checkInRequestDto.getUserId())
                .orElseThrow(() -> new NotFoundException(NO_USER_ERROR_MESSAGE));
        ShopTable table = shopTableRepository.findById(checkInRequestDto.getTableId())
                .orElseThrow(() -> new NotFoundException(NO_TABLE_ERROR_MESSAGE));
        Shop shop = table.getOptionalShop()
                .orElseThrow(() -> new NotFoundException(NO_SHOP_ERROR_MESSAGE));
        table.use();
        visitRepository.save(checkInRequestDto.toEntity(user, shop));
        return new Message(CHECKIN_SUCCESS_MESSAGE);
    }
}
