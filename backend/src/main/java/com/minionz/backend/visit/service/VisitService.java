package com.minionz.backend.visit.service;

import com.minionz.backend.shop.domain.Shop;
import com.minionz.backend.shop.domain.ShopRepository;
import com.minionz.backend.user.domain.User;
import com.minionz.backend.user.domain.UserRepository;
import com.minionz.backend.visit.controller.dto.CheckInRequestDto;
import com.minionz.backend.visit.controller.dto.CheckInResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class VisitService {

    private final UserRepository userRepository;
    private final ShopRepository shopRepository;

    public CheckInResponseDto checkIn(CheckInRequestDto checkInRequestDto) {
        User user = userRepository.findByEmail(checkInRequestDto.getUserEmail());
        Shop shop = shopRepository.findByTelNumber(checkInRequestDto.getShopTelNumber());
        return new CheckInResponseDto(user.getId(), shop.getId());
    }
}
