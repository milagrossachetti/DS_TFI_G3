package com.disenio.TFI.controller;

import com.disenio.TFI.model.Secretary;
import com.disenio.TFI.service.SecretaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("secretary")
@RequiredArgsConstructor
public class SecretaryController {
    @Autowired
    SecretaryService secretaryService;
    @PostMapping
    public String createSecretary(@RequestBody Secretary secretary){
        secretaryService.createSecretary(secretary);
        return "La secretaría se guardó con éxito";
    }
}
