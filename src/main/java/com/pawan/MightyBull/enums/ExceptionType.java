package com.pawan.MightyBull.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * @author Pawan Saini
 * Created on 04/11/24.
 */
@Getter
@AllArgsConstructor
public enum ExceptionType {

    BASE_EXCEPTION(1, ErrorType.INTERNAL_SERVER, HttpStatus.INTERNAL_SERVER_ERROR),
    UNAUTHORIZED_EXCEPTION(2, ErrorType.UNAUTHORIZED, HttpStatus.UNAUTHORIZED),
    BAD_REQUEST_EXCEPTION(3, ErrorType.BAD_REQUEST, HttpStatus.BAD_REQUEST),
    INTERNAL_SERVER_EXCEPTION(4, ErrorType.INTERNAL_SERVER, HttpStatus.INTERNAL_SERVER_ERROR),
    JSON_PARSING_EXCEPTION(4, ErrorType.BAD_REQUEST, HttpStatus.BAD_REQUEST),
    CONFIGURATION_SERVICE_EXCEPTION(5, ErrorType.EXTERNAL_SERVER, HttpStatus.BAD_GATEWAY),
    OPERATION_NOT_ALLOWED_EXCEPTION(6, ErrorType.INTERNAL_SERVER, HttpStatus.INTERNAL_SERVER_ERROR),
    NOT_FOUND_EXCEPTION(7, ErrorType.NOT_FOUND, HttpStatus.NOT_FOUND),;

    private final Integer errorCode;
    private final ErrorType errorType;
    private final HttpStatus httpStatus;
}
