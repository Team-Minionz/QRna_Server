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
import com.minionz.backend.user.service.OwnerService;
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

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.BDDMockito.willThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OwnerController.class)
@MockBean(JpaMetamodelMappingContext.class)
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
class OwnerControllerTest extends ApiDocument {

    @MockBean
    private OwnerService ownerService;

    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("오너 로그인 성공")
    @Test
    public void 오너로그인테스트_성공() throws Exception {
        Long id = 1L;
        final LoginRequestDto LoginRequestDto = new LoginRequestDto("email", "password");
        final LoginResponseDto loginResponseDto = new LoginResponseDto(id, new Message("로그인 성공"));
        willReturn(loginResponseDto).given(ownerService).login(any(LoginRequestDto.class));
        final ResultActions resultActions = 오너_로그인_요청(LoginRequestDto);
        오너_로그인_성공(loginResponseDto, resultActions);
    }

    @DisplayName("오너 로그인 실패")
    @Test
    public void 오너로그인테스트_실패() throws Exception {
        final LoginRequestDto LoginRequestDto = new LoginRequestDto("email1", "password");
        Message errorMessage = new Message("로그인 실패");
        willThrow(new NotFoundException("로그인 실패")).given(ownerService).login(any(LoginRequestDto.class));
        final ResultActions resultActions = 오너_로그인_요청(LoginRequestDto);
        오너_로그인_실패(errorMessage, resultActions);
    }

    @DisplayName("오너 로그아웃 성공")
    @Test
    public void 오너로그아웃테스트_성공() throws Exception {
        final Long id = 1L;
        Message message = new Message("로그아웃 성공");
        willReturn(message).given(ownerService).logout(any(Long.class));
        final ResultActions resultActions = 오너_로그아웃_요청(id);
        오너_로그아웃_성공(resultActions);
    }

    @DisplayName("오너 로그아웃 실패")
    @Test
    public void 오너로그아웃테스트_실패() throws Exception {
        final Long id = 1L;
        Message errorMessage = new Message("로그아웃 실패");
        willThrow(new NotFoundException("로그아웃 실패")).given(ownerService).logout(any(Long.class));
        final ResultActions resultActions = 오너_로그아웃_요청(id);
        오너_로그아웃_실패(errorMessage, resultActions);
    }

    @DisplayName("오너 회원가입 성공")
    @Test
    void 오너회원가입_성공() throws Exception {
        JoinRequestDto signUpRequest = JoinRequestDto.builder()
                .name("정재욱")
                .email("operation@naver.com")
                .telNumber("11111111")
                .password("1234")
                .build();
        Message message = new Message("회원가입 성공");
        willReturn(message).given(ownerService).signUp(any(JoinRequestDto.class));
        final ResultActions response = 오너_회원가입_요청(signUpRequest);
        오너_회원가입_성공(message, response);
    }

    @DisplayName("오너 회원가입 실패")
    @Test
    void 오너회원가입_실패() throws Exception {
        JoinRequestDto signUpRequest = JoinRequestDto.builder()
                .name("정재욱")
                .email("operation@naver.com")
                .telNumber("11111111")
                .password("1234")
                .build();
        Message errorMessage = new Message("회원가입 실패");
        willThrow(new BadRequestException("회원가입 실패")).given(ownerService).signUp(any(JoinRequestDto.class));
        final ResultActions response = 오너_회원가입_요청(signUpRequest);
        오너_회원가입_실패(errorMessage, response);
    }

    @DisplayName("오너 마이페이지 조회 성공")
    @Test
    void 오너마이페이지_조회_성공() throws Exception {
        Owner owner = Owner.builder()
                .telNumber("111")
                .name("주인")
                .build();
        Long id = 1L;
        OwnerPageResponseDto ownerPageResponseDto = new OwnerPageResponseDto(owner);
        willReturn(ownerPageResponseDto).given(ownerService).viewMyPage(any(Long.class));
        final ResultActions response = 오너_마이페이지_조회_요청(id);
        오너_마이페이지_성공(response, ownerPageResponseDto);
    }

