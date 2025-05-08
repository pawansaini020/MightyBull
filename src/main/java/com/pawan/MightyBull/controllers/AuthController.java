package com.pawan.MightyBull.controllers;

import com.pawan.MightyBull.constants.ApiEndpointConstant;
import com.pawan.MightyBull.dto.response.SuccessResponse;
import com.pawan.MightyBull.dto.user.SignupRequest;
import com.pawan.MightyBull.services.auth.AuthService;
import com.pawan.MightyBull.utils.GsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = ApiEndpointConstant.Auth.BASE)
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(value = ApiEndpointConstant.Auth.SIGNUP)
    public SuccessResponse<?> signup(@RequestBody SignupRequest signupRequest) {
        log.info("AUTH_CONTROLLER ::: Received request for user signup for: {}", GsonUtils.getGson().toJson(signupRequest));
        return new SuccessResponse<>(authService.userSignup(signupRequest));
    }

    @PostMapping(value = ApiEndpointConstant.Auth.VERIFY_OTP)
    public SuccessResponse<?> verifyOTP(@RequestParam String username,
                                        @RequestParam String password,
                                        @RequestParam String otp) {
        log.info("AUTH_CONTROLLER ::: Received request for verifying user otp for: {}, {}, {}", username, password, otp);
        return new SuccessResponse<>(authService.verifyOTP(username, password, otp));
    }

    @PostMapping(value = ApiEndpointConstant.Auth.LOGIN)
    public SuccessResponse<?> login(@RequestParam String username,
                                    @RequestParam String password) {
        log.info("AUTH_CONTROLLER ::: Received request for logging user: {}, {}", username, password);
        return new SuccessResponse<>(authService.userLogin(username, password));
    }

    @PostMapping(value = ApiEndpointConstant.Auth.RESET_PASSWORD)
    public SuccessResponse<?> resetPassword(@RequestParam String username,
                                    @RequestParam String newPassword) {
        log.info("AUTH_CONTROLLER ::: Received request for resetting user password: {}, {}", username, newPassword);
        return new SuccessResponse<>(authService.resetPassword(username, newPassword));
    }
}
