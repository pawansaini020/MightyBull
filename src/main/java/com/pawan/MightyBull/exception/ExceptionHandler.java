package com.pawan.MightyBull.exception;

import com.pawan.MightyBull.dto.grow.response.ErrorResponse;
import com.pawan.MightyBull.dto.grow.response.FailureResponse;
import com.pawan.MightyBull.enums.ExceptionType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Collections;

/**
 * @author Pawan Saini
 * Created on 04/11/24.
 */
@Slf4j
@RestControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<FailureResponse<Object>> handleHttpMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException exception) {
        ExceptionType exceptionType = ExceptionType.BAD_REQUEST_EXCEPTION;
        return buildExceptionResponse(exception, exceptionType.getErrorCode(), exceptionType.getErrorType().getValue(), exceptionType.getHttpStatus());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(BaseException.class)
    public ResponseEntity<FailureResponse<Object>> handleBaseException(BaseException exception) {
        return buildExceptionResponse(exception, exception.getErrorCode(), exception.getErrorType(), exception.getHttpStatus());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public ResponseEntity<FailureResponse<Object>> handleInternalException(Exception exception) {
        ExceptionType exceptionType = ExceptionType.INTERNAL_SERVER_EXCEPTION;
        return buildExceptionResponse(exception, exceptionType.getErrorCode(), exceptionType.getErrorType().getValue(), exceptionType.getHttpStatus());
    }

    private ResponseEntity<FailureResponse<Object>> buildExceptionResponse(
            Exception exception, Integer errorCode, String errorType, HttpStatus status) {
        log.error("Error in service :: {} :: {}", exception.getClass().getSimpleName(), exception.getMessage(), exception);
        ErrorResponse errorResponse = ErrorResponse.builder()
                .errorCode(errorCode)
                .errorType(errorType)
                .errorMessage(exception.getMessage())
                .build();
        FailureResponse<Object> response = new FailureResponse(Collections.singletonList(errorResponse));
        return new ResponseEntity<>(response, status);
    }
}
