package com.disenio.TFI.controller;

import com.disenio.TFI.exception.OdontologistAlreadyExistsException;
import com.disenio.TFI.exception.SecretaryAlreadyExistsException;
import com.disenio.TFI.exception.UserNotFoundException;
import com.disenio.TFI.model.Odontologist;
import com.disenio.TFI.service.OdontologistService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("odontologist")
@RequiredArgsConstructor
public class OdontologistController {
    @Autowired
    OdontologistService odontologistService;
    @PostMapping("/createOdontologistAndUser")
    public Odontologist createOdontologistAndUser(@RequestBody Odontologist odontologist){
        return odontologistService.createOdontologistAndUser(odontologist);
    }

    @PostMapping("/createOdontologist")
    public Odontologist createOdontologist(@RequestBody Map<String, Long> user_id) throws UserNotFoundException, OdontologistAlreadyExistsException, SecretaryAlreadyExistsException {
        Long userId = user_id.get("user_id");
        return odontologistService.createOdontologist(userId);
    }
}