package com.minionz.backend.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.minionz.backend.ApiDocument;
import com.minionz.backend.common.domain.Address;
import com.minionz.backend.common.domain.Message;
import com.minionz.backend.common.exception.BadRequestException;
import com.minionz.backend.common.exception.NotFoundException;
import com.minionz.backend.user.controller.dto.JoinRequestDto;
import com.minionz.backend.user.controller.dto.Role;
import com.minionz.backend.user.controller.dto.LoginRequestDto;
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
import static org.mockito.BDDMockito.*;
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
        final LoginRequestDto LoginRequestDto = new LoginRequestDto("email", "password",Role.USER);
        Message message = new Message("로그인 성공");
        willReturn(message).given(userService).login(any(LoginRequestDto.class));
        final ResultActions resultActions = 유저_로그인_요청(LoginRequestDto);
        유저_로그인_성공(message, resultActions);
    }

    @DisplayName("유저 로그인 실패")
    @Test
    public void 유저로그인테스트_실패() throws Exception {
        final LoginRequestDto LoginRequestDto = new LoginRequestDto("email1", "password",Role.USER);
        Message errorMessage = new Message("로그인 실패");
        willThrow(new NotFoundException("로그인 실패")).given(userService).login(any(LoginRequestDto.class));
        final ResultActions resultActions = 유저_로그인_요청(LoginRequestDto);
        유저_로그인_실패(errorMessage, resultActions);
    }

    @DisplayName("오너 로그인 성공")
    @Test
    public void 오너로그인테스트_성공() throws Exception {
        final LoginRequestDto LoginRequestDto = new LoginRequestDto("email", "password",Role.OWNER);
        Message message = new Message("로그인 성공");
        willReturn(message).given(userService).login(any(LoginRequestDto.class));
        final ResultActions resultActions = 유저_로그인_요청(LoginRequestDto);
        오너_로그인_성공(message, resultActions);
    }

    @DisplayName("오너 로그인 실패")
    @Test
    public void 오너로그인테스트_실패() throws Exception {
        final LoginRequestDto LoginRequestDto = new LoginRequestDto("email1", "password",Role.OWNER);
        Message errorMessage = new Message("로그인 실패");
        willThrow(new NotFoundException("로그인 실패")).given(userService).login(any(LoginRequestDto.class));
        final ResultActions resultActions = 유저_로그인_요청(LoginRequestDto);
        오너_로그인_실패(errorMessage, resultActions);
    }

    @DisplayName("유저 로그아웃 성공")
    @Test
    public void 유저로그아웃테스트_성공() throws Exception {
        final String email = "email";
        Message message = new Message("로그아웃 성공");
        willReturn(message).given(userService).logout(any(String.class), any(Role.class));
        final ResultActions resultActions = 유저_로그아웃_요청(email, Role.USER);
        유저_로그아웃_성공(resultActions);
    }

    @DisplayName("유저 로그아웃 실패")
    @Test
    public void 유저로그아웃테스트_실패() throws Exception {
        final String email = "email";
        Message errorMessage = new Message("로그아웃 실패");
        willThrow(new NotFoundException("로그아웃 실패")).given(userService).logout(any(String.class), any(Role.class));
        final ResultActions resultActions = 유저_로그아웃_요청(email, Role.USER);
        유저_로그아웃_실패(errorMessage, resultActions);
    }

    @DisplayName("오너 로그아웃 성공")
    @Test
    public void 오너로그아웃테스트_성공() throws Exception {
        final String email = "email";
        Message message = new Message("로그아웃 성공");
        willReturn(message).given(userService).logout(any(String.class), any(Role.class));
        final ResultActions resultActions = 유저_로그아웃_요청(email, Role.OWNER);
        오너_로그아웃_성공(resultActions);
    }

    @DisplayName("오너 로그아웃 실패")
    @Test
    public void 오너로그아웃테스트_실패() throws Exception {
        final String email = "email";
        Message errorMessage = new Message("로그아웃 실패");
        willThrow(new NotFoundException("로그아웃 실패")).given(userService).logout(any(String.class), any(Role.class));
        final ResultActions resultActions = 유저_로그아웃_요청(email, Role.OWNER);
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
                .build();Message errorMessage = new Message("회원가입 실패");
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
        final String email = "email";
        Message message = new Message("회원탈퇴 성공");
        willReturn(message).given(userService).withdraw(any(String.class), any(Role.class));
        final ResultActions response = 유저_회원탈퇴_요청(email, Role.USER);
        유저_회원탈퇴_성공(response);
    }

    @DisplayName("유저회원탈퇴 실패")
    @Test
    void 유저회원탈퇴_실패() throws Exception {
        final String email = "email";
        Message errorMessage = new Message("회원탈퇴 실패");
        willThrow(new NotFoundException("회원탈퇴 실패")).given(userService).withdraw(any(String.class), any(Role.class));
        final ResultActions response = 유저_회원탈퇴_요청(email, Role.USER);
        유저_회원탈퇴_실패(errorMessage, response);
    }

    @DisplayName("오너회원탈퇴 성공")
    @Test
    void 오너회원탈퇴_성공() throws Exception {
        final String email = "email";
        Message message = new Message("회원탈퇴 성공");
        willReturn(message).given(userService).withdraw(any(String.class), any(Role.class));
        final ResultActions response = 유저_회원탈퇴_요청(email, Role.OWNER);
        오너_회원탈퇴_성공(response);
    }

    @DisplayName("오너회원탈퇴 실패")
    @Test
    void 오너회원탈퇴_실패() throws Exception {
        final String email = "email";
        Message errorMessage = new Message("회원탈퇴 실패");
        willThrow(new NotFoundException("회원탈퇴 실패")).given(userService).withdraw(any(String.class), any(Role.class));
        final ResultActions response = 유저_회원탈퇴_요청(email, Role.OWNER);
        오너_회원탈퇴_실패(errorMessage, response);
    }

    private ResultActions 유저_회원가입_요청(JoinRequestDto signUpRequest) throws Exception {
        return mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users/join")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(signUpRequest)));
    }

    private ResultActions 유저_회원탈퇴_요청(String email, Role role) throws Exception {
        return mockMvc.perform(delete("/api/v1/users/withdraw/" + email+"/"+role)
                .contentType(MediaType.APPLICATION_JSON)
                .content(email)
                .content(role.name()));
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

    private ResultActions 유저_로그아웃_요청(String email, Role role) throws Exception {
        return mockMvc.perform(get("/api/v1/users/logout/" + email+ "/" + role)
                .contentType(MediaType.APPLICATION_JSON)
                .content(email).content(String.valueOf(role)));
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

    private void 유저_로그인_성공(Message message, ResultActions resultActions) throws Exception {
        resultActions.andExpect(status().isOk())
                .andExpect(content().json(toJson(message)))
                .andDo(print())
                .andDo(toDocument("user-login-success"));
    }

    private void 오너_로그인_성공(Message message, ResultActions resultActions) throws Exception {
        resultActions.andExpect(status().isOk())
                .andExpect(content().json(toJson(message)))
                .andDo(print())
                .andDo(toDocument("owner-login-success"));
    }

    private ResultActions 유저_로그인_요청(LoginRequestDto LoginRequestDto) throws Exception {
        String content = objectMapper.writeValueAsString(LoginRequestDto);
        return mockMvc.perform(post("/api/v1/users/login")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON));
    }
}
