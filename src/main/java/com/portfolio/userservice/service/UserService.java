package com.portfolio.userservice.service;

import com.portfolio.userservice.data.vo.UserVO;
import com.portfolio.userservice.entity.User;
import com.portfolio.userservice.exception.NotAllowedException;
import com.portfolio.userservice.exception.ResourceNotFoundException;
import com.portfolio.userservice.repository.UserRepository;
import com.portfolio.userservice.security.AuthService;
import com.portfolio.userservice.security.UserSS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

import static com.portfolio.userservice.constants.Messages.NOT_ALLOWED_USER_UPDATE;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final AuthService authService;

    @Autowired
    public UserService(UserRepository userRepository, AuthService authService) {
        this.authService = authService;
        this.userRepository = userRepository;
    }

    public UserVO create(UserVO userVO) {
        userVO.setPassword(authService.passwordEncoder.encode(userVO.getPassword()));
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

        this.isAllowed(entity.getId());

        return UserVO.create(entity);
    }

    public UserVO update(UserVO userVO) {
        final Optional<User> optionalUser = userRepository.findById(userVO.getId());

        if(optionalUser.isEmpty()) {
            throw new ResourceNotFoundException("Nenhum usuário encontrado para este ID.");
        }

        this.isAllowed(optionalUser.get().getId());

        if (userVO.getPassword() != null) {
            userVO.setPassword(authService.passwordEncoder.encode(userVO.getPassword()));
        }

        return UserVO.create(userRepository.save(User.create(userVO)));
    }

    public void delete(Long id) {
        var entity = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Nenhum usuário encontrado para este ID."));

        this.isAllowed(entity.getId());

        userRepository.delete(entity);
    }

    public UserVO findByEmail(String email) {
        return UserVO.create(userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Nenhum usuário encontrado para este ID.")));
    }

    private void isAllowed(Long id) {
        UserSS userSS = UserSS.authenticated();
        assert userSS != null;
        if(!Objects.equals(userSS.getIdUser(), id)) {
            throw new NotAllowedException(NOT_ALLOWED_USER_UPDATE);
        }
    }
}
