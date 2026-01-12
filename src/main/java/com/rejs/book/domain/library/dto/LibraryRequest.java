package com.rejs.book.domain.library.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class LibraryRequest {
    @NotEmpty
    private String name;
    @NotEmpty
    private String location;

    public static LibraryRequest from (LibraryResponse library){
        return LibraryRequest.builder()
                .name(library.getName())
                .location(library.getLocation())
                .build();
    }
}
