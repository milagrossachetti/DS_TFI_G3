package com.disenio.TFI.service.impl;

import com.disenio.TFI.exception.OdontologistAlreadyExistsException;
import com.disenio.TFI.exception.SecretaryAlreadyExistsException;
import com.disenio.TFI.exception.UserNotFoundException;
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
    public Odontologist createOdontologistAndUser(Odontologist odontologist) {
        try {
            return odontologistRepository.save(odontologist);
        } catch (Exception e) {
            throw new RuntimeException("Error al crear la odontóloga");
        }
    }

    // el sgte metodo permite crear una odontologa a partir del usuario del id
    @Override
    public Odontologist createOdontologist(Long user_id) throws UserNotFoundException, OdontologistAlreadyExistsException, SecretaryAlreadyExistsException {
        Optional<User> userOptional = userRepository.findById(user_id);

        // Si el usuario no existe, se retorna un mensaje de error
        if (!userOptional.isPresent()) throw new UserNotFoundException("El usuario no existe");

        // Si ya existe un Secretary asociado al user_id, retornar un mensaje de error
        Optional<Secretary> existingSecretaryOptional = secretaryRepository.findByUserId(user_id);
        if(existingSecretaryOptional.isPresent()) throw new SecretaryAlreadyExistsException("Ya existe una secretaria asociada a este usuario");

        // Si ya existe un Odontologist asociado al user_id, retornar un mensaje de error
        Optional<Odontologist> existingOdontologistOptional = odontologistRepository.findByUserId(user_id);
        if (existingOdontologistOptional.isPresent()) throw new OdontologistAlreadyExistsException("Ya existe una odontóloga asociada a este usuario");

        // Si el usuario existe, se crea la secretaria
        User user = userOptional.get();
        Odontologist odontologist = new Odontologist();
        odontologist.setUser(user);
        return odontologistRepository.save(odontologist);
    }
}