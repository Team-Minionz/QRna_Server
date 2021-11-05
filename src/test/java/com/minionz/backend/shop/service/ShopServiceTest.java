package com.minionz.backend.shop.service;

import com.minionz.backend.common.domain.Address;
import com.minionz.backend.common.exception.NotFoundException;
import com.minionz.backend.shop.controller.dto.ShopRequestDto;
import com.minionz.backend.shop.controller.dto.ShopSaveResponseDto;
import com.minionz.backend.shop.controller.dto.ShopTableRequestDto;
import com.minionz.backend.shop.controller.dto.ShopTableResponseDto;
import com.minionz.backend.shop.domain.Shop;
import com.minionz.backend.shop.domain.ShopRepository;
import com.minionz.backend.user.domain.Owner;
import com.minionz.backend.user.domain.OwnerRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

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

    @DisplayName("매장 테이블 리스트 조회 테스트")
    @Test
    void viewShopTablesTest() {
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
        shopService.save(shopRequestDto);
        // when
        List<ShopTableResponseDto> shopTableResponseDtoList = shopService.viewTables(1L);
        // then
        assertThat(shopTableResponseDtoList.get(0).getTableNumber()).isEqualTo(1);
        assertThat(shopTableResponseDtoList.get(1).getTableNumber()).isEqualTo(2);
        assertThat(shopTableResponseDtoList.get(2).getTableNumber()).isEqualTo(3);
    }
}
