package com.rejs.book.domain.book.controller;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.BuilderArbitraryIntrospector;
import com.navercorp.fixturemonkey.jakarta.validation.plugin.JakartaValidationPlugin;
import com.rejs.book.domain.book.dto.BookDto;
import com.rejs.book.domain.book.dto.CreateBookRequest;
import com.rejs.book.domain.book.dto.UpdateBookRequest;
import com.rejs.book.domain.book.service.BookService;
import com.rejs.book.global.security.config.SecurityConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.navercorp.fixturemonkey.api.expression.JavaGetterMethodPropertySelector.javaGetter;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
@ActiveProfiles("test")
@Import(SecurityConfig.class)
class BookControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

    private final FixtureMonkey fixtureMonkey = FixtureMonkey.builder()
            .objectIntrospector(BuilderArbitraryIntrospector.INSTANCE)
            .plugin(new JakartaValidationPlugin())
            .build();

    @Test
    @DisplayName("도서 목록 조회 성공: 페이징된 데이터와 index 뷰를 반환해야 한다")
    void getIndex_success() throws Exception {
        // given
        List<BookDto> bookList = fixtureMonkey.giveMeBuilder(BookDto.class).sampleList(3);
        Pageable pageable = PageRequest.of(0, 30);
        Page<BookDto> bookPage = new PageImpl<>(bookList, pageable, bookList.size());

        given(bookService.readByPage(any(Pageable.class))).willReturn(bookPage);

        // when & then
        mockMvc.perform(get("/books")
                        .param("page", "0")
                        .param("size", "30"))
                .andExpect(status().isOk())
                .andExpect(view().name("book/index"))
                .andExpect(model().attributeExists("bookPage"))
                .andExpect(model().attribute("bookPage", bookPage));

        then(bookService).should(times(1)).readByPage(any(Pageable.class));
    }

    @Test
    @DisplayName("도서 상세 조회 성공: 도서 데이터와 id" +
            "" +
            "" +
            "" +
            " 뷰를 반환해야 한다")
    void getBookById_success() throws Exception {
        // given
        Long bookId = 1L;
        BookDto response = fixtureMonkey.giveMeBuilder(BookDto.class)
                .set(javaGetter(BookDto::getName), "테스트 도서")
                .sample();

        given(bookService.readById(bookId)).willReturn(response);

        // when & then
        mockMvc.perform(get("/books/{id}", bookId))
                .andExpect(status().isOk())
                .andExpect(view().name("book/id"))
                .andExpect(model().attributeExists("book"))
                .andExpect(model().attribute("book", response));

        then(bookService).should(times(1)).readById(bookId);
    }

    @Test
    @DisplayName("도서 등록 폼 조회 성공")
    void getBookCreate_success() throws Exception {
        mockMvc.perform(get("/books/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("book/create"))
                .andExpect(model().attributeExists("createBookRequest"));
    }

    @Test
    @DisplayName("도서 등록 처리 성공: 유효한 입력 시 상세 페이지로 리다이렉트된다 (PRG 패턴)")
    void postBookCreate_success() throws Exception {
        // given
        Long savedBookId = 100L;
        CreateBookRequest request = fixtureMonkey.giveMeOne(CreateBookRequest.class);

        given(bookService.createBook(any(CreateBookRequest.class))).willReturn(savedBookId);

        // when & then
        mockMvc.perform(post("/books/create")
                        .flashAttr("createBookRequest", request)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/books/" + savedBookId));

        then(bookService).should(times(1)).createBook(any(CreateBookRequest.class));
    }

    @Test
    @DisplayName("도서 등록 처리 실패: 유효성 검사 오류 시 폼 뷰로 돌아가야 한다")
    void postBookCreate_validationFail() throws Exception {
        // given: name이 빈 값인 잘못된 요청
        CreateBookRequest invalidRequest = CreateBookRequest.builder()
                .name("") // @NotEmpty 위반
                .description("설명")
                .isbn("12345")
                .build();

        // when & then
        mockMvc.perform(post("/books/create")
                        .flashAttr("createBookRequest", invalidRequest)
                        .with(csrf()))
                .andExpect(status().isOk()) // 뷰를 다시 렌더링하므로 200 OK
                .andExpect(view().name("book/create"))
                .andExpect(model().hasErrors());

        // 검증 에러가 있으므로 서비스 호출은 발생하지 않아야 함
        then(bookService).should(never()).createBook(any(CreateBookRequest.class));
    }

    @Test
    @DisplayName("도서 수정 폼 조회 성공: 기존 도서 정보를 조회하여 모델에 담고 update 뷰를 반환해야 한다")
    void getBookUpdate_success() throws Exception {
        // given
        Long bookId = 1L;
        BookDto existingBook = fixtureMonkey.giveMeBuilder(BookDto.class)
                .set(javaGetter(BookDto::getId), bookId)
                .set(javaGetter(BookDto::getName), "기존 도서명")
                .sample();

        given(bookService.readById(bookId)).willReturn(existingBook);

        // when & then
        mockMvc.perform(get("/books/{id}/update", bookId))
                .andExpect(status().isOk())
                .andExpect(view().name("book/update"))
                .andExpect(model().attributeExists("updateBookRequest"))
                // 서비스에서 가져온 데이터가 Request 객체에 잘 변환되었는지 확인 (일부 필드)
                .andExpect(model().attributeHasNoErrors("updateBookRequest"));

        then(bookService).should(times(1)).readById(bookId);
    }

    @Test
    @DisplayName("도서 수정 처리 성공: 유효한 수정 요청 시 상세 페이지로 리다이렉트되어야 한다")
    void postBookUpdate_success() throws Exception {
        // given
        Long bookId = 1L;
        UpdateBookRequest request = fixtureMonkey.giveMeOne(UpdateBookRequest.class);

        // when & then
        mockMvc.perform(post("/books/{id}/update", bookId)
                        .flashAttr("updateBookRequest", request)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/books/" + bookId));

        then(bookService).should(times(1)).update(eq(bookId), any(UpdateBookRequest.class));
    }

    @Test
    @DisplayName("도서 수정 처리 실패: 유효성 검사 에러 발생 시 수정 폼으로 다시 이동해야 한다")
    void postBookUpdate_fail_validation() throws Exception {
        // given: 필수 값이 비어있는 잘못된 데이터 생성
        Long bookId = 1L;
        UpdateBookRequest invalidRequest = UpdateBookRequest.builder()
                .name("") // @NotEmpty 위반 상황 가정
                .build();

        // when & then
        mockMvc.perform(post("/books/{id}/update", bookId)
                        .flashAttr("updateBookRequest", invalidRequest)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("book/update"))
                .andExpect(model().hasErrors());

        // 비즈니스 로직(Service.update)이 호출되지 않았음을 확인
        then(bookService).should(never()).update(anyLong(), any());
    }
    @Test
    @DisplayName("도서 삭제 성공: 삭제 후 목록 페이지로 리다이렉트되어야 한다")
    void postBookDelete_success() throws Exception {
        // given
        Long bookId = 1L;

        // when & then
        mockMvc.perform(post("/books/{id}/delete", bookId)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/books"));

        then(bookService).should(times(1)).deleteBook(bookId);
    }
}