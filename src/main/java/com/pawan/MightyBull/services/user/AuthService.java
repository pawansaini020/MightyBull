package com.pawan.MightyBull.services.user;

import com.pawan.MightyBull.authserver.JwtManager;
import com.pawan.MightyBull.dao.UserDao;
import com.pawan.MightyBull.dto.user.AuthResponse;
import com.pawan.MightyBull.dto.user.SignupRequest;
import com.pawan.MightyBull.entity.UserEntity;
import com.pawan.MightyBull.exception.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class AuthService {

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;
    private final JwtManager jwtManager;

    @Autowired
    public AuthService(UserDao userDao,
                       PasswordEncoder passwordEncoder,
                       JwtManager jwtManager) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
        this.jwtManager = jwtManager;
    }

    public void userSignup(SignupRequest signupRequest) {
        Optional<UserEntity> user = userDao.getByEmail(signupRequest.getEmail());
        if (user.isPresent()) {
            throw new AuthenticationException("Oops! This email is already taken. Try logging in instead.");
        }

        UserEntity newUser = UserEntity.builder()
                .name(signupRequest.getName())
                .email(signupRequest.getEmail())
                .phone(signupRequest.getPhone())
                .password(passwordEncoder.encode(signupRequest.getPassword()))
                .role(signupRequest.getRole())
                .build();
        userDao.save(newUser);
    }

    public AuthResponse userLogin(String username, String password) {
        UserEntity user = userDao.getByEmail(username).orElse(null);
        if (Objects.isNull(user)) {
            throw new AuthenticationException("Authentication failed! Check your email and password and try again");
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new AuthenticationException("Authentication failed! Check your email and password and try again");
        }

        String token = jwtManager.generateToken(user.getEmail(), user.getRole().name());
        return new AuthResponse(token, token, user.getRole().name());
    }
}
