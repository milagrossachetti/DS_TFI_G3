package com.disenio.TFI.service.impl;

import com.disenio.TFI.model.*;
import com.disenio.TFI.repository.*;
import com.disenio.TFI.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class TurnServiceImpl implements TurnService{
    private final TurnRepository turnRepository;
    @Autowired
    private SecretaryRepository secretaryRepository;
    @Autowired
    private PatientRepository patientRepository;

    @Override
    public String createTurn(TurnData turnData) {
        // Busca la secretaria y el paciente por su id
        Optional<Secretary> secretaryOptional = secretaryRepository.findById(turnData.getSecretaryId());
        Optional<Patient> patientOptional = patientRepository.findById(turnData.getPatientId());

        // Si la secretaria o el paciente no existen, retornar un mensaje de error
        if (!secretaryOptional.isPresent()) return "La secretaria no existe";
        if (!patientOptional.isPresent()) return "El paciente no existe";

        Secretary secretary = secretaryOptional.get();
        Patient patient = patientOptional.get();

        Turn turn = new Turn();
        turn.setDate(turnData.getDate());
        turn.setDescription(turnData.getDescription());
        turn.setStatus(TurnStatus.PENDING);
        turn.setSecretary(secretary);
        turn.setPatient(patient);
        turnRepository.save(turn);

        return "El turno se guardó con éxito";
    }

}
