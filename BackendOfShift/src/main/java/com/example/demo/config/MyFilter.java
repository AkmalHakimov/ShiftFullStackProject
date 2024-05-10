package com.example.demo.config;


import com.example.demo.repository.UserRepo;
import com.example.demo.service.security.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class MyFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserRepo userRepo;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
            String header = "Authorization";
             String token = request.getHeader(header);
             if(token!=null && !token.isEmpty()){
                 String subject = jwtService.extractSubjectFromJwt(token);
                     UserDetails userDetails = userRepo.findById(UUID.fromString(subject)).orElseThrow();
                 UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                         new UsernamePasswordAuthenticationToken(
                                 userDetails,
                                 null,
                                 userDetails.getAuthorities()
                         );
                 SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
             }
             filterChain.doFilter(request,response);
    }
}
