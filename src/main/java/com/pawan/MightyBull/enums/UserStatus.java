package com.pawan.MightyBull.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserStatus {
    PENDING,
    ACTIVE,
    BLOCKED,
    DEACTIVATED
}
