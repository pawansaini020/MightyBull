package com.pawan.MightyBull.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Pawan Saini
 * Created on 04/11/24.
 */
@Getter
@AllArgsConstructor
public enum ErrorType {

    UNKNOWN(0, "Unknown"),
    UNAUTHORIZED(1, "UNAUTHORIZED"),
    BAD_REQUEST(2, "Bad Request"),
    INTERNAL_SERVER(3, "Internal Server"),
    EXTERNAL_SERVER(4, "External Server"),
    NOT_FOUND(5, "Not Found"),
    UNPROCESSABLE_ENTITY(6, "Unprocessable Entity"),
    VALIDATION(7, "Validation");

    private final Integer index;
    private final String value;
}
