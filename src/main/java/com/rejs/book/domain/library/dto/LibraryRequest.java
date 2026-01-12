package com.rejs.book.domain.library.dto;

import lombok.Data;

@Data
public class LibraryRequest {
    private String name;
    private String webpage;
    private String location;
}
