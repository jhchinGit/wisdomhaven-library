package com.wisdomhaven.library.authenticator.controller;

import com.wisdomhaven.library.authenticator.dto.request.UserRequestBody;
import com.wisdomhaven.library.authenticator.service.IUserService;
import com.wisdomhaven.library.authenticator.util.RequestUtil;
import com.wisdomhaven.library.authenticator.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "users")
public class UserController {

    private final IUserService userService;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity createUser(@RequestBody UserRequestBody userRequestBody) {
        RequestUtil.validate(userRequestBody);

        this.userService.createUser(
                userRequestBody.username(),
                userRequestBody.password(),
                userRequestBody.email());

        return ResponseUtil.buildResponseEntity(HttpStatus.CREATED);
    }
}
