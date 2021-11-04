package com.minionz.backend.shop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.minionz.backend.ApiDocument;
import com.minionz.backend.common.domain.Address;
import com.minionz.backend.common.domain.Message;
import com.minionz.backend.common.exception.BadRequestException;
import com.minionz.backend.common.exception.NotFoundException;
import com.minionz.backend.shop.controller.dto.*;
import com.minionz.backend.shop.domain.CongestionStatus;
import com.minionz.backend.shop.domain.UseStatus;
import com.minionz.backend.shop.service.ShopService;
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

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.BDDMockito.willThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ShopController.class)
@MockBean(JpaMetamodelMappingContext.class)
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
class ShopControllerTest extends ApiDocument {

    @MockBean
    private ShopService shopService;

    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("상점 등록 성공")
    @Test
    void 상점등록_성공() throws Exception {
        Long id = 1L;
        List<ShopTableRequestDto> list = new ArrayList<>();
        list.add(new ShopTableRequestDto(2));
        list.add(new ShopTableRequestDto(4));
        list.add(new ShopTableRequestDto(4));
        Address address = Address.builder().zipcode("111-222").street("구월동").city("인천시 남동구").build();
        ShopRequestDto shopRequestDto = new ShopRequestDto("name", address, "032-888-8888", list, 1L);
        ShopSaveResponseDto shopSaveResponseDto = new ShopSaveResponseDto(id, new Message("상점 등록 성공"));
        willReturn(shopSaveResponseDto).given(shopService).save(any(ShopRequestDto.class));
        ResultActions resultActions = 상점등록_요청(shopRequestDto);
        상점등록요청_성공(shopSaveResponseDto, resultActions);
    }

    @DisplayName("상점 등록 실패")
    @Test
    void 상점등록_실패() throws Exception {
        List<ShopTableRequestDto> list = new ArrayList<>();
        list.add(new ShopTableRequestDto(2));
        list.add(new ShopTableRequestDto(4));
        list.add(new ShopTableRequestDto(4));
        Address address = Address.builder().zipcode("111-222").street("구월동").city("인천시 남동구").build();
        ShopRequestDto shopRequestDto = new ShopRequestDto("name", address, "032-888-8888", list, 1L);
        Message message = new Message("Shop 등록 실패");
        willThrow(new BadRequestException("Shop 등록 실패")).given(shopService).save(any(ShopRequestDto.class));
        ResultActions resultActions = 상점등록_요청(shopRequestDto);
        상점등록요청_실패(message, resultActions);
    }

    @DisplayName("상점 update 성공")
    @Test
    void 상점수정_성공() throws Exception {
        Long id = 1L;
        List<ShopTableRequestDto> list = new ArrayList<>();
        list.add(new ShopTableRequestDto(2));
        list.add(new ShopTableRequestDto(4));
        list.add(new ShopTableRequestDto(4));
        Address address = Address.builder().zipcode("111-222").street("구월동").city("인천시 남동구").build();
        willReturn(new Message("UPDATE 성공")).given(shopService).update(any(Long.class), any(ShopRequestDto.class));
        ShopRequestDto shopRequestDto = new ShopRequestDto("name", address, "032-888-8888", list, 1L);
        ResultActions resultActions = 상점수정_요청(id, shopRequestDto);
        상점수정요청_성공(resultActions);
    }

    @DisplayName("상점 update 실패")
    @Test
    void 상점수정_실패() throws Exception {
        Long id = 1L;
        List<ShopTableRequestDto> list = new ArrayList<>();
        list.add(new ShopTableRequestDto(2));
        list.add(new ShopTableRequestDto(4));
        list.add(new ShopTableRequestDto(4));
        Address address = Address.builder().zipcode("111-222").street("구월동").city("인천시 남동구").build();
        ShopRequestDto shopRequestDto = new ShopRequestDto("name", address, "032-888-8888", list, 1L);
        Message message = new Message("Shop 수정 실패");
        willThrow(new NotFoundException("Shop 수정 실패")).given(shopService).update(any(Long.class), any(ShopRequestDto.class));
        ResultActions resultActions = 상점수정_요청(id, shopRequestDto);
        상점수정요청_실패(message, resultActions);
    }

