package com.disenio.TFI.service.impl;

import com.disenio.TFI.model.Turn;
import com.disenio.TFI.repository.TurnRepository;
import com.disenio.TFI.service.TurnService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TurnServiceImpl implements TurnService {
    @Autowired
    private TurnRepository turnRepository;
    @Override
    public String createTurn(Turn turn) {
        turnRepository.save(turn);
        return "El turno se guardó con éxito";
    }
}