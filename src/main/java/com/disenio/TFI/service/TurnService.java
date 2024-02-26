package com.disenio.TFI.service;

import com.disenio.TFI.exception.PatientNotFoundException;
import com.disenio.TFI.exception.SecretaryNotFoundException;
import com.disenio.TFI.model.Turn;
import com.disenio.TFI.model.TurnData;
import org.springframework.stereotype.Service;

@Service
public interface TurnService {
    Turn createTurn(TurnData turnData) throws SecretaryNotFoundException, PatientNotFoundException;
}