    @DisplayName("상점 delete 성공")
    @Test
    void 상점삭제_성공() throws Exception {
        Long id = 1L;
        Message message = new Message("Shop 삭제 성공");
        willReturn(message).given(shopService).delete(any(Long.class));
        ResultActions resultActions = 상점삭제_요청(id);
        상점삭제요청_성공(resultActions);
    }

    @DisplayName("상점 delete 실패")
    @Test
    void 상점삭제_실패() throws Exception {
        Long id = 1L;
        Message message = new Message("Shop 삭제 실패");
        willThrow(new NotFoundException("Shop 삭제 실패")).given(shopService).delete(any(Long.class));
        ResultActions resultActions = 상점삭제_요청(id);
        상점삭제요청_실패(message, resultActions);
    }

    @DisplayName("상점 목록 조회 성공")
    @Test
    void 상점목록조회_성공() throws Exception {
        List<ShopResponseDto> shopList = new ArrayList<>();
        shopList.add(new ShopResponseDto("매장1", CongestionStatus.SMOOTH));
        shopList.add(new ShopResponseDto("매장2", CongestionStatus.NORMAL));
        shopList.add(new ShopResponseDto("매장3", CongestionStatus.NORMAL));
        willReturn(shopList).given(shopService).viewAll();
        ResultActions resultActions = 상점목록조회_요청();
        상점목록조회요청_성공(resultActions, shopList);
    }

    @DisplayName("상점 목록 조회 실패")
    @Test
    void 상점목록조회_실패() throws Exception {
        List<ShopResponseDto> shopList = new ArrayList<>();
        shopList.add(new ShopResponseDto("매장1", CongestionStatus.SMOOTH));
        shopList.add(new ShopResponseDto("매장2", CongestionStatus.NORMAL));
        shopList.add(new ShopResponseDto("매장3", CongestionStatus.NORMAL));
        Message message = new Message("등록된 매장이 존재하지 않습니다.");
        willThrow(new NotFoundException("등록된 매장이 존재하지 않습니다.")).given(shopService).viewAll();
        ResultActions resultActions = 상점목록조회_요청();
        상점목록조회요청_실패(resultActions, message);
    }

    @DisplayName("상점 검색 성공")
    @Test
    void 상점검색_성공() throws Exception {
        Address address = new Address("인천시", "부평구", "산곡동", 1.0, 2.0);
        String query = "맘스터치";
        List<CommonShopResponseDto> shopResponseDtoList = new ArrayList<>();
        shopResponseDtoList.add(new CommonShopResponseDto(1L, "맘스터치1", address, CongestionStatus.NORMAL, 10, 5));
        shopResponseDtoList.add(new CommonShopResponseDto(2L, "맘스터치2", address, CongestionStatus.NORMAL, 11, 2));
        shopResponseDtoList.add(new CommonShopResponseDto(3L, "맘스터치3", address, CongestionStatus.NORMAL, 13, 4));
        willReturn(shopResponseDtoList).given(shopService).searchShop(any(String.class));
        ResultActions resultActions = 상점검색_요청(query);
        상점검색요청_성공(resultActions, shopResponseDtoList);
    }

    @DisplayName("상점 검색 실패")
    @Test
    void 상점검색_실패() throws Exception {
        String query = "맘스터치";
        Message message = new Message("등록된 매장이 존재하지 않습니다.");
        willThrow(new NotFoundException("등록된 매장이 존재하지 않습니다.")).given(shopService).searchShop(any(String.class));
        ResultActions resultActions = 상점검색_요청(query);
        상점검색요청_실패(message, resultActions);
    }

    @DisplayName("상점 지역검색 성공")
    @Test
    void 상점지역검색_성공() throws Exception {
        Address address = new Address("인천시", "부평구", "산곡동", 1.0, 2.0);
        String query = "맘스터치";
        String region = "경기도";
        List<CommonShopResponseDto> shopResponseDtoList = new ArrayList<>();
        shopResponseDtoList.add(new CommonShopResponseDto(1L, "맘스터치1", address, CongestionStatus.NORMAL, 10, 5));
        shopResponseDtoList.add(new CommonShopResponseDto(2L, "맘스터치2", address, CongestionStatus.NORMAL, 11, 2));
        shopResponseDtoList.add(new CommonShopResponseDto(3L, "맘스터치3", address, CongestionStatus.NORMAL, 13, 4));
        willReturn(shopResponseDtoList).given(shopService).searchShopByRegion(any(String.class), any(String.class));
        ResultActions resultActions = 상점지역검색_요청(query, region);
        상점지역검색요청_성공(resultActions, shopResponseDtoList);
    }

