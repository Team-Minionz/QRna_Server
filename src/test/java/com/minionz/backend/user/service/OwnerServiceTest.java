package com.minionz.backend.user.service;

import com.minionz.backend.common.domain.Address;
import com.minionz.backend.common.domain.Message;
import com.minionz.backend.common.exception.NotFoundException;
import com.minionz.backend.shop.controller.dto.ShopRequestDto;
import com.minionz.backend.shop.controller.dto.ShopTableRequestDto;
import com.minionz.backend.shop.service.ShopService;
import com.minionz.backend.user.controller.dto.JoinRequestDto;
import com.minionz.backend.user.controller.dto.LoginRequestDto;
import com.minionz.backend.user.controller.dto.LoginResponseDto;
import com.minionz.backend.user.controller.dto.OwnerShopResponseDto;
import com.minionz.backend.user.domain.Owner;
import com.minionz.backend.user.domain.OwnerRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@WebAppConfiguration
@ExtendWith(SpringExtension.class)
@SpringBootTest
class OwnerServiceTest {

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private OwnerService ownerService;

    @Autowired
    private ShopService shopService;

    @AfterEach
    void cleanUp() {
        ownerRepository.deleteAll();
    }

    @Test
    void 로그인_성공_테스트_오너() {
        //given
        JoinRequestDto joinRequestDto = JoinRequestDto.builder()
                .name("정재욱")
                .email("operation@naver.com")
                .telNumber("11111111")
                .password("1234")
                .build();
        ownerService.signUp(joinRequestDto);
        Owner findOwner = ownerRepository.findByEmail("operation@naver.com")
                .orElseThrow(() -> new NotFoundException("회원가입 실패"));
        LoginRequestDto LoginRequestDto = new LoginRequestDto("operation@naver.com", "1234");
        //when
        LoginResponseDto login = ownerService.login(LoginRequestDto);
        //then
        assertThat(login.getId()).isEqualTo(findOwner.getId());
    }

    @Test
    void 회원탈퇴_성공_테스트_오너() {
        //given
        Owner owner = Owner.builder()
                .email("wodnr8462@naver.com")
                .name("정재욱")
                .password("1234")
                .telNumber("1111111")
                .build();
        Owner save = ownerRepository.save(owner);
        //when
        Message message = ownerService.withdraw(save.getId());
        //then
        assertThat(message.getMessage()).isEqualTo("회원탈퇴 성공");
    }

    @Test
    void 오너샵조회_성공() {
        //given
        Address address = new Address("믿음", "소망", "씨티");
        List<ShopTableRequestDto> shopTables1 = new ArrayList<>();
        shopTables1.add(new ShopTableRequestDto(2));
        shopTables1.add(new ShopTableRequestDto(4));
        shopTables1.add(new ShopTableRequestDto(4));
        List<ShopTableRequestDto> shopTables2 = new ArrayList<>();
        shopTables2.add(new ShopTableRequestDto(4));
        shopTables2.add(new ShopTableRequestDto(4));
        shopTables2.add(new ShopTableRequestDto(4));
        Owner owner = Owner.builder()
                .name("주인")
                .email("jhnj841@naba.com")
                .password("123")
                .telNumber("123123")
                .build();
        Owner savedOwner = ownerRepository.save(owner);
        ShopRequestDto shopRequestDto1 = new ShopRequestDto("맘스터치1", address, "010-111-33332", shopTables1, savedOwner.getId());
        ShopRequestDto shopRequestDto2 = new ShopRequestDto("맘스터치2", address, "010-111-33333", shopTables2, savedOwner.getId());
        shopService.save(shopRequestDto1);
        shopService.save(shopRequestDto2);
        //when
        List<OwnerShopResponseDto> ownerShopResponseDtoList = ownerService.viewMyShop(savedOwner.getId());
        //then
        assertThat(ownerShopResponseDtoList.size()).isEqualTo(2);
    }

    @Test
    void 오너샵조회_실패() {
        //given
        Address address = new Address("믿음", "소망", "씨티");
        List<ShopTableRequestDto> shopTables1 = new ArrayList<>();
        shopTables1.add(new ShopTableRequestDto(2));
        shopTables1.add(new ShopTableRequestDto(4));
        shopTables1.add(new ShopTableRequestDto(4));
        List<ShopTableRequestDto> shopTables2 = new ArrayList<>();
        shopTables2.add(new ShopTableRequestDto(4));
        shopTables2.add(new ShopTableRequestDto(4));
        shopTables2.add(new ShopTableRequestDto(4));
        Owner owner = Owner.builder()
                .name("주인")
                .email("jhnj841@naba.com")
                .password("123")
                .telNumber("123123")
                .build();
        Owner savedOwner = ownerRepository.save(owner);
        ShopRequestDto shopRequestDto1 = new ShopRequestDto("맘스터치1", address, "010-111-33332", shopTables1, savedOwner.getId());
        ShopRequestDto shopRequestDto2 = new ShopRequestDto("맘스터치2", address, "010-111-33333", shopTables2, savedOwner.getId());
        shopService.save(shopRequestDto1);
        shopService.save(shopRequestDto2);
        //when
        //then
        assertThatThrownBy(() -> ownerService.viewMyShop(2L))
                .isInstanceOf(NotFoundException.class);
    }
}