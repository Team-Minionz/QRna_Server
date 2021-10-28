package com.minionz.backend.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.minionz.backend.ApiDocument;
import com.minionz.backend.common.domain.Address;
import com.minionz.backend.common.domain.Message;
import com.minionz.backend.common.exception.BadRequestException;
import com.minionz.backend.common.exception.NotFoundException;
import com.minionz.backend.shop.domain.Shop;
import com.minionz.backend.shop.domain.ShopTable;
import com.minionz.backend.user.controller.dto.*;
import com.minionz.backend.user.domain.Owner;
import com.minionz.backend.user.domain.User;
import com.minionz.backend.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.BDDMockito.willThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@MockBean(JpaMetamodelMappingContext.class)
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
class UserControllerTest extends ApiDocument {

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("유저 로그인 성공")
    @Test
    public void 유저로그인테스트_성공() throws Exception {
        Long id = 1L;
        final LoginRequestDto LoginRequestDto = new LoginRequestDto("email", "password", Role.USER);
        final LoginResponseDto loginResponseDto = new LoginResponseDto(id, new Message("로그인 성공"));
        willReturn(loginResponseDto).given(userService).login(any(LoginRequestDto.class));
        final ResultActions resultActions = 유저_로그인_요청(LoginRequestDto);
        유저_로그인_성공(loginResponseDto, resultActions);
    }

    @DisplayName("유저 로그인 실패")
    @Test
    public void 유저로그인테스트_실패() throws Exception {
        final LoginRequestDto LoginRequestDto = new LoginRequestDto("email1", "password", Role.USER);
        Message errorMessage = new Message("로그인 실패");
        willThrow(new NotFoundException("로그인 실패")).given(userService).login(any(LoginRequestDto.class));
        final ResultActions resultActions = 유저_로그인_요청(LoginRequestDto);
        유저_로그인_실패(errorMessage, resultActions);
    }

    @DisplayName("오너 로그인 성공")
    @Test
    public void 오너로그인테스트_성공() throws Exception {
        Long id = 1L;
        final LoginRequestDto LoginRequestDto = new LoginRequestDto("email", "password", Role.OWNER);
        final LoginResponseDto loginResponseDto = new LoginResponseDto(id, new Message("로그인 성공"));
        willReturn(loginResponseDto).given(userService).login(any(LoginRequestDto.class));
        final ResultActions resultActions = 유저_로그인_요청(LoginRequestDto);
        오너_로그인_성공(loginResponseDto, resultActions);
    }

    @DisplayName("오너 로그인 실패")
    @Test
    public void 오너로그인테스트_실패() throws Exception {
        final LoginRequestDto LoginRequestDto = new LoginRequestDto("email1", "password", Role.OWNER);
        Message errorMessage = new Message("로그인 실패");
        willThrow(new NotFoundException("로그인 실패")).given(userService).login(any(LoginRequestDto.class));
        final ResultActions resultActions = 유저_로그인_요청(LoginRequestDto);
        오너_로그인_실패(errorMessage, resultActions);
    }

    @DisplayName("유저 로그아웃 성공")
    @Test
    public void 유저로그아웃테스트_성공() throws Exception {
        final Long id = 1L;
        Message message = new Message("로그아웃 성공");
        willReturn(message).given(userService).logout(any(Long.class), any(Role.class));
        final ResultActions resultActions = 유저_로그아웃_요청(id, Role.USER);
        유저_로그아웃_성공(resultActions);
    }

    @DisplayName("유저 로그아웃 실패")
    @Test
    public void 유저로그아웃테스트_실패() throws Exception {
        final Long id = 1L;
        Message errorMessage = new Message("로그아웃 실패");
        willThrow(new NotFoundException("로그아웃 실패")).given(userService).logout(any(Long.class), any(Role.class));
        final ResultActions resultActions = 유저_로그아웃_요청(id, Role.USER);
        유저_로그아웃_실패(errorMessage, resultActions);
    }