    @DisplayName("상점 지역검색 실패")
    @Test
    void 상점지역검색_실패() throws Exception {
        String query = "맘스터치";
        String region = "경기도";
        Message message = new Message("등록된 매장이 존재하지 않습니다.");
        willThrow(new NotFoundException("등록된 매장이 존재하지 않습니다.")).given(shopService).searchShopByRegion(any(String.class), any(String.class));
        ResultActions resultActions = 상점지역검색_요청(query, region);
        상점지역검색요청_실패(message, resultActions);
    }

    @DisplayName("유저 주변가게 조회 성공")
    @Test
    void 유저_주변가게_조회_성공() throws Exception {
        double x = 0.1;
        double y = 0.1;
        Address address = new Address("인천시", "부평구", "산곡동", 1.0, 2.0);
        List<CommonShopResponseDto> nearShopResponseDtoList = new ArrayList<>();
        nearShopResponseDtoList.add(new CommonShopResponseDto(1L, "맘스터치1", address, CongestionStatus.NORMAL, 10, 5));
        nearShopResponseDtoList.add(new CommonShopResponseDto(2L, "맘스터치2", address, CongestionStatus.NORMAL, 11, 2));
        nearShopResponseDtoList.add(new CommonShopResponseDto(3L, "맘스터치3", address, CongestionStatus.NORMAL, 13, 4));
        willReturn(nearShopResponseDtoList).given(shopService).nearShop(any(double.class), any(double.class));
        ResultActions resultActions = 유저_주변가게_조회_요청(x, y);
        유저_주변가게_조회_성공(resultActions, nearShopResponseDtoList);
    }

    @DisplayName("유저 주변가게 조회 실패")
    @Test
    void 유저_주변가게_조회_실패() throws Exception {
        double x = 0.1;
        double y = 0.1;
        Message errorMessage = new Message("해당 유저가 존재하지 않습니다.");
        willThrow(new NotFoundException("해당 유저가 존재하지 않습니다.")).given(shopService).nearShop(any(double.class), any(double.class));
        final ResultActions response = 유저_주변가게_조회_요청(x, y);
        유저_주변가게_조회_실패(response, errorMessage);
    }

    @DisplayName("매장 상세보기 조회 성공")
    @Test
    void 매장_상세보기_조회_성공() throws Exception {
        Long userId = 1L;
        Long shopId = 1L;
        String name = "BBQ";
        String telNumber = "010-6634-3435";
        Address address = Address.builder().zipcode("111-222").street("구월동").city("인천시 남동구").build();
        List<ShopTableCountResponseDto> list = new ArrayList<>();
        list.add(new ShopTableCountResponseDto(2, 2));
        list.add(new ShopTableCountResponseDto(3, 5));
        list.add(new ShopTableCountResponseDto(4, 7));
        ShopDetailResponseDto shopDetailResponseDto = new ShopDetailResponseDto(name, address, telNumber, list, 20, 47, CongestionStatus.SMOOTH, true);
        willReturn(shopDetailResponseDto).given(shopService).viewDetail(any(Long.class), any(Long.class));
        ResultActions resultActions = 유저_매장_상세보기_조회_요청(userId, shopId);
        유저_매장_상세보기_조회_성공(resultActions, shopDetailResponseDto);
    }

    @DisplayName("매장 상세보기 조회 실패")
    @Test
    void 매장_상세보기_조회_실패() throws Exception {
        Long userId = 1L;
        Long shopId = 1L;
        Message message = new Message("매장 상세보기 조회 실패");
        willThrow(new NotFoundException("매장 상세보기 조회 실패")).given(shopService).viewDetail(any(Long.class), any(Long.class));
        ResultActions resultActions = 유저_매장_상세보기_조회_요청(userId, shopId);
        유저_매장_상세보기_조회_실패(resultActions, message);
    }

