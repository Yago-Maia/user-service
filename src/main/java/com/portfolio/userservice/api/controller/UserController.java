package com.portfolio.userservice.api.controller;

import com.portfolio.userservice.api.UserApi;
import com.portfolio.userservice.data.vo.UserVO;
import com.portfolio.userservice.service.UserService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Api("Api de usários")
public class UserController implements UserApi {

    private final UserService userService;
    private final PagedResourcesAssembler<UserVO> assembler;

    @Override
    public UserVO findById(Long id) {
        UserVO userVO = userService.findById(id);
        userVO.add(linkTo(methodOn(UserController.class).findById(id)).withSelfRel());
        return userVO;
    }

    @Override
    public ResponseEntity<?> findAll(int page, int limit, String direction) {

        var sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, "firstName"));

        Page<UserVO> users = userService.findAll(pageable);
        users.stream()
                .forEach(u -> u.add(linkTo(methodOn(UserController.class).findById(u.getId())).withSelfRel()));
        PagedModel<EntityModel<UserVO>> pagedModel = assembler.toModel(users);

        return new ResponseEntity<>(pagedModel, HttpStatus.OK);
    }

    @Override
    public UserVO create(UserVO userVO) {
        UserVO userVOSaved = userService.create(userVO);
        userVOSaved.add(linkTo(methodOn(UserController.class).findById(userVOSaved.getId())).withSelfRel());

        return userVOSaved;
    }

    @Override
    public UserVO update(UserVO userVO) {
        UserVO userVOSaved = userService.update(userVO);
        userVOSaved.add(linkTo(methodOn(UserController.class).findById(userVOSaved.getId())).withSelfRel());

        return userVOSaved;
    }

    @Override
    public ResponseEntity<?> delete(Long id) {
        userService.delete(id);

        return ResponseEntity.ok().build();
    }

}
