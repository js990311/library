package com.rejs.book.global.security.controller;

import com.rejs.book.global.security.config.SecurityConfig;
import com.rejs.book.global.security.dto.SignupRequest;
import com.rejs.book.global.security.exception.UsernameAlreadyExistsException;
import com.rejs.book.global.security.service.SignupService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LoginController.class)
@ActiveProfiles("test")
@Import(SecurityConfig.class)
class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SignupService signupService;

    @Test
    @DisplayName("GET /signup - 회원가입 페이지가 정상적으로 반환되어야 한다")
    void signupPage_test() throws Exception {
        mockMvc.perform(get("/signup"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/signup"))
                .andExpect(model().attributeExists("signupForm"));
    }

    @Test
    @DisplayName("POST /signup 성공 - 로그인 페이지로 리다이렉트되어야 한다")
    void signup_success_test() throws Exception {
        mockMvc.perform(post("/signup")
                        .param("username", "newuser")
                        .param("password", "password123")
                        .with(csrf())
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"))
                .andExpect(flash().attribute("redirectMsg", "회원가입 성공했습니다. 로그인해주십시오"))
        ;

        verify(signupService, times(1)).signup(any(SignupRequest.class));
    }

    @Test
    @DisplayName("POST /signup 실패 - 아이디 중복 시 에러 메시지와 함께 signup 뷰를 반환해야 한다")
    void signup_fail_duplicate_username() throws Exception {
        doThrow(new UsernameAlreadyExistsException())
                .when(signupService).signup(any(SignupRequest.class));

        mockMvc.perform(post("/signup")
                        .param("username", "duplicateUser")
                        .param("password", "password123")
                        .with(csrf())
                )
                .andExpect(status().isOk())
                .andExpect(view().name("user/signup"))
                .andExpect(model().hasErrors())
                .andExpect(model().attributeHasFieldErrors("signupForm", "username"));
    }

    @Test
    @DisplayName("POST /signup 실패 - Validation 오류 (아이디 미입력)")
    void signup_fail_validation() throws Exception {
        mockMvc.perform(post("/signup")
                        .param("username", "") // 빈 값 전송
                        .param("password", "123")
                        .with(csrf())
                )
                .andExpect(status().isOk())
                .andExpect(view().name("user/signup"))
                .andExpect(model().hasErrors());
    }
}