package com.rejs.book.global.security.service;

import com.rejs.book.domain.user.entity.User;
import com.rejs.book.domain.user.entity.UserRole;
import com.rejs.book.domain.user.repository.UserRepository;
import com.rejs.book.global.security.dto.SignupRequest;
import com.rejs.book.global.security.exception.UsernameAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Controller
public class SignupService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void signup(SignupRequest request){
        if(userRepository.existsByUsername(request.getUsername())){
            throw new UsernameAlreadyExistsException();
        }
        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .userRole(UserRole.USER)
                .build();
        user = userRepository.save(user);
        return;
    }
}
