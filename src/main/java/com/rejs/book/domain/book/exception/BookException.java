package com.rejs.book.domain.book.exception;

import com.rejs.book.domain.book.exception.code.BookErrorCode;

public class BookException extends RuntimeException{
    private BookErrorCode code;

    public BookException(BookErrorCode code) {
        this.code = code;
    }

    public BookException(String message, BookErrorCode code) {
        super(message);
        this.code = code;
    }

    public BookException(String message, Throwable cause, BookErrorCode code) {
        super(message, cause);
        this.code = code;
    }

    public BookException(Throwable cause, BookErrorCode code) {
        super(cause);
        this.code = code;
    }

    public BookException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, BookErrorCode code) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.code = code;
    }
}
