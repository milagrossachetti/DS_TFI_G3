package com.disenio.TFI.service.impl;

import com.disenio.TFI.model.Secretary;
import com.disenio.TFI.model.User;
import com.disenio.TFI.repository.SecretaryRepository;
import com.disenio.TFI.repository.UserRepository;
import com.disenio.TFI.service.SecretaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class SecretaryServiceImpl implements SecretaryService {
    private final SecretaryRepository secretaryRepository;
    @Autowired
    private UserRepository userRepository;
    @Override
    public String createSecretaryAndUser(Secretary secretary) {
        secretaryRepository.save(secretary);
        return "El usuario y la secretaria se guardaron con éxito";
    }

    // el sgte metodo permite crear una secretaria a partir del usuario del id
    @Override
    public String createSecretary(Long user_id) {
        Optional<User> userOptional = userRepository.findById(user_id);

        // Si el usuario no existe, se retorna un mensaje de error
        if (!userOptional.isPresent()) return "El usuario no existe";

        // Si ya existe un Secretary asociado al user_id, retornar un mensaje de error
        Optional<Secretary> existingSecretaryOptional = secretaryRepository.findByUserId(user_id);
        if (existingSecretaryOptional.isPresent()) return "Ya existe una secretaria asociado a este usuario";

        // Si el usuario existe, se crea la secretaria
        User user = userOptional.get();
        Secretary secretary = new Secretary();
        secretary.setUser(user);
        secretaryRepository.save(secretary);
        return "La secretaria se guardó con éxito";
    }
}