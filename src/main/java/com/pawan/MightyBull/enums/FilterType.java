package com.pawan.MightyBull.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FilterType {
    EQUAL,
    NOT_EQUAL,
    GREATER_THAN,
    GREATER_THAN_OR_EQUAL,
    LESS_THAN,
    LESS_THAN_OR_EQUAL,
    BETWEEN,
    NOT_BETWEEN,
    LIKE,
    NOT_LIKE,
    ILIKE,
    NOT_ILIKE,
    IN,
    NOT_IN,
    IS_NULL,
    IS_NOT_NULL
}
