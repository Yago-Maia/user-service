package com.portfolio.userservice.api;

import com.portfolio.userservice.data.vo.CredentialsVO;
import com.portfolio.userservice.data.vo.TokenVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Validated
public interface AuthApi {
    @Operation(summary = "Autenticar o usuário", description = "Autentica o usuário e recupera o token jwt para utilização na api")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Autenticado"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado") })
    @PostMapping(produces = {"application/json", "application/xml", "application/x-yaml"},
            consumes = {"application/json", "application/xml", "application/x-yaml"})
    public TokenVO authenticate(@RequestBody CredentialsVO credentialsVO);
}
