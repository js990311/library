package com.rejs.book.domain.library.exception;

public class LibraryNotFoundException extends LibraryException{
    public LibraryNotFoundException() {
        super(LibraryErrorCode.NOT_FOUND);
    }

    public LibraryNotFoundException(String message) {
        super(message, LibraryErrorCode.NOT_FOUND);
    }

    public LibraryNotFoundException(String message, Throwable cause) {
        super(message, cause, LibraryErrorCode.NOT_FOUND);
    }

    public LibraryNotFoundException(Throwable cause) {
        super(cause, LibraryErrorCode.NOT_FOUND);
    }

    public LibraryNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace, LibraryErrorCode.NOT_FOUND);
    }
}
