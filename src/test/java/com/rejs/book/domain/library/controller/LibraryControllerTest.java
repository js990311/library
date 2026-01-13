package com.rejs.book.domain.library.controller;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.BuilderArbitraryIntrospector;
import com.navercorp.fixturemonkey.jakarta.validation.plugin.JakartaValidationPlugin;
import com.rejs.book.domain.library.dto.LibraryRequest;
import com.rejs.book.domain.library.dto.LibraryResponse;
import com.rejs.book.domain.library.exception.LibraryNotFoundException;
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

    @Test
    @DisplayName("도서관 단건 상세 조회 성공")
    void getLibraryId_success() throws Exception {
        // Given: Fixture Monkey로 ID와 이름, 위치만 있는 가짜 응답 생성
        Long targetId = 1L;
        LibraryResponse response = fixtureMonkey.giveMeBuilder(LibraryResponse.class)
                .set(javaGetter(LibraryResponse::getId), targetId)
                .set(javaGetter(LibraryResponse::getName), "관악구립도서관")
                .set(javaGetter(LibraryResponse::getLocation), "서울시 관악구")
                .sample();

        given(libraryService.readById(targetId)).willReturn(response);

        // When & Then
        mockMvc.perform(get("/libraries/{id}", targetId))
                .andExpect(status().isOk())
                .andExpect(view().name("library/id.jsp"))
                .andExpect(model().attributeExists("library"))
                .andExpect(model().attribute("library", response));

        verify(libraryService, times(1)).readById(targetId);
    }

    @Test
    @DisplayName("존재하지 않는 도서관 조회 시 예외 발생")
    void getLibraryId_notFound() throws Exception {
        // Given
        Long invalidId = 99L;
        given(libraryService.readById(invalidId)).willThrow(new LibraryNotFoundException());

        // When & Then
        mockMvc.perform(get("/libraries/{id}", invalidId))
                .andExpect(status().isNotFound())
                .andExpect(view().name("error/404"))
        ;
    }

    @Test
    @DisplayName("도서관 삭제 성공 - 리다이렉트 확인")
    void postLibraryDelete_success() throws Exception {
        // Given
        Long targetId = 1L;
        // libraryService.delete(id.jsp)는 보통 void를 반환하므로 별도의 given 설정 없이 호출 여부만 확인합니다.

        // When & Then
        mockMvc.perform(post("/libraries/{id}/delete", targetId)
                        .with(csrf())) // CSRF 토큰 필수
                .andExpect(status().is3xxRedirection()) // 302 리다이렉트 확인
                .andExpect(redirectedUrl("/libraries")); // 목록으로 가는지 확인

        // 서비스의 delete 메서드가 정확한 ID로 1회 호출되었는지 검증
        verify(libraryService, times(1)).delete(targetId);
    }

    @Test
    @DisplayName("존재하지 않는 도서관 삭제 시도 시 404 응답")
    void postLibraryDelete_fail_notFound() throws Exception {
        // Given
        Long invalidId = 999L;
        // 서비스에서 존재하지 않을 때 예외를 던지도록 설정
        doThrow(new LibraryNotFoundException()).when(libraryService).delete(invalidId);

        // When & Then
        mockMvc.perform(post("/libraries/{id}/delete", invalidId)
                        .with(csrf())
                )
                .andExpect(status().isNotFound())
                .andExpect(view().name("error/404"));

        verify(libraryService, times(1)).delete(invalidId);
    }

    @Test
    @DisplayName("도서관 수정 폼 조회 성공")
    void getLibraryUpdate_success() throws Exception {
        // Given
        Long targetId = 1L;
        LibraryResponse response = fixtureMonkey.giveMeBuilder(LibraryResponse.class)
                .set(javaGetter(LibraryResponse::getId), targetId)
                .set(javaGetter(LibraryResponse::getName), "기존 도서관")
                .set(javaGetter(LibraryResponse::getLocation), "서울")
                .sample();

        given(libraryService.readById(targetId)).willReturn(response);

        // When & Then
        mockMvc.perform(get("/libraries/{id}/update", targetId))
                .andExpect(status().isOk())
                .andExpect(view().name("library/update"))
                .andExpect(model().attributeExists("libraryRequest"));
    }

    @Test
    @DisplayName("도서관 수정 처리 성공 - 리다이렉트 확인")
    void postLibraryUpdate_success() throws Exception {
        // Given
        Long targetId = 1L;
        LibraryRequest request = fixtureMonkey.giveMeBuilder(LibraryRequest.class)
                .set(javaGetter(LibraryRequest::getName), "수정된 도서관")
                .set(javaGetter(LibraryRequest::getLocation), "인천")
                .sample();

        // When & Then
        mockMvc.perform(post("/libraries/{id}/update", targetId)
                        .flashAttr("libraryRequest", request) // @ModelAttribute 바인딩
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/libraries"));

        // 서비스의 update 메서드가 정확히 호출되었는지 확인
        verify(libraryService, times(1)).update(eq(targetId), any(LibraryRequest.class));
    }

    @Test
    @DisplayName("도서관 수정 처리 실패 - 유효성 검사 오류")
    void postLibraryUpdate_validationFail() throws Exception {
        // Given
        Long targetId = 1L;
        // name을 빈 값으로 보내 @NotEmpty 위반 유도
        LibraryRequest invalidRequest = LibraryRequest.builder()
                .name("")
                .location("인천")
                .build();

        // When & Then
        mockMvc.perform(post("/libraries/{id}/update", targetId)
                        .flashAttr("libraryRequest", invalidRequest)
                        .with(csrf()))
                .andExpect(view().name("library/update")); // 다시 수정 폼으로 이동

        // 검증 에러가 발생했으므로 서비스의 update는 호출되지 않아야 함
        verify(libraryService, never()).update(anyLong(), any(LibraryRequest.class));
    }
}