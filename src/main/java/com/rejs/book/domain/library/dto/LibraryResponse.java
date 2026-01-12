package com.rejs.book.domain.library.dto;

import com.rejs.book.domain.library.entity.Library;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class LibraryResponse {
    private Long id;
    private String name;
    private String webpage;
    private String location;

    public static LibraryResponse from(Library library){
        return LibraryResponse.builder()
                .id(library.getId())
                .name(library.getName())
                .webpage(library.getWebpage())
                .location(library.getLocation())
                .build();
    }
}
