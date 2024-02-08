package com.disenio.TFI.controller;

import com.disenio.TFI.model.Turn;
import com.disenio.TFI.service.TurnService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("turn")
@RequiredArgsConstructor
public class TurnController {
    @Autowired
    TurnService turnService;
    @PostMapping
    public String createTurn(@RequestBody Turn turn){
        turnService.createTurn(turn);
        return "El turn se guardó con éxito";
    }
}