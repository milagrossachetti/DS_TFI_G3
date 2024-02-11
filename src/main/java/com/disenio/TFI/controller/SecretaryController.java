package com.disenio.TFI.controller;

import com.disenio.TFI.model.Secretary;
import com.disenio.TFI.service.SecretaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("secretary")
@RequiredArgsConstructor
public class SecretaryController {
    @Autowired
    SecretaryService secretaryService;
    @PostMapping("/createSecretaryAndUser")
    public String createSecretaryAndUser(@RequestBody Secretary secretary){
        return secretaryService.createSecretaryAndUser(secretary);
    }

    @PostMapping("/createSecretary")
    public String createSecretary(@RequestBody Map<String, Long> user_id){
        Long userId = user_id.get("user_id");
        if(userId == null) return "No se proporcionó un ID de usuario válido";
        return secretaryService.createSecretary(userId);
    }
}