package com.example.demo.service.security;

import com.example.demo.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class JwtService {
    public String generateJwtToken(User user){
        UUID id = user.getId();
        int expirationInYears = 1;
        LocalDate now = LocalDate.now();
        LocalDate expirationDate = now.plusYears(expirationInYears);
        Date expirationTime = Date.from(expirationDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Map<String,Object> claims = new HashMap<>();
        claims.put("phone",user.getPhone());
        claims.put("firstName",user.getFirstName());
        claims.put("lastName",user.getLastName());
        String jwt = Jwts.builder()
                .setExpiration(expirationTime)
                .setIssuedAt(new Date())
                .setSubject(id.toString())
                .addClaims(claims)
                .signWith(getSignKey())
                .compact();
        return jwt;
    }
    public String generateJwtRefreshToken(User user){
        UUID id = user.getId();
        String jwt = Jwts.builder()
                .setExpiration(new Date(System.currentTimeMillis() + 1000*60*60)).setIssuedAt(new Date())
                .setSubject(id.toString())
                .signWith(getSignKey())
                .compact();
        return jwt;
    }

    private Key getSignKey(){
        byte[] keyBytes = Decoders.BASE64.decode("4e12d39457a85ba87cc0effa7e04ed091ad531b3ac182209370e49f263813699");
        return Keys.hmacShaKeyFor(keyBytes);
    }
    public String extractSubjectFromJwt(String token) {
        Claims body = Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return body.getSubject();
    }
}
