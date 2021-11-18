package com.minionz.backend.shop.service;

import com.minionz.backend.common.domain.Address;
import com.minionz.backend.common.exception.NotFoundException;
import com.minionz.backend.shop.controller.dto.*;
import com.minionz.backend.shop.domain.Shop;
import com.minionz.backend.shop.domain.ShopRepository;
import com.minionz.backend.shop.domain.ShopTableRepository;
import com.minionz.backend.user.domain.Owner;
import com.minionz.backend.user.domain.OwnerRepository;
import com.minionz.backend.user.domain.User;
import com.minionz.backend.user.domain.UserRepository;
import com.minionz.backend.visit.controller.dto.CheckInRequestDto;
import com.minionz.backend.visit.service.VisitService;
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
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
    private ShopTableRepository shopTableRepository;

    @Autowired
    private VisitService visitService;

    @AfterEach
    void cleanUp() {
        shopRepository.deleteAll();
        userRepository.deleteAll();
        ownerRepository.deleteAll();
        shopTableRepository.deleteAll();
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
        Shop findShop = shopRepository.findById(shopSaveResponseDto.getId())
                .orElseThrow(() -> new NotFoundException("No shop"));
        CheckInRequestDto checkInRequestDto = new CheckInRequestDto(user.getId(), findShop.getTableList().get(0).getId());
        visitService.checkIn(checkInRequestDto);
        ShopDetailResponseDto shopDetailResponseDto = shopService.viewDetail(user.getId(), shopSaveResponseDto.getId());
        // then
        assertThat(shopDetailResponseDto.getMaxUser()).isEqualTo(17);
        assertThat(shopDetailResponseDto.getUseUser()).isEqualTo(1);
    }

    @DisplayName("매장 검색 성공")
    @Test
    void 매장검색_성공() {
        //given
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
        ShopRequestDto shopRequestDto = new ShopRequestDto("맘스터치", address, "032-888-8888", list, savedOwner.getId());
        shopService.save(shopRequestDto);
        // when
        List<CommonShopResponseDto> commonShopResponseDtos = shopService.searchShop("맘스터치");
        // then
        assertThat(commonShopResponseDtos.get(0).getName()).contains("맘스터치");
    }

    @DisplayName("매장 검색 실패")
    @Test
    void 매장검색_실패() {
        //given
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
        ShopRequestDto shopRequestDto = new ShopRequestDto("맘스터치", address, "032-888-8888", list, savedOwner.getId());
        shopService.save(shopRequestDto);
        // when
        // then
        assertThatThrownBy(() -> shopService.searchShop("맘스치"))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("등록된 매장이 존재하지 않습니다.");
    }

    @DisplayName("매장 지역 + 키워드 검색 성공")
    @Test
    void 매장지역_키워드_검색_성공() {
        //given
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
        ShopRequestDto shopRequestDto = new ShopRequestDto("맘스터치", address, "032-888-8888", list, savedOwner.getId());
        shopService.save(shopRequestDto);
        // when
        List<CommonShopResponseDto> commonShopResponseDtos = shopService.searchShopByRegion("맘스터치", "인천");
        // then
        assertThat(commonShopResponseDtos.get(0).getName()).contains("맘스터치");
    }

    @DisplayName("매장 지역 + 키워드 검색 실패")
    @Test
    void 매장지역_키워드_실패() {
        //given
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
        ShopRequestDto shopRequestDto = new ShopRequestDto("맘스터치", address, "032-888-8888", list, savedOwner.getId());
        shopService.save(shopRequestDto);
        // when
        // then
        assertThatThrownBy(() -> shopService.searchShopByRegion("맘스터치", "서울"))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("등록된 매장이 존재하지 않습니다.");
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
        ShopSaveResponseDto save = shopService.save(shopRequestDto);
        // when
        List<ShopTableResponseDto> shopTableResponseDtoList = shopService.viewTables(save.getId());
        // then
        assertThat(shopTableResponseDtoList.get(0).getTableNumber()).isEqualTo(1);
        assertThat(shopTableResponseDtoList.get(1).getTableNumber()).isEqualTo(2);
        assertThat(shopTableResponseDtoList.get(2).getTableNumber()).isEqualTo(3);
    }

    @DisplayName("근처매장_조회_성공")
    @Test
    void 근처매장_조회_성공() {
        List<ShopTableRequestDto> list1 = new ArrayList<>();
        list1.add(new ShopTableRequestDto(2));
        list1.add(new ShopTableRequestDto(4));
        list1.add(new ShopTableRequestDto(4));
        Address address1 = Address.builder().zipcode("111-222").street("구월동").city("인천시 남동구").latitude(37.515).longitude(126.940).build();
        List<ShopTableRequestDto> list2 = new ArrayList<>();
        list2.add(new ShopTableRequestDto(2));
        list2.add(new ShopTableRequestDto(4));
        list2.add(new ShopTableRequestDto(4));
        Address address2 = Address.builder().zipcode("111-222").street("구월동").city("인천시 남동구").latitude(37.518378).longitude(126.940).build();
        Owner owner = Owner.builder()
                .name("주인")
                .email("jhnj841@naba.com")
                .password("123")
                .telNumber("123123")
                .build();
        Owner savedOwner = ownerRepository.save(owner);
        ShopRequestDto shopRequestDto1 = new ShopRequestDto("맘스터치", address1, "032-888-8838", list1, savedOwner.getId());
        ShopRequestDto shopRequestDto2 = new ShopRequestDto("롯데리아", address2, "032-888-8888", list2, savedOwner.getId());
        // when
        shopService.save(shopRequestDto1);
        shopService.save(shopRequestDto2);
        // then
        List<NearShopResponseDto> nearShopResponseDtoList = shopService.nearShop("default", 37.515, 126.940);
        assertThat(nearShopResponseDtoList.get(0).getName()).isEqualTo("맘스터치");
    }

    @DisplayName("근처매장_조회_실패")
    @Test
    void 근처매장_조회_실패() {
        List<ShopTableRequestDto> list1 = new ArrayList<>();
        list1.add(new ShopTableRequestDto(2));
        list1.add(new ShopTableRequestDto(4));
        list1.add(new ShopTableRequestDto(4));
        Address address1 = Address.builder().zipcode("111-222").street("구월동").city("인천시 남동구").latitude(37.518378).longitude(126.940114).build();
        List<ShopTableRequestDto> list2 = new ArrayList<>();
        list2.add(new ShopTableRequestDto(2));
        list2.add(new ShopTableRequestDto(4));
        list2.add(new ShopTableRequestDto(4));
        Address address2 = Address.builder().zipcode("111-222").street("구월동").city("인천시 남동구").latitude(.518378).longitude(127.940114).build();
        Owner owner = Owner.builder()
                .name("주인")
                .email("jhnj841@naba.com")
                .password("123")
                .telNumber("123123")
                .build();
        Owner savedOwner = ownerRepository.save(owner);
        ShopRequestDto shopRequestDto1 = new ShopRequestDto("맘스터치", address1, "032-888-8838", list1, savedOwner.getId());
        ShopRequestDto shopRequestDto2 = new ShopRequestDto("롯데리아", address2, "032-888-8888", list2, savedOwner.getId());
        // when
        shopService.save(shopRequestDto1);
        shopService.save(shopRequestDto2);
        // then
        assertThatThrownBy(() -> shopService.nearShop("default", 40.515, 132.940))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("등록된 매장이 존재하지 않습니다.");
    }

    @DisplayName("근처매장_조회_혼잡도정렬_성공")
    @Test
    void 근처매장_조회_혼잡도정렬_성공() {
        Address userAddress = Address.builder().zipcode("111-222").street("구월동").city("인천시 남동구").build();
        User user = User.builder()
                .name("정재욱")
                .password("12345")
                .telNumber("010-9969-9776")
                .address(userAddress)
                .email("wodnr8462@naver.com")
                .nickName("김해룡축구장")
                .build();
        User savedUser = userRepository.save(user);
        List<ShopTableRequestDto> list1 = new ArrayList<>();
        list1.add(new ShopTableRequestDto(2));
        list1.add(new ShopTableRequestDto(4));
        list1.add(new ShopTableRequestDto(4));
        Address address1 = Address.builder().zipcode("111-222").street("구월동").city("인천시 남동구").latitude(37.518378).longitude(126.940114).build();
        List<ShopTableRequestDto> list2 = new ArrayList<>();
        list2.add(new ShopTableRequestDto(2));
        list2.add(new ShopTableRequestDto(4));
        list2.add(new ShopTableRequestDto(4));
        Address address2 = Address.builder().zipcode("111-222").street("구월동").city("인천시 남동구").latitude(37.518378).longitude(126.940114).build();
        Owner owner = Owner.builder()
                .name("주인")
                .email("jhnj841@naba.com")
                .password("123")
                .telNumber("123123")
                .build();
        Owner savedOwner = ownerRepository.save(owner);
        ShopRequestDto shopRequestDto1 = new ShopRequestDto("맘스터치", address1, "032-888-8838", list1, savedOwner.getId());
        ShopRequestDto shopRequestDto2 = new ShopRequestDto("롯데리아", address2, "032-888-8888", list2, savedOwner.getId());
        // when
        ShopSaveResponseDto savedShop = shopService.save(shopRequestDto1);
        shopService.save(shopRequestDto2);
        Shop shop = shopRepository.findById(savedShop.getId())
                .orElseThrow(() -> new NotFoundException("해당 매장 X"));
        visitService.checkIn(new CheckInRequestDto(savedUser.getId(), shop.getTableList().get(0).getId()));
        List<NearShopResponseDto> nearShopResponseDtoList = shopService.nearShop("congestion", 37.515, 126.940);
        // then
        assertThat(nearShopResponseDtoList.get(0).getUseTables()).isEqualTo(1);
    }

    @DisplayName("근처매장_조회_혼잡도정렬_실패")
    @Test
    void 근처매장_조회_혼잡도정렬_실패() {
        Address userAddress = Address.builder().zipcode("111-222").street("구월동").city("인천시 남동구").build();
        User user = User.builder()
                .name("정재욱")
                .password("12345")
                .telNumber("010-9969-9776")
                .address(userAddress)
                .email("wodnr8462@naver.com")
                .nickName("김해룡축구장")
                .build();
        User savedUser = userRepository.save(user);
        List<ShopTableRequestDto> list1 = new ArrayList<>();
        list1.add(new ShopTableRequestDto(2));
        list1.add(new ShopTableRequestDto(4));
        list1.add(new ShopTableRequestDto(4));
        Address address1 = Address.builder().zipcode("111-222").street("구월동").city("인천시 남동구").latitude(37.518378).longitude(126.940114).build();
        List<ShopTableRequestDto> list2 = new ArrayList<>();
        list2.add(new ShopTableRequestDto(2));
        list2.add(new ShopTableRequestDto(4));
        list2.add(new ShopTableRequestDto(4));
        Address address2 = Address.builder().zipcode("111-222").street("구월동").city("인천시 남동구").latitude(0.518378).longitude(126.940114).build();
        Owner owner = Owner.builder()
                .name("주인")
                .email("jhnj841@naba.com")
                .password("123")
                .telNumber("123123")
                .build();
        Owner savedOwner = ownerRepository.save(owner);
        ShopRequestDto shopRequestDto1 = new ShopRequestDto("맘스터치", address1, "032-888-8838", list1, savedOwner.getId());
        ShopRequestDto shopRequestDto2 = new ShopRequestDto("롯데리아", address2, "032-888-8888", list2, savedOwner.getId());
        // when
        ShopSaveResponseDto savedShop = shopService.save(shopRequestDto1);
        shopService.save(shopRequestDto2);

        Shop shop = shopRepository.findById(savedShop.getId()).orElseThrow(() -> new NotFoundException("해당 매장 X"));
        visitService.checkIn(new CheckInRequestDto(savedUser.getId(), shop.getTableList().get(0).getId()));
        // then
        assertThatThrownBy(() -> shopService.nearShop("congestion", 42.515, 135.940))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("등록된 매장이 존재하지 않습니다.");
    }
}
