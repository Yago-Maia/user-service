package com.portfolio.userservice.data.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenVO {

    @ApiModelProperty(notes = "E-mail do usuário", required = true)
    private String email;

    @ApiModelProperty(notes = "Token do usuário", required = true)
    private String token;
}
