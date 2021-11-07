package com.minionz.backend.user.service;

import com.minionz.backend.common.domain.Address;
import com.minionz.backend.common.domain.Message;
import com.minionz.backend.common.exception.BadRequestException;
import com.minionz.backend.common.exception.NotEqualsException;
import com.minionz.backend.common.exception.NotFoundException;
import com.minionz.backend.shop.controller.dto.CommonShopResponseDto;
import com.minionz.backend.shop.controller.dto.ShopRequestDto;
import com.minionz.backend.shop.controller.dto.ShopSaveResponseDto;
import com.minionz.backend.shop.controller.dto.ShopTableRequestDto;
import com.minionz.backend.shop.domain.ShopRepository;
import com.minionz.backend.shop.service.ShopService;
import com.minionz.backend.user.controller.dto.*;
import com.minionz.backend.user.domain.*;
import com.minionz.backend.visit.controller.dto.CheckInRequestDto;
import com.minionz.backend.visit.domain.VisitRepository;
import com.minionz.backend.visit.service.VisitService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@WebAppConfiguration
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private BookmarkRepository bookmarkRepository;

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private VisitRepository visitRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ShopService shopService;

    @Autowired
    private VisitService visitService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @AfterEach
    void cleanUp() {
        visitRepository.deleteAll();
        userRepository.deleteAll();
        ownerRepository.deleteAll();
        bookmarkRepository.deleteAll();
        shopRepository.deleteAll();
    }

    @Test
    void 회원가입_성공_테스트_유저() {
        //given
        final Address address = new Address("안산시", "성포동", "우리집", 1.0, 2.0);
        JoinRequestDto joinRequestDto = JoinRequestDto.builder()
                .name("정재욱")
                .email("operation@naver.com")
                .nickName("라이언")
                .telNumber("11111111")
                .password("1234")
                .address(address)
                .build();
        //when
        Message message = userService.signUp(joinRequestDto);
        //then
        assertThat(message.getMessage()).isEqualTo("회원가입 성공");
    }

    @Test
    void 회원가입_성공_테스트_오너() {
        //given
        Address address = new Address("안산시", "상록구", "성포동", 1.0, 2.0);
        JoinRequestDto joinRequestDto = JoinRequestDto.builder()
                .name("정재욱")
                .nickName("바보")
                .address(address)
                .email("operation@naver.com")
                .telNumber("11111111")
                .password("1234")
                .build();
        //when
        Message message = userService.signUp(joinRequestDto);
        //then
        assertThat(message.getMessage()).isEqualTo("회원가입 성공");
    }

    @Test
    void 회원탈퇴_성공_테스트_유저() {
        //given
        final Address address = new Address("안산시", "성포동", "우리집", 1.0, 2.0);
        User user = User.builder()
                .email("wodnr8462@naver.com")
                .name("정재욱")
                .password("1234")
                .nickName("라이언")
                .telNumber("1111111")
                .address(address)
                .build();
        User save = userRepository.save(user);
        //when
        Message message = userService.withdraw(save.getId());
        //then
        assertThat(message.getMessage()).isEqualTo("회원탈퇴 성공");
    }

    @Test
    void 회원중복_성공_테스트() {
        //given
        final Address address = new Address("안산시", "성포동", "우리집", 1.0, 2.0);
        User user = User.builder()
                .email("wodnr8462@naver.com")
                .name("정재욱")
                .password("1234")
                .nickName("라이언")
                .telNumber("1111111")
                .address(address)
                .build();
        userRepository.save(user);
        JoinRequestDto joinRequestDto = JoinRequestDto.builder()
                .name("정재욱")
                .email("wodnr8462@naver.com")
                .nickName("라이언")
                .telNumber("11111111")
                .password("1234")
                .address(address)
                .build();
        //when
        //then
        assertThatThrownBy(() -> userService.signUp(joinRequestDto)).isInstanceOf(BadRequestException.class)
                .hasMessage("해당 유저 이메일이 중복입니다.");
    }

    @Test
    void 로그인_성공_테스트_유저() {
        //given
        final Address address = new Address("안산시", "성포동", "우리집", 1.0, 2.0);
        JoinRequestDto joinRequestDto = JoinRequestDto.builder()
                .name("정재욱")
                .email("operation@naver.com")
                .nickName("라이언")
                .telNumber("11111111")
                .password("1234")
                .address(address)
                .build();
        userService.signUp(joinRequestDto);
        User findUser = userRepository.findByEmail("operation@naver.com")
                .orElseThrow(() -> new NotFoundException("회원가입 실패"));
        LoginRequestDto LoginRequestDto = new LoginRequestDto("operation@naver.com", "1234");
        //when
        LoginResponseDto login = userService.login(LoginRequestDto);
        //then
        assertThat(login.getId()).isEqualTo(findUser.getId());
    }

    @Test
    void 로그인_아이디_불일치_테스트() {
        //given
        Address address = new Address("안산시", "상록구", "성포동", 1.0, 2.0);
        User user = User.builder()
                .email("jhnj741@naver.com")
                .name("동현")
                .address(address)
                .password("123456")
                .nickName("donglee99")
                .telNumber("010111111111")
                .build();
        LoginRequestDto LoginRequestDto = new LoginRequestDto("jh3j741@naver.com", "123456");
        userRepository.save(user);
        //when
        //then
        assertThatThrownBy(() -> userService.login(LoginRequestDto))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("해당 유저가 존재하지 않습니다.");
    }

    @Test
    void 로그인_비밀번호_불일치_테스트() {
        //given
        Address address = new Address("안산시", "상록구", "성포동", 1.0, 2.0);
        User user = User.builder()
                .email("jhnj741@naver.com")
                .name("동현")
                .password("123456")
                .address(address)
                .nickName("donglee99")
                .telNumber("010111111111")
                .build();
        LoginRequestDto LoginRequestDto = new LoginRequestDto("jhnj741@naver.com", "12346");
        userRepository.save(user);
        //when
        //then
        assertThatThrownBy(() -> userService.login(LoginRequestDto))
                .isInstanceOf(NotEqualsException.class)
                .hasMessage("비밀번호가 일치하지 않습니다.");
    }

    @Test
    void 마이페이지_조회_성공() {
        //given
        Address address = new Address("믿음", "소망", "씨티", 1.0, 2.0);
        User user = User.builder()
                .email("jhnj741@naver.com")
                .name("동현")
                .password("123456")
                .nickName("donglee99")
                .telNumber("010111111111")
                .address(address)
                .build();
        User save = userRepository.save(user);
        //when
        UserPageResponseDto userPageResponseDto = userService.viewMyPage(save.getId());
        //then
        assertThat(userPageResponseDto.getNickname()).isEqualTo("donglee99");
        assertThat(userPageResponseDto.getTelNumber()).isEqualTo("010111111111");
    }

    @Test
    void 마이페이지_조회_실패() {
        //given
        Address address = new Address("안산시", "상록구", "성포동", 1.0, 2.0);
        User user = User.builder()
                .email("jhnj741@naver.com")
                .name("동현")
                .password("123456")
                .nickName("donglee99")
                .address(address)
                .telNumber("010111111111")
                .build();
        userRepository.save(user);
        //when
        //then
        assertThatThrownBy(() -> userService.viewMyPage(2L))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("해당 유저 이메일이 존재하지 않습니다.");
    }

    @DisplayName("패스워드 암호화 테스트")
    @Test
    void passwordEncode() {
        // given
        Address userAddress = new Address("안산시", "상록구", "월피동", 1.0, 2.0);
        Address address = Address.builder().zipcode("111-222").street("구월동").city("인천시 남동구").build();
        String rawPassword = "12345678";
        JoinRequestDto joinRequestDto = JoinRequestDto.builder()
                .email("jhnj741@naver.com")
                .password("123456t")
                .name("동현")
                .nickName("DongLee99")
                .telNumber("010-111-1111")
                .address(address)
                .build();
        userService.signUp(joinRequestDto);
        Owner owner = Owner.builder()
                .name("주인")
                .email("223@naver.com")
                .password("123")
                .telNumber("012030123")
                .build();
        Owner savedOwner = ownerRepository.save(owner);
        //when
        //then
        assertThatThrownBy(() -> userService.viewMyPage(2L))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("해당 유저 이메일이 존재하지 않습니다.");
    }

    @Test
    void ViewMyVisitList() {
        // given
        Address address = new Address("123-456", "송도동", "인천시 연수구", 1.0, 2.0);
        Address userAddress = new Address("456-789", "송도동", "인천시 연수구", 1.0, 2.0);
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
        User user = User.builder()
                .name("미니언")
                .email("minionz@naver.com")
                .password("1234")
                .nickName("미니언")
                .telNumber("010-1111-1111")
                .address(userAddress)
                .build();
        userRepository.save(user);
        //when
        ShopRequestDto shopRequestDto = new ShopRequestDto("name", address, "032-888-8888", list, savedOwner.getId());
        ShopSaveResponseDto shopSaveResponseDto = shopService.save(shopRequestDto);
        CheckInRequestDto checkInRequestDto = new CheckInRequestDto(user.getId(), shopSaveResponseDto.getId());
        visitService.checkIn(checkInRequestDto);
        UserPageResponseDto userPageResponseDtoList = userService.viewMyPage(user.getId());
        //then
        assertThat(userPageResponseDtoList.getUserVisitResponseList().get(0).getShopTelNumber()).isEqualTo("032-888-8888");
    }

    @DisplayName("즐겨찾기 추가 성공")
    @Test
    public void 즐겨찾기_추가_성공() {
        //given
        Address address = new Address("안산시", "상록구", "성포동", 1.0, 2.0);
        JoinRequestDto joinRequestDto = JoinRequestDto.builder()
                .email("jhnj741@naver.com")
                .password("123456t")
                .name("동현")
                .nickName("DongLee99")
                .telNumber("010-111-1111")
                .address(address)
                .build();
        userService.signUp(joinRequestDto);
        Owner owner = Owner.builder()
                .name("주인")
                .email("223@naver.com")
                .password("123")
                .telNumber("012030123")
                .build();
        Owner savedOwner = ownerRepository.save(owner);
        List<ShopTableRequestDto> list = new ArrayList<>();
        list.add(new ShopTableRequestDto(2));
        list.add(new ShopTableRequestDto(4));
        list.add(new ShopTableRequestDto(4));
        ShopRequestDto shopRequestDto = new ShopRequestDto("name", address, "032-888-8888", list, savedOwner.getId());
        ShopSaveResponseDto save = shopService.save(shopRequestDto);
        //when
        BookmarkRequestDto bookmarkRequestDto = new BookmarkRequestDto(1L, save.getId());
        Message message = userService.addBookmark(bookmarkRequestDto);
        //then
        assertThat(message.getMessage()).isEqualTo("즐겨찾기 추가 성공");
    }

    @DisplayName("즐겨찾기 추가 실패")
    @Test
    public void 즐겨찾기_추가_실패() {
        //given
        Address address = new Address("안산시", "상록구", "성포동", 1.0, 2.0);
        User user = User.builder()
                .email("jhnj741@naver.com")
                .password("123456t")
                .name("동현")
                .nickName("DongLee99")
                .telNumber("010-111-1111")
                .address(address)
                .build();
        Owner owner = Owner.builder()
                .name("주인")
                .email("223@naver.com")
                .password("123")
                .telNumber("012030123")
                .build();
        Owner savedOwner = ownerRepository.save(owner);
        List<ShopTableRequestDto> list = new ArrayList<>();
        list.add(new ShopTableRequestDto(2));
        list.add(new ShopTableRequestDto(4));
        list.add(new ShopTableRequestDto(4));
        ShopRequestDto shopRequestDto = new ShopRequestDto("name", address, "032-888-8888", list, savedOwner.getId());
        ShopSaveResponseDto save = shopService.save(shopRequestDto);
        BookmarkRequestDto bookmarkRequestDto = new BookmarkRequestDto(1L, save.getId());
        //when
        //then
        assertThatThrownBy(() -> userService.addBookmark(bookmarkRequestDto))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("해당 유저가 존재하지 않습니다.");
    }

    @DisplayName("즐겨찾기 삭제 성공")
    @Test
    public void 즐겨찾기_삭제_성공() {
        //given
        Address address = new Address("안산시", "상록구", "성포동", 1.0, 2.0);
        User user = User.builder()
                .email("jhnj741@naver.com")
                .password("123456t")
                .name("동현")
                .nickName("DongLee99")
                .telNumber("010-111-1111")
                .address(address)
                .build();
        User savedUser = userRepository.save(user);
        Owner owner = Owner.builder()
                .name("주인")
                .email("223@naver.com")
                .password("123")
                .telNumber("012030123")
                .build();
        Owner savedOwner = ownerRepository.save(owner);
        List<ShopTableRequestDto> list = new ArrayList<>();
        list.add(new ShopTableRequestDto(2));
        list.add(new ShopTableRequestDto(4));
        list.add(new ShopTableRequestDto(4));
        ShopRequestDto shopRequestDto = new ShopRequestDto("name", address, "032-888-8888", list, savedOwner.getId());
        ShopSaveResponseDto save = shopService.save(shopRequestDto);
        BookmarkRequestDto bookmarkRequestDto = new BookmarkRequestDto(savedUser.getId(), save.getId());
        userService.addBookmark(bookmarkRequestDto);
        //when
        Message message = userService.deleteBookmark(savedUser.getId(), save.getId());
        //then
        assertThat(message.getMessage()).isEqualTo("즐겨찾기 삭제 성공");
    }

    @DisplayName("즐겨찾기 삭제 실패")
    @Test
    public void 즐겨찾기_삭제_실패() {
        //given
        Address address = new Address("안산시", "상록구", "성포동", 1.0, 2.0);
        User user = User.builder()
                .email("jhnj741@naver.com")
                .password("123456t")
                .name("동현")
                .nickName("DongLee99")
                .telNumber("010-111-1111")
                .address(address)
                .build();
        User savedUser = userRepository.save(user);
        Owner owner = Owner.builder()
                .name("주인")
                .email("223@naver.com")
                .password("123")
                .telNumber("012030123")
                .build();
        Owner savedOwner = ownerRepository.save(owner);
        List<ShopTableRequestDto> list = new ArrayList<>();
        list.add(new ShopTableRequestDto(2));
        list.add(new ShopTableRequestDto(4));
        list.add(new ShopTableRequestDto(4));
        ShopRequestDto shopRequestDto = new ShopRequestDto("name", address, "032-888-8888", list, savedOwner.getId());
        ShopSaveResponseDto save = shopService.save(shopRequestDto);
        BookmarkRequestDto bookmarkRequestDto = new BookmarkRequestDto(savedUser.getId(), save.getId());
        userService.addBookmark(bookmarkRequestDto);
        //when
        //then
        assertThatThrownBy(() -> userService.deleteBookmark(1L, save.getId()))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("해당 유저가 존재하지 않습니다.");
    }

    @DisplayName("즐겨찾기 조회 성공")
    @Test
    public void 즐겨찾기_조회_성공() {
        //given
        Address address = new Address("안산시", "상록구", "성포동", 1.0, 2.0);
        User user = User.builder()
                .email("jhnj741@naver.com")
                .password("123456t")
                .name("동현")
                .nickName("DongLee99")
                .telNumber("010-111-1111")
                .address(address)
                .build();
        User savedUser = userRepository.save(user);
        Owner owner = Owner.builder()
                .name("주인")
                .email("223@naver.com")
                .password("123")
                .telNumber("012030123")
                .build();
        Owner savedOwner = ownerRepository.save(owner);
        List<ShopTableRequestDto> list = new ArrayList<>();
        list.add(new ShopTableRequestDto(2));
        list.add(new ShopTableRequestDto(4));
        list.add(new ShopTableRequestDto(4));
        ShopRequestDto shopRequestDto = new ShopRequestDto("맘스터치", address, "032-888-8888", list, savedOwner.getId());
        ShopSaveResponseDto save = shopService.save(shopRequestDto);
        BookmarkRequestDto bookmarkRequestDto = new BookmarkRequestDto(savedUser.getId(), save.getId());
        userService.addBookmark(bookmarkRequestDto);
        //when
        List<CommonShopResponseDto> commonShopResponseDtos = userService.viewMyBookmark(savedUser.getId());
        //then
        assertThat(commonShopResponseDtos.get(0).getName()).isEqualTo("맘스터치");
    }

    @DisplayName("즐겨찾기 조회 실패")
    @Test
    public void 즐겨찾기_조회_실패() {
        //given
        Address address = new Address("안산시", "상록구", "성포동", 1.0, 2.0);
        User user = User.builder()
                .email("jhnj741@naver.com")
                .password("123456t")
                .name("동현")
                .nickName("DongLee99")
                .telNumber("010-111-1111")
                .address(address)
                .build();
        User savedUser = userRepository.save(user);
        Owner owner = Owner.builder()
                .name("주인")
                .email("223@naver.com")
                .password("123")
                .telNumber("012030123")
                .build();
        Owner savedOwner = ownerRepository.save(owner);
        List<ShopTableRequestDto> list = new ArrayList<>();
        list.add(new ShopTableRequestDto(2));
        list.add(new ShopTableRequestDto(4));
        list.add(new ShopTableRequestDto(4));
        ShopRequestDto shopRequestDto = new ShopRequestDto("맘스터치", address, "032-888-8888", list, savedOwner.getId());
        ShopSaveResponseDto save = shopService.save(shopRequestDto);
        BookmarkRequestDto bookmarkRequestDto = new BookmarkRequestDto(savedUser.getId(), save.getId());
        userService.addBookmark(bookmarkRequestDto);
        //when
        //then
        assertThatThrownBy(() -> userService.viewMyBookmark(2L))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("해당 유저가 존재하지 않습니다.");
    }
}
