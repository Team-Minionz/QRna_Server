package com.minionz.backend.visit.service;

import com.minionz.backend.shop.domain.Shop;
import com.minionz.backend.shop.domain.ShopRepository;
import com.minionz.backend.user.domain.User;
import com.minionz.backend.user.domain.UserRepository;
import com.minionz.backend.visit.controller.dto.CheckInRequestDto;
import com.minionz.backend.visit.controller.dto.CheckInResponseDto;
import com.minionz.backend.visit.domain.Visit;
import com.minionz.backend.visit.domain.VisitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class VisitService {

    private static final String NO_USER_ERROR_MESSAGE = "해당 유저가 존재하지 않습니다.";
    private static final String NO_SHOP_ERROR_MESSAGE = "해당 업체가 존재하지 않습니다.";
    private final UserRepository userRepository;
    private final ShopRepository shopRepository;
    private final VisitRepository visitRepository;

    @Transactional
    public CheckInResponseDto checkIn(CheckInRequestDto checkInRequestDto) {
        User user = userRepository.findByEmail(checkInRequestDto.getUserEmail())
                .orElseThrow(() -> new NoSuchElementException(NO_USER_ERROR_MESSAGE));
        Shop shop = shopRepository.findByTelNumber(checkInRequestDto.getShopTelNumber())
                .orElseThrow(() -> new NoSuchElementException(NO_SHOP_ERROR_MESSAGE));
        Visit visit = checkInRequestDto.toEntity(user, shop);
        visitRepository.save(visit);
        return new CheckInResponseDto(user.getId(), shop.getId());
    }
}
