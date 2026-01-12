package com.rejs.book.domain.library.controller;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.BuilderArbitraryIntrospector;
import com.navercorp.fixturemonkey.jakarta.validation.plugin.JakartaValidationPlugin;
import com.rejs.book.domain.library.dto.LibraryRequest;
import com.rejs.book.domain.library.dto.LibraryResponse;
import com.rejs.book.domain.library.service.LibraryService;
import com.rejs.book.global.security.config.SecurityConfig;
import com.rejs.book.global.security.controller.LoginController;
import com.rejs.book.global.security.service.SignupService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static com.navercorp.fixturemonkey.api.expression.JavaGetterMethodPropertySelector.javaGetter;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LibraryController.class)
@ActiveProfiles("test")
@Import(SecurityConfig.class)
class LibraryControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private LibraryService libraryService;

    @InjectMocks
    private LibraryController libraryController;

    private final FixtureMonkey fixtureMonkey = FixtureMonkey.builder()
            .objectIntrospector(BuilderArbitraryIntrospector.INSTANCE)
            .plugin(new JakartaValidationPlugin())
            .build();

    @Test
    void getList() throws Exception {
        List<LibraryResponse> contents = fixtureMonkey.giveMeBuilder(LibraryResponse.class).sampleList(15);
        Pageable pageable = PageRequest.of(0,15);
        PageImpl<LibraryResponse> libraryPage = new PageImpl<>(contents, pageable, contents.size());

        given(libraryService.readAllByPage(any(Pageable.class))).willReturn(libraryPage);

        mockMvc.perform(get("/libraries") // 경로가 /libraries라 가정
                        .param("page", "0")
                        .param("size", "15"))
                .andExpect(status().isOk()) // HTTP 200 확인
                .andExpect(view().name("library/index"))
                .andExpect(model().attributeExists("libraryPage"))
                .andExpect(model().attribute("libraryPage", libraryPage));

        // 서비스가 정확히 호출되었는지 확인
        verify(libraryService, times(1)).readAllByPage(any(Pageable.class));
    }

    @Test
    @DisplayName("도서관 등록 폼 조회 성공")
    void getLibraryCreate_test() throws Exception {
        mockMvc.perform(get("/libraries/create")) // GetMapping("/create") 호출
                .andExpect(status().isOk())
                .andExpect(view().name("library/create"))
                .andExpect(model().attributeExists("libraryRequest"));
    }

    @Test
    @DisplayName("도서관 등록 처리 성공 - PRG 패턴 확인")
    void postLibraryCreate_success() throws Exception {
        // Given
        LibraryRequest request = fixtureMonkey.giveMeBuilder(LibraryRequest.class)
                .sample();

        // When & Then
        mockMvc.perform(post("/libraries/create")
                        .flashAttr("libraryRequest", request)
                        .with(csrf())
                ) // ModelAttribute 바인딩 시뮬레이션
                .andExpect(status().is3xxRedirection()) // redirect:/libraries 확인
                .andExpect(redirectedUrl("/libraries"));

        verify(libraryService, times(1)).create(any(LibraryRequest.class));
    }

    @Test
    @DisplayName("도서관 등록 처리 실패 - BindingResult 검증 오류 시")
    void postLibraryCreate_fail() throws Exception {
        // When & Then
        mockMvc.perform(post("/libraries/create")
                        .param("name", "")
                        .with(csrf())
                ) // 빈 이름 전달 (Validation 오류 유도)
                .andExpect(view().name("library/create")); // 다시 등록 폼으로 돌아가는지 확인

        // 에러가 있으면 서비스가 호출되지 않아야 함
        verify(libraryService, never()).create(any(LibraryRequest.class));
    }
}