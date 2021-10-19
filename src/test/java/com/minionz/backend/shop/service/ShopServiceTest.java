package com.minionz.backend.shop.service;

import com.minionz.backend.common.exception.NotFoundException;
import com.minionz.backend.shop.controller.dto.ShopSaveRequestDto;
import com.minionz.backend.shop.domain.Shop;
import com.minionz.backend.shop.domain.ShopRepository;
import com.minionz.backend.shop.domain.ShopTable;
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
    private static ShopRepository shopRepository;

    @AfterEach
    void cleanUp() {
        shopRepository.deleteAll();
    }

    @DisplayName("Shop 생성 테스트")
    @Test
    public void makeTableListTest() {
        // given
        List<ShopTable> list = new ArrayList<>();
        list.add(ShopTable.builder().maxUser(2).build());
        list.add(ShopTable.builder().maxUser(4).build());
        list.add(ShopTable.builder().maxUser(4).build());
        ShopSaveRequestDto shopSaveRequestDto = new ShopSaveRequestDto("테스트", "442-152", "구월동", "인천시 남동구", "032-888-8888", list);
        Shop shop = shopSaveRequestDto.toEntity();
        System.out.println(shop.getTableList().get(0).getShop().getId());
        shop.mapShopWithTable();
        shopRepository.save(shop);
        // when
        Shop findShop = shopRepository.findByTelNumber("032-888-8888")
                .orElseThrow(() -> new NotFoundException("해당 업체가 존재하지 않습니다"));
        // then
        assertThat(findShop.getName()).isEqualTo("테스트");
    }

    @DisplayName("Shop to ShopTable 매핑 테스트")
    @Test
    public void mapShopToShopTableTest() {
        // given
        List<ShopTable> list = new ArrayList<>();
        list.add(ShopTable.builder().maxUser(2).build());
        list.add(ShopTable.builder().maxUser(4).build());
        list.add(ShopTable.builder().maxUser(4).build());
        ShopSaveRequestDto shopSaveRequestDto = new ShopSaveRequestDto("테스트", "442-152", "구월동", "인천시 남동구", "032-888-8888", list);
        Shop shop = shopSaveRequestDto.toEntity();
        System.out.println(shop.getTableList().get(0).getShop().getId());
        shop.mapShopWithTable();
        // when
        shopRepository.save(shop);
        // then
        for (ShopTable shopTable : shop.getTableList()) {
            Shop shop1 = shopTable.getShop();
            assertThat(shop1.getId()).isEqualTo(shop.getId());
            System.out.println("shopTable.getId() = " + shopTable.getId());
        }
    }
}
