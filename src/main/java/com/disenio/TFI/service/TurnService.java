package com.disenio.TFI.service;

import com.disenio.TFI.model.Turn;
import com.disenio.TFI.model.TurnData;
import com.disenio.TFI.model.TurnStatus;
import org.springframework.stereotype.Service;
import java.util.Date;

@Service
public interface TurnService {
    String createTurn(TurnData turnData);
}
