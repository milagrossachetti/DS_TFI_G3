package com.disenio.TFI.service;

import com.disenio.TFI.model.TurnData;
import org.springframework.stereotype.Service;

@Service
public interface TurnService {
    String createTurn(TurnData turnData);
}
