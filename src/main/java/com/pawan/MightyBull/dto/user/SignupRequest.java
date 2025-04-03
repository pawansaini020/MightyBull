package com.pawan.MightyBull.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pawan.MightyBull.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SignupRequest {

    @NonNull
    private String name;

    @NonNull
    private String email;

    @NonNull
    private String phone;

    @NonNull
    private String password;

    private UserRole role = UserRole.USER;
}