    @DisplayName("오너 로그아웃 성공")
    @Test
    public void 오너로그아웃테스트_성공() throws Exception {
        final Long id = 1L;
        Message message = new Message("로그아웃 성공");
        willReturn(message).given(userService).logout(any(Long.class), any(Role.class));
        final ResultActions resultActions = 유저_로그아웃_요청(id, Role.OWNER);
        오너_로그아웃_성공(resultActions);
    }

    @DisplayName("오너 로그아웃 실패")
    @Test
    public void 오너로그아웃테스트_실패() throws Exception {
        final Long id = 1L;
        Message errorMessage = new Message("로그아웃 실패");
        willThrow(new NotFoundException("로그아웃 실패")).given(userService).logout(any(Long.class), any(Role.class));
        final ResultActions resultActions = 유저_로그아웃_요청(id, Role.OWNER);
        오너_로그아웃_실패(errorMessage, resultActions);
    }

    @DisplayName("유저 회원가입 성공")
    @Test
    void 유저회원가입_성공() throws Exception {
        Address address = new Address("안산시", "상록구", "성포동");
        JoinRequestDto signUpRequest = JoinRequestDto.builder()
                .name("정재욱")
                .email("operation@naver.com")
                .nickName("라이언")
                .telNumber("11111111")
                .password("1234")
                .address(address)
                .role(Role.USER)
                .build();
        Message message = new Message("회원가입 성공");
        willReturn(message).given(userService).signUp(any(JoinRequestDto.class));
        final ResultActions response = 유저_회원가입_요청(signUpRequest);
        유저_회원가입_성공(message, response);
    }

    @DisplayName("유저 회원가입 실패")
    @Test
    void 유저회원가입_실패() throws Exception {
        final Address address = new Address("안산시", "상록구", "성포동");
        JoinRequestDto signUpRequest = JoinRequestDto.builder()
                .name("정재욱")
                .email("operation@naver.com")
                .nickName("라이언")
                .telNumber("11111111")
                .password("1234")
                .address(address)
                .role(Role.USER)
                .build();
        Message errorMessage = new Message("회원가입 실패");
        willThrow(new BadRequestException("회원가입 실패")).given(userService).signUp(any(JoinRequestDto.class));
        final ResultActions response = 유저_회원가입_요청(signUpRequest);
        유저_회원가입_실패(errorMessage, response);
    }

    @DisplayName("오너 회원가입 성공")
    @Test
    void 오너회원가입_성공() throws Exception {
        Address address = new Address("안산시", "상록구", "성포동");
        JoinRequestDto signUpRequest = JoinRequestDto.builder()
                .name("정재욱")
                .email("operation@naver.com")
                .telNumber("11111111")
                .password("1234")
                .role(Role.OWNER)
                .build();
        Message message = new Message("회원가입 성공");
        willReturn(message).given(userService).signUp(any(JoinRequestDto.class));
        final ResultActions response = 유저_회원가입_요청(signUpRequest);
        오너_회원가입_성공(message, response);
    }

    @DisplayName("오너 회원가입 실패")
    @Test
    void 오너회원가입_실패() throws Exception {
        final Address address = new Address("안산시", "상록구", "성포동");
        JoinRequestDto signUpRequest = JoinRequestDto.builder()
                .name("정재욱")
                .email("operation@naver.com")
                .telNumber("11111111")
                .password("1234")
                .role(Role.OWNER)
                .build();
        Message errorMessage = new Message("회원가입 실패");
        willThrow(new BadRequestException("회원가입 실패")).given(userService).signUp(any(JoinRequestDto.class));
        final ResultActions response = 유저_회원가입_요청(signUpRequest);
        오너_회원가입_실패(errorMessage, response);
    }

