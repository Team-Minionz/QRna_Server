package com.minionz.backend.user.controller;

import com.minionz.backend.ApiDocument;
import com.minionz.backend.common.domain.Message;
import com.minionz.backend.common.exception.NotFoundException;
import com.minionz.backend.user.service.OwnerService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.BDDMockito.willThrow;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OwnerController.class)
public class OwnerControllerTest extends ApiDocument {

    @MockBean
    private OwnerService ownerService;

    @DisplayName("테이블 퇴장 성공")
    @Test
    public void 테이블퇴장테스트_성공() throws Exception {
        // given
        Long id = 1L;
        Message message = new Message("테이블 퇴장 성공");
        // when
        willReturn(message).given(ownerService).exitTable(any(Long.class));
        ResultActions resultActions = 테이블_퇴장_요청(id);
        // then
        테이블_퇴장_성공(resultActions, message);
    }

    @DisplayName("테이블 퇴장 실패")
    @Test
    public void 테이블퇴장테스트_실패() throws Exception {
        // given
        Long id = 1L;
        Message message = new Message("테이블 퇴장 실패");
        // when
        willThrow(new NotFoundException("테이블 퇴장 실패")).given(ownerService).exitTable(any(Long.class));
        ResultActions resultActions = 테이블_퇴장_요청(id);
        // then
        테이블_퇴장_실패(resultActions, message);
    }

    private ResultActions 테이블_퇴장_요청(Long id) throws Exception {
        return mockMvc.perform(get("/api/v1/owners/" + id));
    }

    private void 테이블_퇴장_성공(ResultActions resultActions, Message message) throws Exception {
        resultActions.andExpect(status().isOk())
                .andExpect(content().json(toJson(message)))
                .andDo(print())
                .andDo(toDocument("table-exit-success"));
    }

    private void 테이블_퇴장_실패(ResultActions resultActions, Message message) throws Exception {
        resultActions.andExpect(status().isNotFound())
                .andExpect(content().json(toJson(message)))
                .andDo(print())
                .andDo(toDocument("table-exit-fail"));
    }
}
