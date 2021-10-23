package com.minionz.backend.visit.controller;

import com.minionz.backend.ApiDocument;
import com.minionz.backend.common.domain.Message;
import com.minionz.backend.common.exception.NotFoundException;
import com.minionz.backend.visit.controller.dto.CheckInRequestDto;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(VisitController.class)
class VisitControllerTest extends ApiDocument {

    @MockBean
    private VisitService visitService;

    @DisplayName("방문 기록 성공")
    @Test
    void checkIn() throws Exception {
        // given
        final CheckInRequestDto checkInRequestDto = CheckInRequestDto.builder()
                .userId(1L)
                .tableId(1L)
                .build();
        Message message = new Message("방문 기록 성공");
        // when
        willReturn(message).given(visitService).checkIn(any(CheckInRequestDto.class));
        final ResultActions resultActions = 방문_기록_요청(checkInRequestDto);
        // then
        방문_기록_성공(message, resultActions);
    }

    @DisplayName("방문 기록 실패")
    @Test
    void checkIn_fail() throws Exception {
        // given
        final CheckInRequestDto checkInRequestDto = CheckInRequestDto.builder()
                .userId(1L)
                .tableId(1L)
                .build();
        final Message errorMessage = new Message("방문 기록 실패");
        // when
        willThrow(new NotFoundException("방문 기록 실패")).given(visitService).checkIn(any(CheckInRequestDto.class));
        final ResultActions resultActions = 방문_기록_요청(checkInRequestDto);
        // then
        방문_기록_실패(errorMessage, resultActions);
    }

    private ResultActions 방문_기록_요청(CheckInRequestDto checkInRequestDto) throws Exception {
        return mockMvc.perform(post("/api/v1/visits")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(checkInRequestDto)));
    }

    private void 방문_기록_성공(Message message, ResultActions resultActions) throws Exception {
        resultActions.andExpect(status().isCreated())
                .andExpect(content().json(toJson(message)))
                .andDo(print())
                .andDo(toDocument("visit-checkin"));
    }

    private void 방문_기록_실패(Message errorMessage, ResultActions resultActions) throws Exception {
        resultActions.andExpect(status().isNotFound())
                .andExpect(content().json(toJson(errorMessage)))
                .andDo(print())
                .andDo(toDocument("visit-checkin-fail"));
    }
}
