package com.minionz.backend.shop.service;

import com.minionz.backend.common.domain.Address;
import com.minionz.backend.common.domain.Message;
import com.minionz.backend.shop.controller.dto.ShopRequestDto;
import com.minionz.backend.shop.controller.dto.ShopTableRequestDto;
import com.minionz.backend.shop.domain.ShopRepository;
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

    @AfterEach
    void cleanUp() {
        shopRepository.deleteAll();
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
        ShopRequestDto shopRequestDto = new ShopRequestDto("name", address, "032-888-8888", list);
        // when
        Message message = shopService.save(shopRequestDto);
        // then
        assertThat(message.getMessage()).isEqualTo("SHOP 등록 성공");
    }

    @DisplayName("Shop 생성 테스트")
    @Test
    public void saveShopTest1() {
        // given
        List<ShopTableRequestDto> list = new ArrayList<>();
        list.add(new ShopTableRequestDto(2));
        list.add(new ShopTableRequestDto(4));
        list.add(new ShopTableRequestDto(4));
        Address address = Address.builder().zipcode("111-222").street("구월동").city("인천시 남동구").build();
        ShopRequestDto shopRequestDto = new ShopRequestDto("name", address, "032-888-8888", list);
        // when
        Message message = shopService.save(shopRequestDto);
        // then
        assertThat(message.getMessage()).isEqualTo("SHOP 등록 성공");
    }
}
