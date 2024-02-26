package com.disenio.TFI.service;

import com.disenio.TFI.exception.OdontologistAlreadyExistsException;
import com.disenio.TFI.exception.SecretaryAlreadyExistsException;
import com.disenio.TFI.exception.UserNotFoundException;
import com.disenio.TFI.model.Odontologist;
import org.springframework.stereotype.Service;

@Service
public interface OdontologistService {
    Odontologist createOdontologistAndUser(Odontologist odontologist);
    Odontologist createOdontologist(Long user_id) throws UserNotFoundException, OdontologistAlreadyExistsException, SecretaryAlreadyExistsException;
}