    @DisplayName("유저회원탈퇴 성공")
    @Test
    void 유저회원탈퇴_성공() throws Exception {
        final Long id = 1L;
        Message message = new Message("회원탈퇴 성공");
        willReturn(message).given(userService).withdraw(any(Long.class), any(Role.class));
        final ResultActions response = 유저_회원탈퇴_요청(id, Role.USER);
        유저_회원탈퇴_성공(response);
    }

    @DisplayName("유저회원탈퇴 실패")
    @Test
    void 유저회원탈퇴_실패() throws Exception {
        final Long id = 1L;
        Message errorMessage = new Message("회원탈퇴 실패");
        willThrow(new NotFoundException("회원탈퇴 실패")).given(userService).withdraw(any(Long.class), any(Role.class));
        final ResultActions response = 유저_회원탈퇴_요청(id, Role.USER);
        유저_회원탈퇴_실패(errorMessage, response);
    }

    @DisplayName("유저 마이페이지 조회 성공")
    @Test
    void 유저마이페이지_조회_성공() throws Exception {
        Address address = new Address("a", "b", "C");
        User user = User.builder()
                .nickName("asd")
                .telNumber("111")
                .address(address)
                .build();
        Long id = 1L;
        UserPageResponseDto userPageResponseDto = new UserPageResponseDto(user);
        willReturn(userPageResponseDto).given(userService).viewMypage(any(Long.class), any(Role.class));
        final ResultActions response = 유저_마이페이지_요청(id, Role.USER);
        유저_마이페이지_성공(response, userPageResponseDto);
    }

    @DisplayName("유저 마이페이지 조회 실패")
    @Test
    void 유저마이페이지_조회_실패() throws Exception {
        Long id = 2L;
        Message errorMessage = new Message("마이페이지 조회 실패");
        willThrow(new NotFoundException("마이페이지 조회 실패")).given(userService).viewMypage(any(Long.class), any(Role.class));
        final ResultActions response = 유저_마이페이지_요청(id, Role.USER);
        유저_마이페이지_실패(response, errorMessage);
    }

    @DisplayName("오너 마이페이지 조회 성공")
    @Test
    void 오너마이페이지_조회_성공() throws Exception {
        Owner owner = Owner.builder()
                .telNumber("111")
                .name("주인")
                .build();
        Long id = 1L;
        UserPageResponseDto userPageResponseDto = new UserPageResponseDto(owner);
        willReturn(userPageResponseDto).given(userService).viewMypage(any(Long.class), any(Role.class));
        final ResultActions response = 유저_마이페이지_요청(id, Role.OWNER);
        오너_마이페이지_성공(response, userPageResponseDto);
    }

    @DisplayName("오너 마이페이지 조회 실패")
    @Test
    void 오너마이페이지_조회_실패() throws Exception {
        Long id = 2L;
        Message errorMessage = new Message("마이페이지 조회 실패");
        willThrow(new NotFoundException("마이페이지 조회 실패")).given(userService).viewMypage(any(Long.class), any(Role.class));
        final ResultActions response = 유저_마이페이지_요청(id, Role.OWNER);
        오너_마이페이지_실패(response, errorMessage);
    }

    @DisplayName("오너회원탈퇴 성공")
    @Test
    void 오너회원탈퇴_성공() throws Exception {
        final Long id = 1L;
        Message message = new Message("회원탈퇴 성공");
        willReturn(message).given(userService).withdraw(any(Long.class), any(Role.class));
        final ResultActions response = 유저_회원탈퇴_요청(id, Role.OWNER);
        오너_회원탈퇴_성공(response);
    }

    @DisplayName("오너회원탈퇴 실패")
    @Test
    void 오너회원탈퇴_실패() throws Exception {
        final Long id = 1L;
        Message errorMessage = new Message("회원탈퇴 실패");
        willThrow(new NotFoundException("회원탈퇴 실패")).given(userService).withdraw(any(Long.class), any(Role.class));
        final ResultActions response = 유저_회원탈퇴_요청(id, Role.OWNER);
        오너_회원탈퇴_실패(errorMessage, response);
    }

