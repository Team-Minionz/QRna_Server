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

@RequiredArgsConstructor
@Service
public class VisitService {

    private final UserRepository userRepository;
    private final ShopRepository shopRepository;
    private final VisitRepository visitRepository;

    @Transactional
    public CheckInResponseDto checkIn(CheckInRequestDto checkInRequestDto) {
        User user = userRepository.findByEmail(checkInRequestDto.getUserEmail());
        Shop shop = shopRepository.findByTelNumber(checkInRequestDto.getShopTelNumber());
        Visit visit = Visit.builder()
                .user(user)
                .shop(shop)
                .build();
        visitRepository.save(visit);
        return new CheckInResponseDto(user.getId(), shop.getId());
    }
}
