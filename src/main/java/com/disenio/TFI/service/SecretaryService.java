package com.disenio.TFI.service;

import com.disenio.TFI.exception.OdontologistAlreadyExistsException;
import com.disenio.TFI.exception.SecretaryAlreadyExistsException;
import com.disenio.TFI.exception.UserNotFoundException;
import com.disenio.TFI.model.Secretary;
import org.springframework.stereotype.Service;

@Service
public interface SecretaryService {
    Secretary createSecretaryAndUser(Secretary secretary);
    Secretary createSecretary(Long user_id) throws UserNotFoundException, OdontologistAlreadyExistsException, SecretaryAlreadyExistsException;
}