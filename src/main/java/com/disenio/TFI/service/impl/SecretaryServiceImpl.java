package com.disenio.TFI.service.impl;

import com.disenio.TFI.model.Secretary;
import com.disenio.TFI.repository.SecretaryRepository;
import com.disenio.TFI.service.SecretaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SecretaryServiceImpl implements SecretaryService {
    @Autowired
    private SecretaryRepository secretaryRepository;
    @Override
    public String createSecretary(Secretary secretary) {
        secretaryRepository.save(secretary);
        return "La secretaría se guardó con éxito";
    }
}