    @DisplayName("오너 샵 조회 성공")
    @Test
    void 오너샵조회_성공() throws Exception {
        List<OwnerShopResponseDto> ownerShopResponseDtoList = new ArrayList<>();
        List<ShopTable> shopTables = new ArrayList<>();
        Address address = new Address("인천시", "부평구", "산곡동");
        Owner owner = Owner.builder()
                .email("hjhj@naver.com")
                .password("123")
                .telNumber("123-123-123")
                .name("사장")
                .build();
        shopTables.add(ShopTable.builder()
                .maxUser(3)
                .tableNumber(1)
                .build());
        shopTables.add(ShopTable.builder()
                .maxUser(3)
                .tableNumber(1)
                .build());
        Shop shop = Shop.builder()
                .name("가게")
                .address(address)
                .owner(owner)
                .telNumber("010-2222-1111")
                .tableList(shopTables)
                .build();
        ownerShopResponseDtoList.add(new OwnerShopResponseDto(shop));
        Long id = 1L;
        willReturn(ownerShopResponseDtoList).given(userService).viewMyShop(any(Long.class));
        ResultActions resultActions = 오너_샵조회_요청(id);
        오너_샵조회_성공(resultActions, ownerShopResponseDtoList);
    }

    @DisplayName("오너 샵 조회 실패")
    @Test
    void 오너샵조회_실패() throws Exception {
        Long id = 2L;
        Message errorMessage = new Message("해당 유저 이메일이 존재하지 않습니다.");
        willThrow(new NotFoundException("해당 유저 이메일이 존재하지 않습니다.")).given(userService).viewMyShop(any(Long.class));
        final ResultActions response = 오너_샵조회_요청(id);
        오너_샵조회_실패(response, errorMessage);
    }

    @DisplayName("유저 방문매장 조회 성공")
    @Test
    void 방문매장_조회_성공() throws Exception {
        Long id = 1L;
        Address userAddress = new Address("안산시", "상록구", "월피동");
        Address address = Address.builder().zipcode("111-222").street("구월동").city("인천시 남동구").build();
        List<ShopTable> shopTables = new ArrayList<>();
        LocalDateTime visitedDate = LocalDateTime.now();
        Owner owner = Owner.builder()
                .email("hjhj@naver.com")
                .password("123")
                .telNumber("123-123-123")
                .name("사장")
                .build();
        shopTables.add(ShopTable.builder()
                .maxUser(3)
                .tableNumber(1)
                .build());
        shopTables.add(ShopTable.builder()
                .maxUser(3)
                .tableNumber(1)
                .build());
        Shop shop = Shop.builder()
                .name("가게")
                .address(address)
                .owner(owner)
                .telNumber("010-2222-1111")
                .tableList(shopTables)
                .build();
        User user = User.builder()
                .name("정재욱")
                .address(userAddress)
                .telNumber("010-2242-5567")
                .build();
        List<UserVisitResponse> userVisitResponseList = new ArrayList<>();
        userVisitResponseList.add(new UserVisitResponse(user, shop, visitedDate));
        willReturn(userVisitResponseList).given(userService).visitMyshop(any(Long.class));
        ResultActions resultActions = 유저_방문매장_조회_요청(id);
        유저_방문매장_조회_성공(resultActions, userVisitResponseList);
    }

    @DisplayName("유저 방문매장 조회 실패")
    @Test
    void 방문매장_조회_실패() throws Exception {
        Long id = 1L;
        Message errorMessage = new Message("해당 유저가 존재하지 않습니다.");
        willThrow(new NotFoundException("해당 유저가 존재하지 않습니다.")).given(userService).visitMyshop(any(Long.class));
        ResultActions resultActions = 유저_방문매장_조회_요청(id);
        유저_방문매장_조회_실패(resultActions, errorMessage);
    }

