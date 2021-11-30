package com.portfolio.userservice.entity;

import com.portfolio.userservice.data.vo.UserVO;
import com.portfolio.userservice.entity.enums.Role;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.modelmapper.ModelMapper;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * Modelo do usuário
 */
@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Primeiro nome do usuário
     * @return fistName
     **/
    @Column(name = "first_name", nullable = false, length = 80)
    @NotEmpty(message = "Campo firstName obrigatório.")
    private String firstName;

    /**
     * Último nome do usuário
     * @return lastName
     **/
    @Column(name = "last_name", nullable = false, length = 80)
    @NotEmpty(message = "Campo lastName obrigatório.")
    private String lastName;

    /**
     * E-mail do usuário
     * @return email
     **/
    @Column(length = 75, nullable = false, unique = true)
    @NotEmpty(message = "Campo email obrigatório.")
    private String email;

    /**
     * Senha do usuário
     * @return password
     **/
    @Column(length = 100, nullable = false)
    @NotEmpty(message = "Campo password obrigatório.")
    private String password;

    /**
     * Nível de acesso do usuário
     * @return role
     **/
    @Column(length = 20, nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    /**
     * Converte userVO para user.
     * @param userVO
     * @return user
     */
    public static User create(UserVO userVO) { return new ModelMapper().map(userVO, User.class); }
}
