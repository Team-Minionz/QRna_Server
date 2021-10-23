package com.minionz.backend.shop.domain;

import com.minionz.backend.common.domain.Address;
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
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ShopRepositoryTest {

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    @AfterEach
    void cleanUp() {
        shopRepository.deleteAll();
    }

    @DisplayName("테이블 번호 생성 테스트")
    @Test
    public void setTableNumberTest() {
        // given
        AtomicInteger expectTableNumber = new AtomicInteger(1);

        List<ShopTable> list = new ArrayList<>();
        list.add(ShopTable.builder().maxUser(2).build());
        list.add(ShopTable.builder().maxUser(4).build());
        list.add(ShopTable.builder().maxUser(4).build());
        Address address = Address.builder().zipcode("111-222").street("구월동").city("인천시 남동구").build();
        Owner owner = Owner.builder()
                .name("주인")
                .email("jhnj841@naba.com")
                .password("123")
                .telNumber("123123")
                .build();
        ownerRepository.save(owner);
        Shop shop = Shop.builder()
                .name("name")
                .telNumber("032-888-8888")
                .address(address)
                .tableList(list)
                .owner(owner)
                .build();
        shop.mapShopWithTable();
        // when
        shop.setTableNumber();
        // then
        shop.getTableList().forEach(s -> assertThat(s.getTableNumber()).isEqualTo(expectTableNumber.getAndIncrement()));
    }
}
