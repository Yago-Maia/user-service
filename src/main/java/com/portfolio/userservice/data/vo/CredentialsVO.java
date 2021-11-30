package com.portfolio.userservice.data.vo;

import com.portfolio.userservice.entity.enums.Role;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CredentialsVO {

    @ApiModelProperty(notes = "E-mail do usuário", required = true)
    private String email;

    @ApiModelProperty(notes = "Senha do usuário", required = true)
    private String password;
}