    @DisplayName("오너 마이페이지 조회 실패")
    @Test
    void 오너마이페이지_조회_실패() throws Exception {
        Long id = 2L;
        Message errorMessage = new Message("마이페이지 조회 실패");
        willThrow(new NotFoundException("마이페이지 조회 실패")).given(ownerService).viewMyPage(any(Long.class));
        final ResultActions response = 오너_마이페이지_조회_요청(id);
        오너_마이페이지_실패(response, errorMessage);
    }

    @DisplayName("오너회원탈퇴 성공")
    @Test
    void 오너회원탈퇴_성공() throws Exception {
        final Long id = 1L;
        Message message = new Message("회원탈퇴 성공");
        willReturn(message).given(ownerService).withdraw(any(Long.class));
        final ResultActions response = 오너_회원탈퇴_요청(id);
        오너_회원탈퇴_성공(response);
    }

    @DisplayName("오너회원탈퇴 실패")
    @Test
    void 오너회원탈퇴_실패() throws Exception {
        final Long id = 1L;
        Message errorMessage = new Message("회원탈퇴 실패");
        willThrow(new NotFoundException("회원탈퇴 실패")).given(ownerService).withdraw(any(Long.class));
        final ResultActions response = 오너_회원탈퇴_요청(id);
        오너_회원탈퇴_실패(errorMessage, response);
    }

    @DisplayName("오너 샵 조회 성공")
    @Test
    void 오너샵조회_성공() throws Exception {
        List<OwnerShopResponseDto> ownerShopResponseDtoList = new ArrayList<>();
        List<ShopTable> shopTables = new ArrayList<>();
        Address address = new Address("인천시", "부평구", "산곡동", 1.0, 2.0);
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
                .id(1L)
                .name("가게")
                .address(address)
                .owner(owner)
                .telNumber("010-2222-1111")
                .tableList(shopTables)
                .build();
        ownerShopResponseDtoList.add(new OwnerShopResponseDto(shop));
        Long id = 1L;
        willReturn(ownerShopResponseDtoList).given(ownerService).viewMyShop(any(Long.class));
        ResultActions resultActions = 오너_샵조회_요청(id);
        오너_샵조회_성공(resultActions, ownerShopResponseDtoList);
    }

    @DisplayName("오너 샵 조회 실패")
    @Test
    void 오너샵조회_실패() throws Exception {
        Long id = 2L;
        Message errorMessage = new Message("해당 유저 이메일이 존재하지 않습니다.");
        willThrow(new NotFoundException("해당 유저 이메일이 존재하지 않습니다.")).given(ownerService).viewMyShop(any(Long.class));
        final ResultActions response = 오너_샵조회_요청(id);
        오너_샵조회_실패(response, errorMessage);
    }

    private ResultActions 오너_마이페이지_조회_요청(Long id) throws Exception {
        return mockMvc.perform(get("/api/v1/owners/page/" + id));
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
        return mockMvc.perform(get("/api/v1/owners/" + id));
    }

    private ResultActions 오너_회원가입_요청(JoinRequestDto signUpRequest) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/owners/join")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(signUpRequest)));
    }

    private ResultActions 오너_회원탈퇴_요청(Long id) throws Exception {
        return mockMvc.perform(delete("/api/v1/owners/withdraw/" + id));
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

    private ResultActions 오너_로그아웃_요청(Long id) throws Exception {
        return mockMvc.perform(get("/api/v1/owners/logout/" + id));
    }

    private void 오너_로그인_실패(Message errorMessage, ResultActions resultActions) throws Exception {
        resultActions.andExpect(status().isNotFound())
                .andExpect(content().json(toJson(errorMessage)))
                .andDo(print())
                .andDo(toDocument("owner-login-fail"));
    }

    private void 오너_마이페이지_성공(ResultActions response, OwnerPageResponseDto ownerPageResponseDto) throws Exception {
        response.andExpect(status().isOk())
                .andExpect(content().json(toJson(ownerPageResponseDto)))
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

    private ResultActions 오너_로그인_요청(LoginRequestDto LoginRequestDto) throws Exception {
        String content = objectMapper.writeValueAsString(LoginRequestDto);
        return mockMvc.perform(post("/api/v1/owners/login")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON));
    }
}