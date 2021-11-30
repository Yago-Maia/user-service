package com.portfolio.userservice.exception;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

public class ApiErrors {
    @Getter
    private final List<String> errors;

    public ApiErrors(List<String> errors) {
        this.errors = errors;
    }

    public ApiErrors(String errorMessage){
        this.errors = List.of(errorMessage);
    }
}
