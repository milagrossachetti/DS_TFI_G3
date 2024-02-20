package com.disenio.TFI.service;

import com.disenio.TFI.model.Odontologist;
import org.springframework.stereotype.Service;

@Service
public interface OdontologistService {
    String createOdontologistAndUser(Odontologist odontologist);
    String createOdontologist(Long user_id);
}