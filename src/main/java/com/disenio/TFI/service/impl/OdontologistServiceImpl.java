package com.disenio.TFI.service.impl;

import com.disenio.TFI.model.Odontologist;
import com.disenio.TFI.model.Secretary;
import com.disenio.TFI.model.User;
import com.disenio.TFI.repository.OdontologistRepository;
import com.disenio.TFI.repository.SecretaryRepository;
import com.disenio.TFI.repository.UserRepository;
import com.disenio.TFI.service.OdontologistService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class OdontologistServiceImpl implements OdontologistService {
    private final OdontologistRepository odontologistRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SecretaryRepository secretaryRepository;
    @Override
    public String createOdontologistAndUser(Odontologist odontologist) {
        odontologistRepository.save(odontologist);
        return "El usuario y la odontóloga se guardaron con éxito";
    }

    // el sgte metodo permite crear una odontologa a partir del usuario del id
    @Override
    public String createOdontologist(Long user_id) {
        Optional<User> userOptional = userRepository.findById(user_id);

        // Si el usuario no existe, se retorna un mensaje de error
        if (!userOptional.isPresent()) return "El usuario no existe";

        // Si ya existe un Secretary asociado al user_id, retornar un mensaje de error
        Optional<Secretary> existingSecretaryOptional = secretaryRepository.findByUserId(user_id);
        if(existingSecretaryOptional.isPresent()) return "Ya existe una secretaria asociado a este usuario";

        // Si ya existe un Odontologist asociado al user_id, retornar un mensaje de error
        Optional<Odontologist> existingOdontologistOptional = odontologistRepository.findByUserId(user_id);
        if (existingOdontologistOptional.isPresent()) return "Ya existe una odontóloga asociado a este usuario";

        // Si el usuario existe, se crea la secretaria
        User user = userOptional.get();
        Odontologist odontologist = new Odontologist();
        odontologist.setUser(user);
        odontologistRepository.save(odontologist);
        return "La secretaria se guardó con éxito";
    }
}