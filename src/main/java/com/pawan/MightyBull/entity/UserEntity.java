package com.pawan.MightyBull.entity;

import com.pawan.MightyBull.entity.base.BaseEntity;
import com.pawan.MightyBull.enums.UserRole;
import com.pawan.MightyBull.enums.UserStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_info")
@Builder
public class UserEntity extends BaseEntity<Long> {

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private UserRole role;

    @Column(name = "status")
    private UserStatus status;

    @Column(name = "otp")
    private String otp;

    @Column(name = "otp_expiry")
    private Date otpExpiry;
}
