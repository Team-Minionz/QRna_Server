package com.minionz.backend.visit.controller;

import com.minionz.backend.ApiDocument;
import com.minionz.backend.common.exception.ErrorMessage;
import com.minionz.backend.common.exception.BadRequestException;
import com.minionz.backend.visit.controller.dto.CheckInRequestDto;
import com.minionz.backend.visit.controller.dto.CheckInResponseDto;
import com.minionz.backend.visit.service.VisitService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.BDDMockito.willThrow;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(VisitController.class)
class VisitControllerTest extends ApiDocument {

    @MockBean
    private VisitService visitService;

    @DisplayName("방문 기록 성공")
    @Test
    void checkIn() throws Exception {
        // given
        final CheckInRequestDto checkInRequestDto = CheckInRequestDto.builder()
                .userEmail("minion")
                .shopTelNumber("032-888-1111")
                .build();
        final CheckInResponseDto checkInResponseDto = new CheckInResponseDto(1L, 1L);
        // when
        willReturn(checkInResponseDto).given(visitService).checkIn(any(CheckInRequestDto.class));
        final ResultActions resultActions = 방문_기록_요청(checkInRequestDto);
        // then
        방문_기록_성공(checkInResponseDto, resultActions);
    }

    @DisplayName("방문 기록 실패")
    @Test
    void checkIn_fail() throws Exception {
        // given
        final CheckInRequestDto checkInRequestDto = CheckInRequestDto.builder()
                .userEmail("minion")
                .shopTelNumber("032-888-1111")
                .build();
        final ErrorMessage errorMessage = new ErrorMessage("check-in fail");
        // when
        willThrow(new BadRequestException("check-in fail")).given(visitService).checkIn(any(CheckInRequestDto.class));
        final ResultActions resultActions = 방문_기록_요청(checkInRequestDto);
        // then
        방문_기록_실패(errorMessage, resultActions);
    }

    private ResultActions 방문_기록_요청(CheckInRequestDto checkInRequestDto) throws Exception {
        return mockMvc.perform(post("/api/v1/visits")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(checkInRequestDto)));
    }

    private void 방문_기록_성공(CheckInResponseDto checkInResponseDto, ResultActions resultActions) throws Exception {
        resultActions.andExpect(status().isCreated())
                .andExpect(content().json(toJson(checkInResponseDto)))
                .andDo(print())
                .andDo(toDocument("visit-checkin"));
    }

    private void 방문_기록_실패(ErrorMessage errorMessage, ResultActions resultActions) throws Exception {
        resultActions.andExpect(status().isBadRequest())
                .andExpect(content().json(toJson(errorMessage)))
                .andDo(print())
                .andDo(toDocument("visit-checkin-fail"));
    }
}
