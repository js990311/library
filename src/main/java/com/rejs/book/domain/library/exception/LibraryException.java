package com.rejs.book.domain.library.exception;

public class LibraryException extends RuntimeException{
    private LibraryErrorCode code;

    public LibraryException(LibraryErrorCode code) {
        this.code = code;
    }

    public LibraryException(String message, LibraryErrorCode code) {
        super(message);
        this.code = code;
    }

    public LibraryException(String message, Throwable cause, LibraryErrorCode code) {
        super(message, cause);
        this.code = code;
    }

    public LibraryException(Throwable cause, LibraryErrorCode code) {
        super(cause);
        this.code = code;
    }

    public LibraryException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, LibraryErrorCode code) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.code = code;
    }
}
