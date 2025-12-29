package com.rejs.book.global.security.service;

import com.rejs.book.domain.user.entity.User;
import com.rejs.book.domain.user.repository.UserRepository;
import com.rejs.book.global.security.dto.SignupRequest;
import com.rejs.book.global.security.exception.UsernameAlreadyExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SignupServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private SignupService signupService;

    private SignupRequest request;

    @BeforeEach
    void setUp() {
        request = new SignupRequest();
        request.setUsername("testUser");
        request.setPassword("password123");
    }

    @Test
    @DisplayName("회원가입 성공 - 중복 아이디가 없는 경우")
    void signup_success() {
        // given
        // 아이디가 존재하지 않는 상황을 가정 (Mock 설정)
        when(userRepository.existsByUsername(request.getUsername())).thenReturn(false);
        when(passwordEncoder.encode(request.getPassword())).thenReturn("encodedPassword");

        // when
        signupService.signup(request);

        // then
        // save 메서드가 최소 한 번 호출되었는지 확인
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    @DisplayName("회원가입 실패 - 이미 아이디가 존재하는 경우 예외 발생")
    void signup_fail_duplicate_username() {
        // given
        // 아이디가 이미 존재하는 상황을 가정 (Mock 설정)
        when(userRepository.existsByUsername(request.getUsername())).thenReturn(true);

        // when & then
        // 예외가 실제로 던져지는지 검증
        assertThrows(UsernameAlreadyExistsException.class, () -> {
            signupService.signup(request);
        });

        // 예외가 터졌으므로 save 메서드는 절대 호출되면 안 됨
        verify(userRepository, never()).save(any(User.class));
    }}