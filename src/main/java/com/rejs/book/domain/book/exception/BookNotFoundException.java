package com.rejs.book.domain.book.exception;

import com.rejs.book.domain.book.exception.code.BookErrorCode;

public class BookNotFoundException extends BookException{
    public BookNotFoundException() {
        super(BookErrorCode.NOT_FOUND);
    }

    public BookNotFoundException(String message) {
        super(message, BookErrorCode.NOT_FOUND);
    }

    public BookNotFoundException(String message, Throwable cause) {
        super(message, cause, BookErrorCode.NOT_FOUND);
    }

    public BookNotFoundException(Throwable cause) {
        super(cause, BookErrorCode.NOT_FOUND);
    }

    public BookNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace, BookErrorCode.NOT_FOUND);
    }
}
