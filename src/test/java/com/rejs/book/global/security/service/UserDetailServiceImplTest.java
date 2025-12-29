package com.rejs.book.global.security.service;

import com.rejs.book.domain.user.entity.User;
import com.rejs.book.domain.user.entity.UserRole;
import com.rejs.book.domain.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserDetailServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserDetailServiceImpl userDetailService;

    private User testUser;

    private String username = "testUser";
    private String password = "encodedPassword";
    private UserRole role = UserRole.USER;

    @BeforeEach
    void setUp() {
        testUser = User.builder()
                .username(username)
                .password(password)
                .userRole(role)
                .build();
    }

    @Test
    @DisplayName("성공: 존재하는 사용자 이름으로 조회 시 UserDetails 반환")
    void loadUserByUsername() {
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(testUser));

        UserDetails userDetails = userDetailService.loadUserByUsername("testUser");

        assertEquals(username, userDetails.getUsername());
        assertEquals(password, userDetails.getPassword());

        assertTrue(userDetails.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_" + role.toString())));

        verify(userRepository, times(1)).findByUsername(username);
    }

    @Test
    @DisplayName("실패: 존재하지 않는 사용자 이름으로 조회 시 예외 발생")
    void loadUserByUsername_fail_notFound() {
        when(userRepository.findByUsername("noneUser")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> {
            userDetailService.loadUserByUsername("noneUser");
        });

        verify(userRepository, times(1)).findByUsername("noneUser");
    }
}