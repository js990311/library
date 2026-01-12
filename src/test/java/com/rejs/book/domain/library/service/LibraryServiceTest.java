package com.rejs.book.domain.library.service;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.BuilderArbitraryIntrospector;
import com.rejs.book.domain.library.dto.LibraryRequest;
import com.rejs.book.domain.library.dto.LibraryResponse;
import com.rejs.book.domain.library.entity.Library;
import com.rejs.book.domain.library.exception.LibraryNotFoundException;
import com.rejs.book.domain.library.repository.LibraryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.navercorp.fixturemonkey.api.expression.JavaGetterMethodPropertySelector.javaGetter;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class LibraryServiceTest {
    @Mock
    private LibraryRepository libraryRepository;

    @InjectMocks
    private LibraryService libraryService;


    private final FixtureMonkey fixtureMonkey = FixtureMonkey.builder()
            .objectIntrospector(BuilderArbitraryIntrospector.INSTANCE)
            .build();

    @Test
    @DisplayName("도서관 등록 성공")
    void create_success() {
        Long id = 1L;
        LibraryRequest request = fixtureMonkey.giveMeOne(LibraryRequest.class);
        Library library = fixtureMonkey.giveMeBuilder(Library.class)
                .set(javaGetter(Library::getId), id)
                .sample();

        // given
        given(libraryRepository.save(any(Library.class))).willReturn(library);

        // when
        Long savedId = libraryService.create(request);

        // then
        assertThat(savedId).isEqualTo(id);
        verify(libraryRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("도서관 단건 조회 성공")
    void readById_success() {
        Long id = 1L;
        String name = "중앙도서관";
        Library library = fixtureMonkey.giveMeBuilder(Library.class)
                .set(javaGetter(Library::getId), id)
                .set(javaGetter(Library::getName), name)
                .sample();

        // given
        given(libraryRepository.findById(id)).willReturn(Optional.of(library));

        // when
        LibraryResponse response = libraryService.readById(id);

        // then
        assertThat(response.getName()).isEqualTo(name);
    }

    @Test
    @DisplayName("존재하지 않는 도서관 조회 시 예외 발생")
    void readById_fail() {
        // given
        given(libraryRepository.findById(1L)).willReturn(Optional.empty());

        // when & then
        assertThrows(LibraryNotFoundException.class, () -> libraryService.readById(1L));
    }

    @Test
    @DisplayName("도서관 수정 성공")
    void update_success() {
        Long id = 1L;
        Library library = fixtureMonkey.giveMeBuilder(Library.class)
                .set(javaGetter(Library::getId), id)
                .sample();
        String newName = "수정된 도서관";
        String newLocation = "경기도";
        LibraryRequest request = fixtureMonkey.giveMeBuilder(LibraryRequest.class)
                .set(javaGetter(LibraryRequest::getLocation), newLocation)
                .set(javaGetter(LibraryRequest::getName), newName)
                        .sample();

        // given
        given(libraryRepository.findById(1L)).willReturn(Optional.of(library));

        // when
        libraryService.update(1L, request);

        // then
        assertThat(library.getName()).isEqualTo(newName);
        assertThat(library.getLocation()).isEqualTo(newLocation);
    }

    @Test
    @DisplayName("도서관 삭제 성공")
    void delete_success() {
        Long id= 1L;
        // given
        given(libraryRepository.existsById(id)).willReturn(true);

        // when
        libraryService.delete(id);

        // then
        verify(libraryRepository, times(1)).deleteById(id);
    }

    @Test
    @DisplayName("삭제 시 도서관이 없으면 예외 발생")
    void delete_fail() {
        // given
        given(libraryRepository.existsById(1L)).willReturn(false);

        // when & then
        assertThrows(LibraryNotFoundException.class, () -> libraryService.delete(1L));
    }}