package com.disenio.TFI.service;

import com.disenio.TFI.model.Turn;
import org.springframework.stereotype.Service;

@Service
public interface TurnService {
    String createTurn(Turn turn);
}
