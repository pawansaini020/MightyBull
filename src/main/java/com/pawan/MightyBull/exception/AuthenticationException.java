package com.pawan.MightyBull.exception;

import com.pawan.MightyBull.enums.ErrorType;
import com.pawan.MightyBull.enums.ExceptionType;
import org.springframework.http.HttpStatus;

public class AuthenticationException extends BaseException {

    private static final Integer ERROR_CODE = ExceptionType.UNAUTHORIZED_EXCEPTION.getErrorCode();
    private static final ErrorType ERROR_TYPE = ExceptionType.UNAUTHORIZED_EXCEPTION.getErrorType();
    private static final HttpStatus HTTP_STATUS = ExceptionType.UNAUTHORIZED_EXCEPTION.getHttpStatus();

    public AuthenticationException() {
        super(ERROR_CODE, ERROR_TYPE, HTTP_STATUS);
    }

    public AuthenticationException(String message) {
        super(ERROR_CODE, ERROR_TYPE, HTTP_STATUS, message);
    }

    public AuthenticationException(String message, Throwable cause) {
        super(ERROR_CODE, ERROR_TYPE, HTTP_STATUS, message, cause);
    }
}
