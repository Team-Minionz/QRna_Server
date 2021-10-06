package com.minionz.backend.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.minionz.backend.ApiDocument;
import com.minionz.backend.common.exception.ErrorMessage;
import com.minionz.backend.common.exception.NotFoundException;
import com.minionz.backend.user.controller.dto.*;
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

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
        UserLoginResponseDto userLoginResponseDto = new UserLoginResponseDto(User.builder().email("email").build());
        willReturn(userLoginResponseDto).given(userService).login(any(UserLoginRequestDto.class));
        final ResultActions resultActions = 유저_로그인_요청(userLoginRequestDto);
        유저_로그인_성공(userLoginResponseDto, resultActions);
    }

    @DisplayName("로그인 실패")
    @Test
    public void 유저로그인테스트_실패() throws Exception {
        final UserLoginRequestDto userLoginRequestDto = new UserLoginRequestDto("email1", "password");
        ErrorMessage errorMessage = new ErrorMessage("로그인 실패");
        willThrow(new NotFoundException("로그인 실패")).given(userService).login(any(UserLoginRequestDto.class));
        final ResultActions resultActions = 유저_로그인_요청(userLoginRequestDto);
        유저_로그인_실패(errorMessage, resultActions);
    }

    @DisplayName("로그아웃 성공")
    @Test
    public void 유저로그아웃테스트_성공() throws Exception {
        final String email = "email";
        UserLogoutResponseDto userLogoutResponseDto = new UserLogoutResponseDto(User.builder().email("email").build());
        willReturn(userLogoutResponseDto).given(userService).logout(email);
        final ResultActions resultActions = 유저_로그아웃_요청(email);
        유저_로그아웃_성공(userLogoutResponseDto, resultActions);
    }

    @DisplayName("로그아웃 실패")
    @Test
    public void 유저로그아웃테스트_실패() throws Exception {
        final String email = "email";
        ErrorMessage errorMessage = new ErrorMessage("로그아웃 실패");
        willThrow(new NotFoundException("로그아웃 실패")).given(userService).logout(email);
        final ResultActions resultActions = 유저_로그아웃_요청(email);
        유저_로그아웃_실패(errorMessage, resultActions);
    }

    private void 유저_로그아웃_실패(ErrorMessage errorMessage, ResultActions resultActions) throws Exception {
        resultActions.andExpect(status().isNotFound())
                .andExpect(content().json(toJson(errorMessage)))
                .andDo(print())
                .andDo(toDocument("user-logout-fail"));
    }

    private void 유저_로그아웃_성공(UserLogoutResponseDto userLogoutResponseDto, ResultActions resultActions) throws Exception {
        resultActions.andExpect(status().isNoContent())
                .andExpect(content().json(objectMapper.writeValueAsString(userLogoutResponseDto)))
                .andDo(print())
                .andDo(toDocument("user-logout-success"));
    }

    private ResultActions 유저_로그아웃_요청(String email) throws Exception {
        return mockMvc.perform(get("/api/v1/users/logout/" + email)
                .contentType(MediaType.APPLICATION_JSON));
    }

    private void 유저_로그인_실패(ErrorMessage errorMessage, ResultActions resultActions) throws Exception {
        resultActions.andExpect(status().isNotFound())
                .andExpect(content().json(toJson(errorMessage)))
                .andDo(print())
                .andDo(toDocument("user-login-fail"));
    }

    private void 유저_로그인_성공(UserLoginResponseDto userLoginResponseDto, ResultActions resultActions) throws Exception {
        resultActions.andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(userLoginResponseDto)))
                .andDo(print())
                .andDo(toDocument("user-login-success"));
    }

    private ResultActions 유저_로그인_요청(UserLoginRequestDto userLoginRequestDto) throws Exception {
        String content = objectMapper.writeValueAsString(userLoginRequestDto);
        return mockMvc.perform(post("/api/v1/users/login")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON));
    }

    @DisplayName("회원가입 성공")
    @Test
    void user_sign_up_success() throws Exception {
        UserJoinRequest signUpRequest = new UserJoinRequest("정재욱", "wodnr@naver.com", "라이언", "010-9969-9776", "111");
        UserJoinResponse signUpResponse = new UserJoinResponse(User.builder().email("wodnr@naver.com").build());
        willReturn(signUpResponse).given(userService).signUp(any(UserJoinRequest.class));
        final ResultActions response = 유저_회원가입_요청(signUpRequest);
        유저_회원가입_성공(signUpResponse, response);
    }

    @DisplayName("회원가입 실패")
    @Test
    void user_sign_up_fail() throws Exception {
        UserJoinRequest signUpRequest = new UserJoinRequest("정재욱", "wodnr@naver.com", "라이언", "010-9969-9776", "111");
        ErrorMessage errorMessage = new ErrorMessage("회원가입 실패");
        willThrow(new NotFoundException("회원가입 실패")).given(userService).signUp(any(UserJoinRequest.class));
        final ResultActions response = 유저_회원가입_요청(signUpRequest);
        유저_회원가입_실패(errorMessage, response);
    }

    @DisplayName("회원탈퇴 성공")
    @Test
    void user_withdraw_success() throws Exception {
        final String email = "email";
        UserWithdrawResponse userWithdrawResponse = new UserWithdrawResponse(User.builder().email(null).build());
        willReturn(userWithdrawResponse).given(userService).withdraw(email);
        final ResultActions response = 유저_회원탈퇴_요청(email);
        유저_회원탈퇴_성공(userWithdrawResponse, response);
    }

    @DisplayName("회원탈퇴 실패")
    @Test
    void user_withdraw_fail() throws Exception {
        final String email = "email";
        ErrorMessage errorMessage = new ErrorMessage("회원탈퇴 실패");
        willThrow(new NotFoundException("회원탈퇴 실패")).given(userService).withdraw(email);
        final ResultActions response = 유저_회원탈퇴_요청(email);
        유저_회원탈퇴_실패(errorMessage, response);
    }

    private ResultActions 유저_회원가입_요청(UserJoinRequest signUpRequest) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users/join")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(signUpRequest)));
    }

    private ResultActions 유저_회원탈퇴_요청(String email) throws Exception {
        return mockMvc.perform(delete("/api/v1/users/withdraw/" + email)
                .contentType(MediaType.APPLICATION_JSON));
    }

    private void 유저_회원가입_성공(UserJoinResponse signUpResponse, ResultActions response) throws Exception {
        response.andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(toJson(signUpResponse)))
                .andDo(print())
                .andDo(toDocument("user-signup-success"));
    }

    private void 유저_회원가입_실패(ErrorMessage errorMessage, ResultActions response) throws Exception {
        response.andExpect(status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().json(toJson(errorMessage)))
                .andDo(print())
                .andDo(toDocument("user-signup-fail"));
    }

    private void 유저_회원탈퇴_성공(UserWithdrawResponse userWithdrawResponse, ResultActions response) throws Exception {
        response.andExpect(status().isNoContent())
                .andExpect(MockMvcResultMatchers.content().json(toJson(userWithdrawResponse)))
                .andDo(print())
                .andDo(toDocument("user-withdraw-success"));
    }

    private void 유저_회원탈퇴_실패(ErrorMessage errorMessage, ResultActions response) throws Exception {
        response.andExpect(status().isNotFound())
                .andExpect(content().json(toJson(errorMessage)))
                .andDo(print())
                .andDo(toDocument("user-withdraw-fail"));
    }
}
