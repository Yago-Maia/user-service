package com.portfolio.userservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class InvalidPasswordException extends RuntimeException {
    public InvalidPasswordException() {
        super("Senha inválida.");
    }
}
