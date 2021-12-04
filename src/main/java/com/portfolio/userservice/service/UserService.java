package com.portfolio.userservice.service;

import com.portfolio.userservice.data.vo.UserVO;
import com.portfolio.userservice.entity.User;
import com.portfolio.userservice.exception.InvalidPasswordException;
import com.portfolio.userservice.exception.ResourceNotFoundException;
import com.portfolio.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, @Lazy PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    public UserVO create(UserVO userVO) {
        userVO.setPassword(passwordEncoder.encode(userVO.getPassword()));
        return UserVO.create(userRepository.save(User.create(userVO)));
    }

    public Page<UserVO> findAll(Pageable pageable) {
        var page = userRepository.findAll(pageable);
        return page.map(this::convertToUserVO);
    }

    private UserVO convertToUserVO(User user) { return UserVO.create(user); }

    public UserVO findById(Long id) {
        var entity = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Nenhum usuário encontrado para este ID."));
        return UserVO.create(entity);
    }

    public UserVO update(UserVO userVO) {
        final Optional<User> optionalUser = userRepository.findById(userVO.getId());

        if(optionalUser.isEmpty()) {
            throw new ResourceNotFoundException("Nenhum usuário encontrado para este ID.");
        }

        return UserVO.create(userRepository.save(User.create(userVO)));
    }

    public void delete(Long id) {
        var entity = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Nenhum usuário encontrado para este ID."));
        userRepository.delete(entity);
    }

    public UserDetails authenticate(User user) {
        UserDetails userDetails = loadUserByUsername(user.getEmail());
        if(passwordEncoder.matches(user.getPassword(), userDetails.getPassword())) {
            return userDetails;
        }

        throw new InvalidPasswordException();
    }

    public UserVO findByEmail(String email) {
        return UserVO.create(userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Nenhum usuário encontrado para este ID.")));
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserVO user = this.findByEmail(email);

        String[] role = new String[]{user.getRole().toString()};

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .roles(role)
                .build();
    }
}
