package com.portfolio.userservice.security.jwt;

import com.portfolio.userservice.data.vo.UserVO;
import com.portfolio.userservice.entity.User;
import com.portfolio.userservice.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;

@Service
public class JwtService {

    @Value("${security.jwt.expiration}")
    private String expiration;

    @Value("${security.jwt.signature-key}")
    private String signatureKey;

    private final UserService userService;

    @Autowired
    public JwtService(UserService userService) {
        this.userService = userService;
    }

    public String generateToken(User user) {
        long expString = Long.parseLong(expiration);
        LocalDateTime dateHourExpiration = LocalDateTime.now().plusMinutes(expString);
        Date date = Date.from(dateHourExpiration.atZone(ZoneId.systemDefault()).toInstant());

        HashMap<String, Object> claims = new HashMap<>();

        UserVO userVO = userService.findByEmail(user.getEmail());
        claims.put("role", userVO.getRole().toString());
        claims.put("idUser", userVO.getId());

        return Jwts
                .builder()
                .setClaims(claims)
                .setSubject(user.getEmail())
                .setExpiration(date)
                .signWith(SignatureAlgorithm.HS512, signatureKey)
                .compact();
    }


    private Claims getClaims(String token) throws ExpiredJwtException {
        return Jwts
                .parser()
                .setSigningKey(signatureKey)
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isTokenValid(String token) {
        try {
            LocalDateTime date = getClaims(token).getExpiration().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            return !LocalDateTime.now().isAfter(date);
        } catch (Exception ex) {
            return false;
        }
    }

    public String getEmailUser(String token) throws ExpiredJwtException {
        return (String) getClaims(token).getSubject();
    }
}
