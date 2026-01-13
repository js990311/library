package com.rejs.book.domain.book.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateBookRequest {
    private String name;
    private String description;
    private String isbn;
}
