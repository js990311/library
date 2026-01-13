package com.rejs.book.domain.book.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateBookRequest {
    @NotEmpty
    private String name;
    @NotEmpty
    private String description;
    @NotEmpty
    private String isbn;
}
