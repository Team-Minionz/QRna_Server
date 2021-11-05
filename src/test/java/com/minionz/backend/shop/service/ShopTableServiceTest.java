package com.minionz.backend.shop.service;

import com.minionz.backend.common.domain.Address;
import com.minionz.backend.common.domain.Message;
import com.minionz.backend.shop.controller.dto.ShopRequestDto;
import com.minionz.backend.shop.controller.dto.ShopTableRequestDto;
import com.minionz.backend.shop.domain.ShopRepository;
import com.minionz.backend.shop.domain.ShopTableRepository;
import com.minionz.backend.user.domain.Owner;
import com.minionz.backend.user.domain.OwnerRepository;
import com.minionz.backend.user.domain.User;
import com.minionz.backend.user.domain.UserRepository;
import com.minionz.backend.visit.controller.dto.CheckInRequestDto;
import com.minionz.backend.visit.service.VisitService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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
public class ShopTableServiceTest {

    @Autowired
    private ShopTableRepository shopTableRepository;

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private ShopService shopService;

    @Autowired
    private ShopTableService shopTableService;

    @Autowired
    private VisitService visitService;

    @BeforeEach
    void setUp() {
        Address address = new Address("123-456", "송도동", "인천시 연수구", 1.0, 2.0);
        List<ShopTableRequestDto> list = new ArrayList<>();
        list.add(new ShopTableRequestDto(2));
        list.add(new ShopTableRequestDto(4));
        list.add(new ShopTableRequestDto(4));
        Owner owner = Owner.builder()
                .email("hjhj@naver.com")
                .password("123")
                .telNumber("123-123-123")
                .name("사장")
                .build();
        Owner savedOwner = ownerRepository.save(owner);
        ShopRequestDto shopRequestDto = new ShopRequestDto("name", address, "032-888-8888", list, savedOwner.getId());
        shopService.save(shopRequestDto);

        Address userAddress = new Address("456-789", "송도동", "인천시 연수구", 1.0, 2.0);
        User user = User.builder()
                .name("미니언")
                .email("minionz@naver.com")
                .password("1234")
                .nickName("미니언")
                .telNumber("010-1111-1111")
                .address(userAddress)
                .build();
        userRepository.save(user);

        User user2 = User.builder()
                .name("미니언1")
                .email("minionz1@naver.com")
                .password("1234")
                .nickName("미니언1")
                .telNumber("010-2222-2222")
                .address(userAddress)
                .build();
        userRepository.save(user2);
    }

    @AfterEach
    void cleanUp() {
        shopTableRepository.deleteAll();
        shopRepository.deleteAll();
        userRepository.deleteAll();
        ownerRepository.deleteAll();
    }

    @DisplayName("테이블 퇴장 성공 테스트")
    @Test
    void exitTableTest() {
        // given
        CheckInRequestDto checkInRequestDto = new CheckInRequestDto(1L, 1L);
        CheckInRequestDto checkInRequestDto1 = new CheckInRequestDto(2L, 2L);
        visitService.checkIn(checkInRequestDto);
        visitService.checkIn(checkInRequestDto1);
        // when
        Message message = shopTableService.exitTable(1L);
        // then
        assertThat(message.getMessage()).isEqualTo("테이블 퇴장 성공");
    }
}
