package com.rejs.book.domain.book.exception.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BookErrorCode {
    NOT_FOUND("0001", "해당 도서가 존재하지 않습니다");

    private final String type;
    private final String detail;

    public String getCode(){
        return "BOOK_" + type;
    }

}
