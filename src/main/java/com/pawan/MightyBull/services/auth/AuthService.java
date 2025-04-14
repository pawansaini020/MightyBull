package com.pawan.MightyBull.services.auth;

//import com.pawan.MightyBull.authserver.JwtManager;
import com.pawan.MightyBull.dao.UserDao;
import com.pawan.MightyBull.dto.user.AuthResponse;
import com.pawan.MightyBull.dto.user.SignupRequest;
import com.pawan.MightyBull.entity.UserEntity;
import com.pawan.MightyBull.enums.UserStatus;
import com.pawan.MightyBull.exception.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;
import java.util.Optional;

@Service
public class AuthService {

    private final UserDao userDao;
//    private final PasswordEncoder passwordEncoder;
//    private final JwtManager jwtManager;
    private final OtpService otpService;

    @Autowired
    public AuthService(UserDao userDao,
//                       PasswordEncoder passwordEncoder,
//                       JwtManager jwtManager,
                       OtpService otpService) {
        this.userDao = userDao;
//        this.passwordEncoder = passwordEncoder;
//        this.jwtManager = jwtManager;
        this.otpService = otpService;
    }

    public String userSignup(SignupRequest signupRequest) {
        Optional<UserEntity> user = userDao.getByEmail(signupRequest.getEmail());
        if (user.isPresent()) {
            throw new AuthenticationException("Oops! This email is already taken. Try logging in instead.");
        }

        String otp = otpService.generateAndSendOtp(signupRequest);
        Date otpExpiry = otpService.getOtpExpiry();

        UserEntity newUser = UserEntity.builder()
                .name(signupRequest.getName())
                .email(signupRequest.getEmail())
                .phone(signupRequest.getPhone())
//                .password(passwordEncoder.encode(signupRequest.getPassword()))
                .role(signupRequest.getRole())
                .status(UserStatus.PENDING)
                .otp(otp)
                .otpExpiry(otpExpiry)
                .build();
        userDao.save(newUser);
        return "OTP sent to your phone. Verify to activate your account.";
    }

    public String verifyOTP(String username, String password, String otp) {
        UserEntity user = userDao.getByEmail(username).orElse(null);
        if (Objects.isNull(user)) {
            throw new AuthenticationException("Authentication failed! User not found.");
        }
        if (user.getOtp() == null || user.getOtpExpiry().before(new Date())) {
            throw new AuthenticationException("OTP expired. Please request a new one.");
        }
        if (!user.getOtp().equals(otp)) {
            throw new RuntimeException("Invalid OTP. Please try again.");
        }

        user.setStatus(UserStatus.ACTIVE);
//        user.setPassword(passwordEncoder.encode(password));
        user.setOtp(null);
        user.setOtpExpiry(null);
        userDao.save(user);
        return "Account successfully verified. You can now log in.";
    }

    public AuthResponse userLogin(String username, String password) {
        UserEntity user = userDao.getByEmail(username).orElse(null);
        if (Objects.isNull(user)) {
            throw new AuthenticationException("Authentication failed! Check your email and password and try again");
        }

//        if (!passwordEncoder.matches(password, user.getPassword())) {
//            throw new AuthenticationException("Authentication failed! Check your email and password and try again");
//        }

        if (UserStatus.PENDING.equals(user.getStatus())) {
            throw new AuthenticationException("Authentication failed! User is not verified to login, please reset password and verify.");
        }

        if (UserStatus.BLOCKED.equals(user.getStatus()) || UserStatus.DEACTIVATED.equals(user.getStatus())) {
            throw new AuthenticationException("Authentication failed! User doesn't have permission to login, please contact to admin.");
        }

//        String token = jwtManager.generateToken(user.getEmail(), user.getRole().name());
        return AuthResponse.builder()
                .name(user.getName())
                .role(user.getRole().name())
                .token("token")
                .refreshToken("token")
                .build();
    }

    public String resetPassword(String username, String newPassword) {
        UserEntity user = userDao.getByEmail(username).orElse(null);
        if (Objects.isNull(user)) {
            throw new AuthenticationException("Authentication failed! User not found.");
        }
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setEmail(username);
        signupRequest.setName(user.getName());
        String otp = otpService.generateAndSendOtp(signupRequest);
        Date otpExpiry = otpService.getOtpExpiry();

        user.setOtp(otp);
        user.setOtpExpiry(otpExpiry);
        userDao.save(user);
        return "OTP sent to your phone. Verify to resetting password.";
    }
}