    private ResultActions 유저_매장_상세보기_조회_요청(Long userId, Long shopId) throws Exception {
        return mockMvc.perform(get("/api/v1/shops/detail/" + shopId + "/" + userId));
    }

    private void 유저_매장_상세보기_조회_실패(ResultActions resultActions, Message message) throws Exception {
        resultActions.andExpect(status().isNotFound())
                .andExpect(content().json(toJson(message)))
                .andDo(print())
                .andDo(toDocument("shop-detail-fail"));
    }

    private void 유저_매장_상세보기_조회_성공(ResultActions resultActions, ShopDetailResponseDto shopDetailResponseDto) throws Exception {
        resultActions.andExpect(status().isOk())
                .andExpect(content().json(toJson(shopDetailResponseDto)))
                .andDo(print())
                .andDo(toDocument("shop-detail-success"));
    }

    private ResultActions 유저_주변가게_조회_요청(double x, double y) throws Exception {
        return mockMvc.perform(get("/api/v1/shops/near?x=" + x + "&y=" + y));
    }

    private void 유저_주변가게_조회_성공(ResultActions resultActions, List<CommonShopResponseDto> nearShopResponseDtoList) throws Exception {
        resultActions.andExpect(status().isOk())
                .andExpect(content().json(toJson(nearShopResponseDtoList)))
                .andDo(print())
                .andDo(toDocument("user-near-shop-success"));
    }

    private void 유저_주변가게_조회_실패(ResultActions resultActions, Message message) throws Exception {
        resultActions.andExpect(status().isNotFound())
                .andExpect(content().json(toJson(message)))
                .andDo(print())
                .andDo(toDocument("user-near-shop-fail"));
    }

    private ResultActions 상점지역검색_요청(String query, String region) throws Exception {
        return mockMvc.perform(get("/api/v1/shops/search/region?region=" + region + "&keyword=" + query)
                .characterEncoding("UTF-8"));
    }

    private void 상점지역검색요청_성공(ResultActions resultActions, List<CommonShopResponseDto> shopResponseDtoList) throws Exception {
        resultActions.andExpect(status().isOk())
                .andExpect(content().json(toJson(shopResponseDtoList)))
                .andDo(print())
                .andDo(toDocument("shop-search-region-success"));
    }

    private void 상점지역검색요청_실패(Message message, ResultActions resultActions) throws Exception {
        resultActions.andExpect(status().isNotFound())
                .andExpect(content().json(toJson(message)))
                .andDo(print())
                .andDo(toDocument("shop-search-region-fail"));
    }

    private void 상점검색요청_성공(ResultActions resultActions, List<CommonShopResponseDto> shopResponseDtoList) throws Exception {
        resultActions.andExpect(status().isOk())
                .andExpect(content().json(toJson(shopResponseDtoList)))
                .andDo(print())
                .andDo(toDocument("shop-search-success"));
    }

    private void 상점검색요청_실패(Message message, ResultActions resultActions) throws Exception {
        resultActions.andExpect(status().isNotFound())
                .andExpect(content().json(toJson(message)))
                .andDo(print())
                .andDo(toDocument("shop-search-fail"));
    }

    private ResultActions 상점검색_요청(String query) throws Exception {
        return mockMvc.perform(get("/api/v1/shops/search?keyword=" + query));
    }

    @DisplayName("테이블 목록 조회 성공")
    @Test
    void 테이블목록조회_성공() throws Exception {
        // given
        Long id = 1L;
        List<ShopTableResponseDto> list = new ArrayList<>();
        list.add(new ShopTableResponseDto(1L, 1, 2, 0, UseStatus.EMPTY));
        list.add(new ShopTableResponseDto(2L, 2, 2, 2, UseStatus.USING));
        list.add(new ShopTableResponseDto(3L, 3, 3, 0, UseStatus.EMPTY));
        // when
        willReturn(list).given(shopService).viewTables(any(Long.class));
        ResultActions resultActions = 테이블목록_조회_요청(id);
        // then
        테이블목록_조회_요청_성공(resultActions, list);
    }

