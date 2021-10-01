package com.minionz.backend.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.minionz.backend.ApiDocument;
import com.minionz.backend.common.domain.StatusCode;
import com.minionz.backend.user.controller.dto.UserLoginRequestDto;
import com.minionz.backend.user.controller.dto.UserLoginResponseDto;
import com.minionz.backend.user.controller.dto.UserLogoutResponseDto;
import com.minionz.backend.user.domain.User;
import com.minionz.backend.user.service.UserService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

    @Test
    public void 유저로그인테스트_성공() throws Exception {
        final UserLoginRequestDto userLoginRequestDto = new UserLoginRequestDto("email", "password");
        UserLoginResponseDto userLoginResponseDto = new UserLoginResponseDto(User.builder().email("email").build());
        willReturn(userLoginResponseDto).given(userService).login(any(UserLoginRequestDto.class));
        final ResultActions resultActions = 유저_로그인_요청(userLoginRequestDto);
        유저_로그인_성공(userLoginResponseDto,resultActions);
    }

    @Test
    public void 유저로그인테스트_실패() throws Exception {
        final UserLoginRequestDto userLoginRequestDto = new UserLoginRequestDto("email1", "password");
        UserLoginResponseDto userLoginResponseDto = new UserLoginResponseDto(User.builder().email("null").build());
        userLoginResponseDto.setStatusCode(StatusCode.BAD_REQUEST);
        //willReturn(userLoginResponseDto).given(userService).login(any(UserLoginRequestDto.class));
        final ResultActions resultActions = 유저_로그인_요청(userLoginRequestDto);
        유저_로그인_실패(userLoginResponseDto, resultActions);
    }

    @Test
    public void 유저로그아웃테스트_성공() throws Exception {
        final String email = "email";
        UserLogoutResponseDto userLogoutResponseDto = new UserLogoutResponseDto(User.builder().email("email").build());
        willReturn(userLogoutResponseDto).given(userService).logout(email);
        final ResultActions resultActions = 유저_로그아웃_요청(email);
        유저_로그아웃_성공(userLogoutResponseDto,resultActions);
    }

    @Test
    public void 유저로그아웃테스트_실패() throws Exception {
        final String email = "email";
        UserLogoutResponseDto userLogoutResponseDto = new UserLogoutResponseDto(User.builder().email("email").build());
        userLogoutResponseDto.setStatusCode(StatusCode.BAD_REQUEST);
        //willReturn(userLogoutResponseDto).given(userService).logout(email);
        final ResultActions resultActions = 유저_로그아웃_요청(email);
        유저_로그아웃_실패(userLogoutResponseDto,resultActions);
    }

    private void 유저_로그아웃_실패(UserLogoutResponseDto userLogoutResponseDto, ResultActions resultActions) throws Exception {
        resultActions.andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(userLogoutResponseDto)))
                .andDo(print())
                .andDo(toDocument("user-logout-fail"));
    }

    private void 유저_로그아웃_성공(UserLogoutResponseDto userLogoutResponseDto, ResultActions resultActions) throws Exception {
        resultActions.andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(userLogoutResponseDto)))
                .andDo(print())
                .andDo(toDocument("user-logout-success"));
    }

    private ResultActions 유저_로그아웃_요청(String email) throws Exception {
        return mockMvc.perform(get("/api/v1/users/logout/"+email)
                .contentType(MediaType.APPLICATION_JSON));
    }

    private void 유저_로그인_실패(UserLoginResponseDto userLoginResponseDto, ResultActions resultActions) throws Exception {
        resultActions.andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(userLoginResponseDto)))
                .andDo(print())
                .andDo(toDocument("user-login-fail"));
    }

    private void 유저_로그인_성공(UserLoginResponseDto userLoginResponseDto, ResultActions resultActions) throws Exception {
        resultActions.andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(userLoginResponseDto)))
                .andDo(print())
                .andDo(toDocument("user-login-success"));
    }

    private ResultActions 유저_로그인_요청(UserLoginRequestDto userLoginRequestDto) throws Exception{
        String content = objectMapper.writeValueAsString(userLoginRequestDto);
        return mockMvc.perform(post("/api/v1/users/login")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON));
    }
}