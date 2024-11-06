package com.pawan.MightyBull.exception;

import com.pawan.MightyBull.enums.ErrorType;
import com.pawan.MightyBull.enums.ExceptionType;
import org.springframework.http.HttpStatus;

/**
 * @author Pawan Saini
 * Created on 04/11/24.
 */
public class InternalServerException extends BaseException {
    private static final Integer ERROR_CODE = ExceptionType.INTERNAL_SERVER_EXCEPTION.getErrorCode();
    private static final ErrorType ERROR_TYPE = ExceptionType.INTERNAL_SERVER_EXCEPTION.getErrorType();
    private static final HttpStatus HTTP_STATUS = ExceptionType.INTERNAL_SERVER_EXCEPTION.getHttpStatus();

    public InternalServerException() {
        super(ERROR_CODE, ERROR_TYPE, HTTP_STATUS);
    }

    public InternalServerException(String message) {
        super(ERROR_CODE, ERROR_TYPE, HTTP_STATUS, message);
    }

    public InternalServerException(String message, Throwable cause) {
        super(ERROR_CODE, ERROR_TYPE, HTTP_STATUS, message, cause);
    }
}
