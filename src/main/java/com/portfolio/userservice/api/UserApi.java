package com.portfolio.userservice.api;

import com.portfolio.userservice.data.vo.UserVO;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Validated
public interface UserApi {
    @Operation(summary = "Procurar usuário por Id", description = "Endpoint para recuperação de usuário pelo Id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuário encontrado"),
            @ApiResponse(responseCode = "401", description = "Não autorizado"),
            @ApiResponse(responseCode = "403", description = "Recurso proibido"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado") })
    @GetMapping(value = "/{id}", produces = {"application/json", "application/xml", "application/x-yaml"})
    UserVO findById(@PathVariable("id") @ApiParam("Id do usuário") Long id);

    @Operation(summary = "Buscar todos os usuários", description = "Endpoint para recuperação de todos os usuários")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuários encontrados"),
            @ApiResponse(responseCode = "401", description = "Não autorizado"),
            @ApiResponse(responseCode = "403", description = "Recurso proibido"),
            @ApiResponse(responseCode = "404", description = "Nenhum usuário cadastrado") })
    @GetMapping(produces = {"application/json", "application/xml", "application/x-yaml"})
    ResponseEntity<?> findAll(@RequestParam(value = "page", defaultValue = "0") @ApiParam("Número da página") int page,
                                     @RequestParam(value = "limit", defaultValue = "12") @ApiParam("Limite de resultado") int limit,
                                     @RequestParam(value = "direction", defaultValue = "asc") @ApiParam("Direção da ordenação") String direction);

    @Operation(summary = "Criar usuário", description = "Endpoint para criar usuário")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuário criado"),
            @ApiResponse(responseCode = "401", description = "Não autorizado"),
            @ApiResponse(responseCode = "403", description = "Recurso proibido")})
    @PostMapping(produces = {"application/json", "application/xml", "application/x-yaml"},
            consumes = {"application/json", "application/xml", "application/x-yaml"})
    @ResponseStatus(HttpStatus.CREATED)
    UserVO create(@RequestBody @Valid UserVO userVO);

    @Operation(summary = "Atualizar usuário", description = "Endpoint para editar usuário")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuário editado"),
            @ApiResponse(responseCode = "401", description = "Não autorizado"),
            @ApiResponse(responseCode = "403", description = "Recurso proibido"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado") })
    @PutMapping(produces = {"application/json", "application/xml", "application/x-yaml"},
            consumes = {"application/json", "application/xml", "application/x-yaml"})
    UserVO update(@RequestBody @Valid UserVO userVO);

    @Operation(summary = "Deletar usuário", description = "Endpoint para deletar usuário")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuário deletado"),
            @ApiResponse(responseCode = "401", description = "Não autorizado"),
            @ApiResponse(responseCode = "403", description = "Recurso proibido"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado") })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") @ApiParam("Id do usuário") Long id);
}
