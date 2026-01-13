package com.rejs.book.domain.book.dto;

import com.rejs.book.domain.book.entity.Book;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class BookDto {
    private Long id;
    private String name;
    private String description;
    private String isbn;

    public static BookDto from(Book book){
        return BookDto.builder()
                .id(book.getId())
                .name(book.getName())
                .description(book.getDescription())
                .isbn(book.getIsbn())
                .build();
    }
}
