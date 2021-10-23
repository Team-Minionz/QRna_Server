package com.minionz.backend.shop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.minionz.backend.ApiDocument;
import com.minionz.backend.common.domain.Address;
import com.minionz.backend.common.domain.Message;
import com.minionz.backend.common.exception.BadRequestException;
import com.minionz.backend.common.exception.NotFoundException;
import com.minionz.backend.shop.controller.dto.ShopResponseDto;
import com.minionz.backend.shop.controller.dto.ShopRequestDto;
import com.minionz.backend.shop.controller.dto.ShopSaveResponseDto;
import com.minionz.backend.shop.controller.dto.ShopTableRequestDto;
import com.minionz.backend.shop.domain.CongestionStatus;
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
