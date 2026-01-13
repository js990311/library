package com.rejs.book.domain.book.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateBookRequest {
    @NotEmpty
    private String name;
    @NotEmpty
    private String description;
    @NotEmpty
    private String isbn;

    public static UpdateBookRequest from(BookDto bookDto) {
        return UpdateBookRequest.builder()
                .name(bookDto.getName())
                .description(bookDto.getDescription())
                .isbn(bookDto.getIsbn())
                .build();
    }
}
