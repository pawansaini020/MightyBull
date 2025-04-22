package com.pawan.MightyBull.authserver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pawan.MightyBull.dao.UserDao;
import com.pawan.MightyBull.dto.response.ErrorResponse;
import com.pawan.MightyBull.dto.response.FailureResponse;
import com.pawan.MightyBull.entity.UserEntity;
import com.pawan.MightyBull.enums.ExceptionType;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    private final JwtManager jwtManager;
    private final UserDao userDao;

    public JwtFilter(JwtManager jwtManager,
                     UserDao userDao) {
        this.jwtManager = jwtManager;
        this.userDao = userDao;
    }

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        try {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            String authorizationHeader = httpRequest.getHeader("Authorization");

            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                String token = authorizationHeader.substring(7);
                String username = jwtManager.extractUsername(token);

                if (username != null && jwtManager.validateToken(token, username)) {
                    UserEntity user = userDao.getByEmail(username).orElse(null);
                    if(user == null) {
                        writeResponse(response, "User Not Found", ExceptionType.NOT_FOUND_EXCEPTION);
                        return;
                    }
                    UserDetails userDetails = User.withUsername(username).password(user.getPassword()).roles(user.getRole().name()).build();
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                } else {
                    writeResponse(response, "Access token is invalid.", ExceptionType.NOT_FOUND_EXCEPTION);
                    return;
                }
            }
//            else {
//                writeResponse(response, "Access token is missing.", ExceptionType.NOT_FOUND_EXCEPTION);
//                return;
//            }
        } catch (ExpiredJwtException | MalformedJwtException | SignatureException | UnsupportedJwtException e) {
            writeResponse(response, e.getMessage(), ExceptionType.UNAUTHORIZED_EXCEPTION);
            return;
        } catch (Exception e) {
            writeResponse(response, e.getMessage(), ExceptionType.INTERNAL_SERVER_EXCEPTION);
            return;
        }

        chain.doFilter(request, response);
    }

    private void writeResponse(HttpServletResponse response, String message, ExceptionType exceptionType) throws IOException {
        ResponseEntity<FailureResponse<Object>> responseEntity = buildExceptionResponse(message, exceptionType);
        response.setStatus(responseEntity.getStatusCodeValue());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        ObjectMapper objectMapper = new ObjectMapper();
        response.getWriter().write(objectMapper.writeValueAsString(responseEntity.getBody()));
    }

    private ResponseEntity<FailureResponse<Object>> buildExceptionResponse(String message, ExceptionType exceptionType) {
        log.info("Authentication failed for token :: {}", message);
        ErrorResponse errorResponse = ErrorResponse.builder()
                .errorCode(exceptionType.getErrorCode())
                .errorType(exceptionType.getErrorType().getValue())
                .errorMessage(message)
                .build();
        FailureResponse<Object> response = new FailureResponse<>(Collections.singletonList(errorResponse));
        return new ResponseEntity<>(response, exceptionType.getHttpStatus());
    }
}
