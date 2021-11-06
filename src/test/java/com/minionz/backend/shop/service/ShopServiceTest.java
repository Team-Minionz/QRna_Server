package com.minionz.backend.shop.service;

import com.minionz.backend.common.domain.Address;
import com.minionz.backend.common.exception.NotFoundException;
import com.minionz.backend.shop.controller.dto.ShopDetailResponseDto;
import com.minionz.backend.shop.controller.dto.ShopRequestDto;
import com.minionz.backend.shop.controller.dto.ShopSaveResponseDto;
import com.minionz.backend.shop.controller.dto.ShopTableRequestDto;
import com.minionz.backend.shop.domain.Shop;
import com.minionz.backend.shop.domain.ShopRepository;
import com.minionz.backend.user.domain.Owner;
import com.minionz.backend.user.domain.OwnerRepository;
import com.minionz.backend.user.domain.User;
import com.minionz.backend.user.domain.UserRepository;
import com.minionz.backend.user.service.UserService;
import com.minionz.backend.visit.controller.dto.CheckInRequestDto;
import com.minionz.backend.visit.service.VisitService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ShopServiceTest {

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private ShopService shopService;

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private VisitService visitService;

    @AfterEach
    void cleanUp() {
        shopRepository.deleteAll();
        ownerRepository.deleteAll();
    }

    @DisplayName("Shop 생성 테스트")
    @Test
    public void saveShopTest() {
        // given
        List<ShopTableRequestDto> list = new ArrayList<>();
        list.add(new ShopTableRequestDto(2));
        list.add(new ShopTableRequestDto(4));
        list.add(new ShopTableRequestDto(4));
        Address address = Address.builder().zipcode("111-222").street("구월동").city("인천시 남동구").build();
        Owner owner = Owner.builder()
                .name("주인")
                .email("jhnj841@naba.com")
                .password("123")
                .telNumber("123123")
                .build();
        Owner savedOwner = ownerRepository.save(owner);
        ShopRequestDto shopRequestDto = new ShopRequestDto("name", address, "032-888-8888", list, savedOwner.getId());
        // when
        ShopSaveResponseDto shopSaveResponseDto = shopService.save(shopRequestDto);
        Shop shop = shopRepository.findByTelNumber(shopRequestDto.getTelNumber())
                .orElseThrow(() -> new NotFoundException("매장 등록 실패"));
        // then
        assertThat(shopSaveResponseDto.getId()).isEqualTo(shop.getId());
    }

    @Transactional
    @DisplayName("Shop 상세보기 매장(공통) 테스트")
    @Test
    public void viewShopDetailTest() {
        // given
        List<ShopTableRequestDto> list = new ArrayList<>();
        list.add(new ShopTableRequestDto(2));
        list.add(new ShopTableRequestDto(4));
        list.add(new ShopTableRequestDto(5));
        list.add(new ShopTableRequestDto(6));
        Address address = Address.builder().zipcode("111-222").street("구월동").city("인천시 남동구").build();
        Address userAddress = Address.builder().zipcode("111-222").street("구월동").city("인천시 남동구").build();
        Owner owner = Owner.builder()
                .name("주인")
                .email("jhnj841@naba.com")
                .password("123")
                .telNumber("010-1234-1234")
                .build();
        Owner savedOwner = ownerRepository.save(owner);
        User user = User.builder()
                .name("정재욱")
                .password("12345")
                .telNumber("010-9969-9776")
                .address(userAddress)
                .email("wodnr8462@naver.com")
                .nickName("김해룡축구장")
                .build();
        userRepository.save(user);
        //when
        ShopRequestDto shopRequestDto = new ShopRequestDto("name", address, "032-888-8888", list, savedOwner.getId());
        ShopSaveResponseDto shopSaveResponseDto = shopService.save(shopRequestDto);
        CheckInRequestDto checkInRequestDto = new CheckInRequestDto(user.getId(), shopSaveResponseDto.getId());
        visitService.checkIn(checkInRequestDto);
        ShopDetailResponseDto shopDetailResponseDto = shopService.viewDetail(user.getId(), shopSaveResponseDto.getId());
        // then
        assertThat(shopDetailResponseDto.getMaxUser()).isEqualTo(17);
        assertThat(shopDetailResponseDto.getUseUser()).isEqualTo(1);
    }
}
