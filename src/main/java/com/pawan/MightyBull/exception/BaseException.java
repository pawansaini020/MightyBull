package com.pawan.MightyBull.exception;

import com.pawan.MightyBull.enums.ErrorType;
import com.pawan.MightyBull.enums.ExceptionType;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * @author Pawan Saini
 * Created on 04/11/24.
 */
@Getter
public abstract class BaseException extends RuntimeException {

    private final Integer errorCode;
    private final String errorType;
    private final HttpStatus httpStatus;

    protected BaseException() {
        super();
        this.errorCode = ExceptionType.BASE_EXCEPTION.getErrorCode();
        this.errorType = ExceptionType.BASE_EXCEPTION.getErrorType().getValue();
        this.httpStatus = ExceptionType.BASE_EXCEPTION.getHttpStatus();
    }

    protected BaseException(String message) {
        super(message);
        this.errorCode = ExceptionType.BASE_EXCEPTION.getErrorCode();
        this.errorType = ExceptionType.BASE_EXCEPTION.getErrorType().getValue();
        this.httpStatus = ExceptionType.BASE_EXCEPTION.getHttpStatus();
    }

    protected BaseException(String message, Throwable cause) {
        super(message, cause);
        this.errorCode = ExceptionType.BASE_EXCEPTION.getErrorCode();
        this.errorType = ExceptionType.BASE_EXCEPTION.getErrorType().getValue();
        this.httpStatus = ExceptionType.BASE_EXCEPTION.getHttpStatus();
    }

    protected BaseException(Integer errorCode, ErrorType errorType, HttpStatus httpStatus) {
        super();
        this.errorCode = errorCode;
        this.errorType = errorType.getValue();
        this.httpStatus = httpStatus;
    }

    protected BaseException(Integer errorCode, ErrorType errorType, HttpStatus httpStatus, String message) {
        super(message);
        this.errorCode = errorCode;
        this.errorType = errorType.getValue();
        this.httpStatus = httpStatus;
    }
    protected BaseException(Integer errorCode, ErrorType errorType, HttpStatus httpStatus, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
        this.errorType = errorType.getValue();
        this.httpStatus = httpStatus;
    }
}
