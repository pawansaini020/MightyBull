//package com.pawan.MightyBull.authserver;
//
//import com.pawan.MightyBull.dao.UserDao;
//import com.pawan.MightyBull.entity.UserEntity;
//import io.jsonwebtoken.ExpiredJwtException;
//import io.jsonwebtoken.MalformedJwtException;
//import io.jsonwebtoken.SignatureException;
//import io.jsonwebtoken.UnsupportedJwtException;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//
//@Component
//public class JwtFilter extends OncePerRequestFilter {
//
//    private final JwtManager jwtManager;
//    private final UserDao userDao;
//
//    public JwtFilter(JwtManager jwtManager,
//                     UserDao userDao) {
//        this.jwtManager = jwtManager;
//        this.userDao = userDao;
//    }
//
//    @Override
//    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
//            throws IOException, ServletException {
//        try {
//            HttpServletRequest httpRequest = (HttpServletRequest) request;
//            String authorizationHeader = httpRequest.getHeader("Authorization");
//
//            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
//                String token = authorizationHeader.substring(7);
//                String username = jwtManager.extractUsername(token);
//
//                if (username != null && jwtManager.validateToken(token, username)) {
//                    UserEntity user = userDao.getByEmail(username).orElse(null);
//                    if(user == null) {
//                        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
//                        response.getWriter().write("User not found. Please login again.");
//                        return;
//                    }
//                    UserDetails userDetails = User.withUsername(username).password(user.getPassword()).roles(user.getRole().name()).build();
//                    UsernamePasswordAuthenticationToken authToken =
//                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//                    SecurityContextHolder.getContext().setAuthentication(authToken);
//                }
//            }
//        } catch (ExpiredJwtException e) {
//            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            response.getWriter().write("Access token expired. Please login again.");
//            return;
//        } catch (MalformedJwtException | SignatureException | UnsupportedJwtException e) {
//            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            response.getWriter().write("Invalid access token.");
//            return;
//        } catch (Exception e) {
//            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//            response.getWriter().write("Authentication error.");
//            return;
//        }
//
//        chain.doFilter(request, response);
//    }
//}