    private void 오너_샵조회_성공(ResultActions resultActions, List<OwnerShopResponseDto> ownerShopResponseDtoList) throws Exception {
        resultActions.andExpect(status().isOk())
                .andExpect(content().json(toJson(ownerShopResponseDtoList)))
                .andDo(print())
                .andDo(toDocument("owner-shop-view-success"));
    }

    private void 오너_샵조회_실패(ResultActions resultActions, Message message) throws Exception {
        resultActions.andExpect(status().isNotFound())
                .andExpect(content().json(toJson(message)))
                .andDo(print())
                .andDo(toDocument("owner-shop-view-fail"));
    }

    private ResultActions 오너_샵조회_요청(Long id) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users/shop/" + id));
    }

    private ResultActions 유저_회원가입_요청(JoinRequestDto signUpRequest) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users/join")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(signUpRequest)));
    }

    private ResultActions 유저_회원탈퇴_요청(Long id, Role role) throws Exception {
        return mockMvc.perform(delete("/api/v1/users/withdraw/" + id + "/" + role));
    }

    private void 유저_회원가입_성공(Message message, ResultActions response) throws Exception {
        response.andExpect(status().isCreated())
                .andExpect(content().json(toJson(message)))
                .andDo(print())
                .andDo(toDocument("user-signup-success"));
    }

    private void 유저_회원가입_실패(Message errorMessage, ResultActions response) throws Exception {
        response.andExpect(status().isBadRequest())
                .andExpect(content().json(toJson(errorMessage)))
                .andDo(print())
                .andDo(toDocument("user-signup-fail"));
    }

    private void 오너_회원가입_성공(Message message, ResultActions response) throws Exception {
        response.andExpect(status().isCreated())
                .andExpect(content().json(toJson(message)))
                .andDo(print())
                .andDo(toDocument("owner-signup-success"));
    }

    private void 오너_회원가입_실패(Message errorMessage, ResultActions response) throws Exception {
        response.andExpect(status().isBadRequest())
                .andExpect(content().json(toJson(errorMessage)))
                .andDo(print())
                .andDo(toDocument("owner-signup-fail"));
    }

    private void 유저_회원탈퇴_성공(ResultActions response) throws Exception {
        response.andExpect(status().isNoContent())
                .andDo(print())
                .andDo(toDocument("user-withdraw-success"));
    }

    private void 유저_회원탈퇴_실패(Message errorMessage, ResultActions response) throws Exception {
        response.andExpect(status().isNotFound())
                .andExpect(content().json(toJson(errorMessage)))
                .andDo(print())
                .andDo(toDocument("user-withdraw-fail"));
    }

    private void 오너_회원탈퇴_성공(ResultActions response) throws Exception {
        response.andExpect(status().isNoContent())
                .andDo(print())
                .andDo(toDocument("owner-withdraw-success"));
    }

    private void 오너_회원탈퇴_실패(Message errorMessage, ResultActions response) throws Exception {
        response.andExpect(status().isNotFound())
                .andExpect(content().json(toJson(errorMessage)))
                .andDo(print())
                .andDo(toDocument("owner-withdraw-fail"));
    }

    private void 유저_로그아웃_실패(Message errorMessage, ResultActions resultActions) throws Exception {
        resultActions.andExpect(status().isNotFound())
                .andExpect(content().json(toJson(errorMessage)))
                .andDo(print())
                .andDo(toDocument("user-logout-fail"));
    }

    private void 오너_로그아웃_성공(ResultActions resultActions) throws Exception {
        resultActions.andExpect(status().isNoContent())
                .andDo(print())
                .andDo(toDocument("owner-logout-success"));
    }

    private void 오너_로그아웃_실패(Message errorMessage, ResultActions resultActions) throws Exception {
        resultActions.andExpect(status().isNotFound())
                .andExpect(content().json(toJson(errorMessage)))
                .andDo(print())
                .andDo(toDocument("owner-logout-fail"));
    }

    private void 유저_로그아웃_성공(ResultActions resultActions) throws Exception {
        resultActions.andExpect(status().isNoContent())
                .andDo(print())
                .andDo(toDocument("user-logout-success"));
    }

    private ResultActions 유저_로그아웃_요청(Long id, Role role) throws Exception {
        return mockMvc.perform(get("/api/v1/users/logout/" + id + "/" + role));
    }

    private void 유저_로그인_실패(Message errorMessage, ResultActions resultActions) throws Exception {
        resultActions.andExpect(status().isNotFound())
                .andExpect(content().json(toJson(errorMessage)))
                .andDo(print())
                .andDo(toDocument("user-login-fail"));
    }

    private void 오너_로그인_실패(Message errorMessage, ResultActions resultActions) throws Exception {
        resultActions.andExpect(status().isNotFound())
                .andExpect(content().json(toJson(errorMessage)))
                .andDo(print())
                .andDo(toDocument("owner-login-fail"));
    }

    private void 유저_로그인_성공(LoginResponseDto loginResponseDto, ResultActions resultActions) throws Exception {
        resultActions.andExpect(status().isOk())
                .andExpect(content().json(toJson(loginResponseDto)))
                .andDo(print())
                .andDo(toDocument("user-login-success"));
    }

    private void 유저_마이페이지_성공(ResultActions response, UserPageResponseDto userPageResponseDto) throws Exception {
        response.andExpect(status().isOk())
                .andExpect(content().json(toJson(userPageResponseDto)))
                .andDo(print())
                .andDo(toDocument("user-view-page-success"));
    }

    private void 유저_마이페이지_실패(ResultActions response, Message errorMessage) throws Exception {
        response.andExpect(status().isNotFound())
                .andExpect(content().json(toJson(errorMessage)))
                .andDo(print())
                .andDo(toDocument("user-view-page-fail"));
    }

    private void 오너_마이페이지_성공(ResultActions response, UserPageResponseDto userPageResponseDto) throws Exception {
        response.andExpect(status().isOk())
                .andExpect(content().json(toJson(userPageResponseDto)))
                .andDo(print())
                .andDo(toDocument("owner-view-page-success"));
    }

    private void 오너_마이페이지_실패(ResultActions response, Message errorMessage) throws Exception {
        response.andExpect(status().isNotFound())
                .andExpect(content().json(toJson(errorMessage)))
                .andDo(print())
                .andDo(toDocument("owner-view-page-fail"));
    }

    private void 오너_로그인_성공(LoginResponseDto loginResponseDto, ResultActions resultActions) throws Exception {
        resultActions.andExpect(status().isOk())
                .andExpect(content().json(toJson(loginResponseDto)))
                .andDo(print())
                .andDo(toDocument("owner-login-success"));
    }

    private ResultActions 유저_로그인_요청(LoginRequestDto LoginRequestDto) throws Exception {
        String content = objectMapper.writeValueAsString(LoginRequestDto);
        return mockMvc.perform(post("/api/v1/users/login")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON));
    }

    private ResultActions 유저_마이페이지_요청(Long id, Role role) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users/page/" + id + "/" + role));
    }

    private void 유저_방문매장_조회_성공(ResultActions resultActions, List<UserVisitResponse> userVisitResponseList) throws Exception {
        resultActions.andExpect(status().isOk())
                .andExpect(content().json(toJson(userVisitResponseList)))
                .andDo(print())
                .andDo(toDocument("user-visit-shop-success"));
    }

    private ResultActions 유저_방문매장_조회_요청(Long id) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users/page/visit/" + id));
    }

    private void 유저_방문매장_조회_실패(ResultActions resultActions, Message message) throws Exception {
        resultActions.andExpect(status().isNotFound())
                .andExpect(content().json(toJson(message)))
                .andDo(print())
                .andDo(toDocument("user-visit-shop-fail"));
    }
}
