package com.portfolio.userservice.data.vo;

import com.portfolio.userservice.entity.User;
import com.portfolio.userservice.entity.enums.Role;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserVO extends RepresentationModel<UserVO> implements Serializable {
    @ApiModelProperty(notes = "Identificação do usuário", required = true)
    private Long id;

    @ApiModelProperty(notes = "Primeiro nome do usuário", required = true)
    @NotEmpty(message = "Campo firstName obrigatório.")
    private String firstName;

    @ApiModelProperty(notes = "Último nome do usuário", required = true)
    @NotEmpty(message = "Campo lastName obrigatório.")
    private String lastName;

    @ApiModelProperty(notes = "E-mail do usuário", required = true)

    @NotEmpty(message = "Campo email obrigatório.")
    private String email;

    @ApiModelProperty(notes = "Senha do usuário", required = true)
    @NotEmpty(message = "Campo password obrigatório.")
    private String password;

    @ApiModelProperty(notes = "Role do usuário")
    private Role role;

    public static UserVO create(User user) { return new ModelMapper().map(user, UserVO.class); }
}
