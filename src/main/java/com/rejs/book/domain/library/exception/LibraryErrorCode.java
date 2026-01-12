package com.rejs.book.domain.library.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LibraryErrorCode {
    NOT_FOUND("0001", "해당 도서관이 존재하지 않습니다");

    private final String type;
    private final String detail;

    public String getCode(){
        return "Library_" + type;
    }
}
