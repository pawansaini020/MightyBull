package com.pawan.MightyBull.services.auth;

import com.pawan.MightyBull.dto.user.SignupRequest;
import com.pawan.MightyBull.services.communication.ICommunicationService;
import com.pawan.MightyBull.services.communication.OtpEmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Date;

@Service
@Slf4j
public class OtpService {

    private static final SecureRandom random = new SecureRandom();
    private static final Integer OTP_EXPIRY_TIME = 10;

    private final ICommunicationService communicationService;

    @Autowired
    public OtpService(OtpEmailService otpEmailService) {
        this.communicationService = otpEmailService;
    }

    public String generateAndSendOtp(SignupRequest signupRequest) {
        String otp = generateOtp();
        signupRequest.setOtp(otp);
        communicationService.send(signupRequest);
        log.info("Otp successfully send to email: {}, {}", signupRequest.getEmail(), otp);
        return otp;
    }

    private String generateOtp() {
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }

    public Date getOtpExpiry() {
        return new Date(System.currentTimeMillis() + OTP_EXPIRY_TIME * 60 * 1000);
    }
}
