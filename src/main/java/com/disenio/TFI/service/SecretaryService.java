package com.disenio.TFI.service;

import com.disenio.TFI.model.Secretary;
import org.springframework.stereotype.Service;

@Service
public interface SecretaryService {
    String createSecretary(Secretary secretary);
}