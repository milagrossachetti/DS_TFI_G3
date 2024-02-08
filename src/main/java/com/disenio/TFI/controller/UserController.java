package com.disenio.TFI.controller;

import com.disenio.TFI.model.User;
import com.disenio.TFI.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController {
    @Autowired
    UserService userService;
    @PostMapping
    public String createUser(@RequestBody User user){
        userService.createUser(user);
        return "El Usuario se guardó con éxito";
    }
}