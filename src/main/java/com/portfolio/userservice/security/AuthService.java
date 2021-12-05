package com.portfolio.userservice.security;

import com.portfolio.userservice.data.vo.UserVO;
import com.portfolio.userservice.entity.User;
import com.portfolio.userservice.exception.InvalidPasswordException;
import com.portfolio.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements UserDetailsService {

    private final UserService userService;
    public final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthService(@Lazy UserService userService, @Lazy PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }


    public UserDetails authenticate(User user) {
        UserDetails userDetails = this.loadUserByUsername(user.getEmail());
        if (passwordEncoder.matches(user.getPassword(), userDetails.getPassword())) {
            return userDetails;
        }

        throw new InvalidPasswordException();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserVO user = userService.findByEmail(email);

        String[] role = new String[]{user.getRole().toString()};

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .roles(role)
                .build();
    }

    public UserSS loadUserByUsernameAndRole(String username, String role, Long id) {
        return UserSS.builder()
                .username(username)
                .password("")
                .roles(role)
                .idUser(id)
                .build();
    }
}