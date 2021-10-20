package com.minionz.backend.shop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.minionz.backend.ApiDocument;
import com.minionz.backend.common.domain.Address;
import com.minionz.backend.common.domain.Message;
import com.minionz.backend.common.exception.BadRequestException;
import com.minionz.backend.common.exception.NotFoundException;
import com.minionz.backend.shop.controller.dto.ShopRequestDto;
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
        Address address = new Address("인천시", "부평구", "산곡동");
        ShopRequestDto shopRequestDto = new ShopRequestDto("맥도날드", address, "032-123-1234");
        Message message = new Message("Shop 등록 성공");
        willReturn(message).given(shopService).save(any(ShopRequestDto.class));
        ResultActions resultActions = 상점등록_요청(shopRequestDto);
        상점등록요청_성공(message, resultActions);
    }

    @DisplayName("상점 등록 실패")
    @Test
    void 상점등록_실패() throws Exception {
        Address address = new Address("인천시", "부평구", "산곡동");
        ShopRequestDto shopRequestDto = new ShopRequestDto("맥도날드", address, "032-123-1234");
        Message message = new Message("Shop 등록 실패");
        willThrow(new BadRequestException("Shop 등록 실패")).given(shopService).save(any(ShopRequestDto.class));
        ResultActions resultActions = 상점등록_요청(shopRequestDto);
        상점등록요청_실패(message, resultActions);
    }

    @DisplayName("상점 update 성공")
    @Test
    void 상점수정_성공() throws Exception {
        Long id = 1L;
        Address address = new Address("인천시", "부평구", "산곡동");
        ShopRequestDto shopRequestDto = new ShopRequestDto("맥도날드", address, "032-123-1234");
        Message message = new Message("Shop 수정 성공");
        willReturn(message).given(shopService).update(any(Long.class) ,any(ShopRequestDto.class));
        ResultActions resultActions = 상점수정_요청(id, shopRequestDto);
        상점수정요청_성공(resultActions);
    }

    @DisplayName("상점 update 실패")
    @Test
    void 상점수정_실패() throws Exception {
        Long id = 1L;
        Address address = new Address("인천시", "부평구", "산곡동");
        ShopRequestDto shopRequestDto = new ShopRequestDto("맥도날드", address, "032-123-1234");
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

    private void 상점등록요청_성공(Message message, ResultActions resultActions) throws Exception {
        resultActions.andExpect(status().isCreated())
                .andExpect(content().json(toJson(message)))
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
        return mockMvc.perform(put("/api/v1/shops/" + id)
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
}