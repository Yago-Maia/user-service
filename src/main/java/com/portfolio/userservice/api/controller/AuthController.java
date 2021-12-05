package com.portfolio.userservice.api.controller;

import com.portfolio.userservice.api.AuthApi;
import com.portfolio.userservice.data.vo.CredentialsVO;
import com.portfolio.userservice.data.vo.TokenVO;
import com.portfolio.userservice.entity.User;
import com.portfolio.userservice.exception.InvalidPasswordException;
import com.portfolio.userservice.security.AuthService;
import com.portfolio.userservice.security.jwt.JwtService;
import io.swagger.annotations.Api;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/auth")
@Api("Api de autenticação")
public class AuthController implements AuthApi {

    private final JwtService jwtService;
    private final AuthService authService;

    public AuthController(JwtService jwtService, AuthService authService) {
        this.jwtService = jwtService;
        this.authService = authService;
    }


    @Override
    public TokenVO authenticate(CredentialsVO credentialsVO) {
        try {
            User user = User.builder()
                    .email(credentialsVO.getEmail())
                    .password(credentialsVO.getPassword())
                    .build();
            UserDetails userAuthenticated = authService.authenticate(user);

            String token = jwtService.generateToken(user);

            return new TokenVO(user.getEmail(), token);
        } catch (UsernameNotFoundException | InvalidPasswordException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }
}
