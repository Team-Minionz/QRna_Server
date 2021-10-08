package com.minionz.backend.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.minionz.backend.ApiDocument;
import com.minionz.backend.common.domain.Address;
import com.minionz.backend.common.domain.Message;
import com.minionz.backend.common.exception.NotFoundException;
import com.minionz.backend.user.controller.dto.UserJoinRequestDto;
import com.minionz.backend.user.controller.dto.UserLoginRequestDto;
import com.minionz.backend.user.controller.dto.UserRequestDto;
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

    @DisplayName("로그인 성공")
    @Test
    public void 유저로그인테스트_성공() throws Exception {
        final UserLoginRequestDto userLoginRequestDto = new UserLoginRequestDto("email", "password");
        Message message = new Message("로그인 성공");
        willReturn(message).given(userService).login(any(UserLoginRequestDto.class));
        final ResultActions resultActions = 유저_로그인_요청(userLoginRequestDto);
        유저_로그인_성공(message, resultActions);
    }

    @DisplayName("로그인 실패")
    @Test
    public void 유저로그인테스트_실패() throws Exception {
        final UserLoginRequestDto userLoginRequestDto = new UserLoginRequestDto("email1", "password");
        Message errorMessage = new Message("로그인 실패");
        willThrow(new NotFoundException("로그인 실패")).given(userService).login(any(UserLoginRequestDto.class));
        final ResultActions resultActions = 유저_로그인_요청(userLoginRequestDto);
        유저_로그인_실패(errorMessage, resultActions);
    }

    @DisplayName("로그아웃 성공")
    @Test
    public void 유저로그아웃테스트_성공() throws Exception {
        final UserRequestDto userRequestDto = new UserRequestDto("email");
        Message message = new Message("로그아웃 성공");
        willReturn(message).given(userService).logout(any(UserRequestDto.class));
        final ResultActions resultActions = 유저_로그아웃_요청(userRequestDto);
        유저_로그아웃_성공(message, resultActions);
    }

    @DisplayName("로그아웃 실패")
    @Test
    public void 유저로그아웃테스트_실패() throws Exception {
        final UserRequestDto userRequestDto = new UserRequestDto("email");
        Message errorMessage = new Message("로그아웃 실패");
        willThrow(new NotFoundException("로그아웃 실패")).given(userService).logout(any(UserRequestDto.class));
        final ResultActions resultActions = 유저_로그아웃_요청(userRequestDto);
        유저_로그아웃_실패(errorMessage, resultActions);
    }

    @DisplayName("회원가입 성공")
    @Test
    void user_sign_up_success() throws Exception {

        Address address = new Address("안산시", "상록구", "성포동");
        UserJoinRequestDto signUpRequest = new UserJoinRequestDto("정재욱", "wodnr@naver.com", "라이언", "010-9969-9776", "111", address);
        Message message = new Message("회원가입 성공");
        willReturn(message).given(userService).signUp(any(UserJoinRequestDto.class));
        final ResultActions response = 유저_회원가입_요청(signUpRequest);
        유저_회원가입_성공(message, response);
    }

    @DisplayName("회원가입 실패")
    @Test
    void user_sign_up_fail() throws Exception {
        final Address address = new Address("안산시", "상록구", "성포동");
        UserJoinRequestDto signUpRequest = new UserJoinRequestDto("정재욱", "wodnr@naver.com", "라이언", "010-9969-9776", "12345", address);
        Message errorMessage = new Message("회원가입 실패");
        willThrow(new NotFoundException("회원가입 실패")).given(userService).signUp(any(UserJoinRequestDto.class));
        final ResultActions response = 유저_회원가입_요청(signUpRequest);
        유저_회원가입_실패(errorMessage, response);
    }

    @DisplayName("회원탈퇴 성공")
    @Test
    void user_withdraw_success() throws Exception {
        final UserRequestDto userRequestDto = new UserRequestDto("email");
        Message message = new Message("회원가입 성공");
        willReturn(message).given(userService).withdraw(any(UserRequestDto.class));
        final ResultActions response = 유저_회원탈퇴_요청(userRequestDto);
        유저_회원탈퇴_성공(message, response);
    }

    @DisplayName("회원탈퇴 실패")
    @Test
    void user_withdraw_fail() throws Exception {
        final UserRequestDto userRequestDto = new UserRequestDto("email");
        Message errorMessage = new Message("회원탈퇴 실패");
        willThrow(new NotFoundException("회원탈퇴 실패")).given(userService).withdraw(any(UserRequestDto.class));
        final ResultActions response = 유저_회원탈퇴_요청(userRequestDto);
        유저_회원탈퇴_실패(errorMessage, response);
    }

    private ResultActions 유저_회원가입_요청(UserJoinRequestDto signUpRequest) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users/join")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(signUpRequest)));
    }

    private ResultActions 유저_회원탈퇴_요청(UserRequestDto userRequestDto) throws Exception {
        return mockMvc.perform(delete("/api/v1/users/withdraw/" + userRequestDto.getEmail())
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(userRequestDto)));
    }

    private void 유저_회원가입_성공(Message message, ResultActions response) throws Exception {
        response.andExpect(status().isOk())
                .andExpect(content().json(toJson(message)))
                .andDo(print())
                .andDo(toDocument("user-signup-success"));
    }

    private void 유저_회원가입_실패(Message errorMessage, ResultActions response) throws Exception {
        response.andExpect(status().isNotFound())
                .andExpect(content().json(toJson(errorMessage)))
                .andDo(print())
                .andDo(toDocument("user-signup-fail"));
    }

    private void 유저_회원탈퇴_성공(Message message, ResultActions response) throws Exception {
        response.andExpect(status().isNoContent())
                .andExpect(content().json(toJson(message)))
                .andDo(print())
                .andDo(toDocument("user-withdraw-success"));
    }

    private void 유저_회원탈퇴_실패(Message errorMessage, ResultActions response) throws Exception {
        response.andExpect(status().isNotFound())
                .andExpect(content().json(toJson(errorMessage)))
                .andDo(print())
                .andDo(toDocument("user-withdraw-fail"));
    }

    private void 유저_로그아웃_실패(Message errorMessage, ResultActions resultActions) throws Exception {
        resultActions.andExpect(status().isNotFound())
                .andExpect(content().json(toJson(errorMessage)))
                .andDo(print())
                .andDo(toDocument("user-logout-fail"));
    }

    private void 유저_로그아웃_성공(Message message, ResultActions resultActions) throws Exception {
        resultActions.andExpect(status().isNoContent())
                .andExpect(content().json(toJson(message)))
                .andDo(print())
                .andDo(toDocument("user-logout-success"));
    }

    private ResultActions 유저_로그아웃_요청(UserRequestDto userRequestDto) throws Exception {
        return mockMvc.perform(get("/api/v1/users/logout/" + userRequestDto.getEmail())
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(userRequestDto)));
    }

    private void 유저_로그인_실패(Message errorMessage, ResultActions resultActions) throws Exception {
        resultActions.andExpect(status().isNotFound())
                .andExpect(content().json(toJson(errorMessage)))
                .andDo(print())
                .andDo(toDocument("user-login-fail"));
    }

    private void 유저_로그인_성공(Message message, ResultActions resultActions) throws Exception {
        resultActions.andExpect(status().isOk())
                .andExpect(content().json(toJson(message)))
                .andDo(print())
                .andDo(toDocument("user-login-success"));
    }

    private ResultActions 유저_로그인_요청(UserLoginRequestDto userLoginRequestDto) throws Exception {
        String content = objectMapper.writeValueAsString(userLoginRequestDto);
        return mockMvc.perform(post("/api/v1/users/login")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON));
    }
}