    @DisplayName("테이블 목록 조회 실패")
    @Test
    void 테이블목록조회_실패() throws Exception {
        // given
        Long id = 1L;
        Message message = new Message("테이블 목록 조회 실패");
        // when
        willThrow(new NotFoundException("테이블 목록 조회 실패")).given(shopService).viewTables(any(Long.class));
        ResultActions resultActions = 테이블목록_조회_요청(id);
        // then
        테이블목록_조회_요청_실패(resultActions, message);
    }

    private ResultActions 테이블목록_조회_요청(Long id) throws Exception {
        return mockMvc.perform(get("/api/v1/shops/" + id));
    }

    private void 테이블목록_조회_요청_성공(ResultActions resultActions, List<ShopTableResponseDto> list) throws Exception {
        resultActions.andExpect(status().isOk())
                .andExpect(content().json(toJson(list)))
                .andDo(print())
                .andDo(toDocument("table-list-view-success"));
    }

    private void 테이블목록_조회_요청_실패(ResultActions resultActions, Message message) throws Exception {
        resultActions.andExpect(status().isNotFound())
                .andExpect(content().json(toJson(message)))
                .andDo(print())
                .andDo(toDocument("table-list-view-fail"));
    }

    private void 상점삭제요청_실패(Message message, ResultActions resultActions) throws Exception {
        resultActions.andExpect(status().isNotFound())
                .andExpect(content().json(toJson(message)))
                .andDo(print())
                .andDo(toDocument("shop-delete-fail"));
    }

    private void 상점수정요청_실패(Message message, ResultActions resultActions) throws Exception {
        resultActions.andExpect(status().isNotFound())
                .andExpect(content().json(toJson(message)))
                .andDo(print())
                .andDo(toDocument("shop-update-fail"));
    }

    private void 상점등록요청_실패(Message message, ResultActions resultActions) throws Exception {
        resultActions.andExpect(status().isBadRequest())
                .andExpect(content().json(toJson(message)))
                .andDo(print())
                .andDo(toDocument("shop-save-fail"));
    }

    private void 상점등록요청_성공(ShopSaveResponseDto shopSaveResponseDto, ResultActions resultActions) throws Exception {
        resultActions.andExpect(status().isCreated())
                .andExpect(content().json(toJson(shopSaveResponseDto)))
                .andDo(print())
                .andDo(toDocument("shop-save-success"));
    }

    private ResultActions 상점등록_요청(ShopRequestDto shopRequestDto) throws Exception {
        return mockMvc.perform(post("/api/v1/shops")
                .content(objectMapper.writeValueAsString(shopRequestDto))
                .contentType(MediaType.APPLICATION_JSON));
    }

    private void 상점수정요청_성공(ResultActions resultActions) throws Exception {
        resultActions.andExpect(status().isNoContent())
                .andDo(print())
                .andDo(toDocument("shop-update-success"));
    }

    private ResultActions 상점수정_요청(Long id, ShopRequestDto shopRequestDto) throws Exception {
        return mockMvc.perform(patch("/api/v1/shops/" + id)
                .content(objectMapper.writeValueAsString(shopRequestDto))
                .contentType(MediaType.APPLICATION_JSON));
    }

    private void 상점삭제요청_성공(ResultActions resultActions) throws Exception {
        resultActions.andExpect(status().isNoContent())
                .andDo(print())
                .andDo(toDocument("shop-delete-success"));
    }

    private ResultActions 상점삭제_요청(Long id) throws Exception {
        return mockMvc.perform(delete("/api/v1/shops/" + id)
                .contentType(MediaType.APPLICATION_JSON));
    }

    private ResultActions 상점목록조회_요청() throws Exception {
        return mockMvc.perform(get("/api/v1/shops"));
    }

    private void 상점목록조회요청_성공(ResultActions resultActions, List<ShopResponseDto> shopList) throws Exception {
        resultActions.andExpect(status().isOk())
                .andExpect(content().json(toJson(shopList)))
                .andDo(print())
                .andDo(toDocument("shop-all-view-success"));
    }

    private void 상점목록조회요청_실패(ResultActions resultActions, Message message) throws Exception {
        resultActions.andExpect(status().isNotFound())
                .andExpect(content().json(toJson(message)))
                .andDo(print())
                .andDo(toDocument("shop-all-view-fail"));
    }
}
