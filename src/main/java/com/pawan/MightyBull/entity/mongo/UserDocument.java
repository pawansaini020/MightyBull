package com.pawan.MightyBull.entity.mongo;

import com.pawan.MightyBull.entity.base.BaseDocument;
import com.pawan.MightyBull.enums.UserRole;
import com.pawan.MightyBull.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Document(collection = "user_info")
public class UserDocument extends BaseDocument<Long> {

    private String name;

    @Indexed(unique = true)
    private String email;

    private String phone;

    private String password;

    private UserRole role;

    private UserStatus status;

    private String otp;

    private Date otpExpiry;
}
