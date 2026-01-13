package com.rejs.book.domain.book.service;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.BuilderArbitraryIntrospector;
import com.rejs.book.domain.book.dto.BookDto;
import com.rejs.book.domain.book.dto.CreateBookRequest;
import com.rejs.book.domain.book.dto.UpdateBookRequest;
import com.rejs.book.domain.book.entity.Book;
import com.rejs.book.domain.book.exception.BookNotFoundException;
import com.rejs.book.domain.book.repository.BookRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static com.navercorp.fixturemonkey.api.expression.JavaGetterMethodPropertySelector.javaGetter;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    private final FixtureMonkey fixtureMonkey = FixtureMonkey.builder()
            .objectIntrospector(BuilderArbitraryIntrospector.INSTANCE)
            .build();

    @Test
    @DisplayName("도서 등록 성공: 생성된 도서의 ID를 반환해야 한다")
    void createBook_success() {
        // given
        Long expectedId = 1L;
        CreateBookRequest request = fixtureMonkey.giveMeOne(CreateBookRequest.class);
        Book savedBook = fixtureMonkey.giveMeBuilder(Book.class)
                .set(javaGetter(Book::getId), expectedId)
                .sample();

        given(bookRepository.save(any(Book.class))).willReturn(savedBook);

        // when
        Long actualId = bookService.createBook(request);

        // then
        assertThat(actualId).isEqualTo(expectedId);
        then(bookRepository).should(times(1)).save(any(Book.class));
    }

    @Test
    @DisplayName("도서 단건 조회 성공: 정확한 도서 정보를 반환해야 한다")
    void readById_success() {
        // given
        Long bookId = 1L;
        Book book = fixtureMonkey.giveMeBuilder(Book.class)
                .set(javaGetter(Book::getId), bookId)
                .sample();

        given(bookRepository.findById(bookId)).willReturn(Optional.of(book));

        // when
        BookDto response = bookService.readById(bookId);

        // then
        assertThat(response.getName()).isEqualTo(book.getName());
        assertThat(response.getIsbn()).isEqualTo(book.getIsbn());
        then(bookRepository).should(times(1)).findById(bookId);
    }

    @Test
    @DisplayName("도서 단건 조회 실패: 존재하지 않는 ID일 경우 예외가 발생한다")
    void readById_fail_notFound() {
        // given
        Long invalidId = 1L;
        given(bookRepository.findById(invalidId)).willReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> bookService.readById(invalidId))
                .isInstanceOf(BookNotFoundException.class);
    }

    @Test
    @DisplayName("도서 페이징 조회 성공: 요청한 페이지의 도서 목록을 반환해야 한다")
    void readByPage_success() {
        // given
        Pageable pageable = PageRequest.of(0, 10);
        List<Book> books = fixtureMonkey.giveMe(Book.class, 3);
        Page<Book> bookPage = new PageImpl<>(books, pageable, books.size());

        given(bookRepository.findAll(pageable)).willReturn(bookPage);

        // when
        Page<BookDto> result = bookService.readByPage(pageable);

        // then
        assertThat(result.getContent()).hasSize(3);
        then(bookRepository).should(times(1)).findAll(pageable);
    }

    @Test
    @DisplayName("도서 정보 수정 성공: 엔티티의 필드값이 변경되어야 한다")
    void update_success() {
        // given
        Long bookId = 1L;
        UpdateBookRequest request = fixtureMonkey.giveMeOne(UpdateBookRequest.class);
        Book book = fixtureMonkey.giveMeBuilder(Book.class)
                .set(javaGetter(Book::getId), bookId)
                .sample();

        given(bookRepository.findById(bookId)).willReturn(Optional.of(book));

        // when
        bookService.update(bookId, request);

        // then
        // 서비스 내부에서 book.update(request)가 호출되어 필드가 변경되었는지 확인
        assertThat(book.getName()).isEqualTo(request.getName());
        assertThat(book.getDescription()).isEqualTo(request.getDescription());
        then(bookRepository).should(times(1)).findById(bookId);
    }

    @Test
    @DisplayName("도서 삭제 성공: ID가 존재하면 삭제 로직을 실행한다")
    void deleteBook_success() {
        // given
        Long bookId = 1L;
        given(bookRepository.existsById(bookId)).willReturn(true);

        // when
        bookService.deleteBook(bookId);

        // then
        then(bookRepository).should(times(1)).deleteById(bookId);
    }

    @Test
    @DisplayName("도서 삭제 실패: 존재하지 않는 ID일 경우 삭제를 시도하지 않고 예외를 던진다")
    void deleteBook_fail_notFound() {
        // given
        Long invalidId = 1L;
        given(bookRepository.existsById(invalidId)).willReturn(false);

        // when & then
        assertThatThrownBy(() -> bookService.deleteBook(invalidId))
                .isInstanceOf(BookNotFoundException.class);

        then(bookRepository).should(never()).deleteById(any());
    }
}