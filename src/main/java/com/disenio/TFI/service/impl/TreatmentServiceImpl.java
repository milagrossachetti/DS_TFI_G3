package com.disenio.TFI.service.impl;

import com.disenio.TFI.model.Treatment;
import com.disenio.TFI.model.Odontologist;
import com.disenio.TFI.model.Patient;
import com.disenio.TFI.model.Turn;
import com.disenio.TFI.repository.OdontologistRepository;
import com.disenio.TFI.repository.PatientRepository;
import com.disenio.TFI.repository.TurnRepository;
import com.disenio.TFI.service.TreatmentService;
import com.disenio.TFI.model.TreatmentData;
import com.disenio.TFI.repository.TreatmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class TreatmentServiceImpl implements TreatmentService{
    private final TreatmentRepository treatmentRepository;
    @Autowired
    private OdontologistRepository odontologistRepository;
    @Autowired
    private TurnRepository turnRepository;
    @Autowired
    private PatientRepository patientRepository;

    @Override
    public String createTreatment(TreatmentData treatmentData) {
        // Busca a la odontóloga y si no existe devuelve un mensaje de error
        Optional<Odontologist> odontologistOptional = odontologistRepository.findById(treatmentData.getOdontologistId());
        if (!odontologistOptional.isPresent()) return "La odontóloga no existe";
        // Busca el turno
        Optional<Turn> turnOptional = turnRepository.findById(treatmentData.getTurnId());
        if (!turnOptional.isPresent()) return "El turno no existe";
        // Busca al paciente
        Optional<Patient> patientOptional = patientRepository.findById(treatmentData.getPatientId());
        if (!patientOptional.isPresent()) return "El paciente no existe";

        Odontologist odontologist = odontologistOptional.get();
        Turn turn = turnOptional.get();
        Patient patient = patientOptional.get();

        // Crea la prestación
        Treatment treatment = new Treatment();
        treatment.setDate(treatmentData.getDate());
        treatment.setCode(treatmentData.getCode());
        treatment.setFee(treatmentData.getFee());
        treatment.setOutstandingBalance(treatmentData.getFee());
        treatment.setFinished(treatmentData.getFinished());
        treatment.setObservations(treatmentData.getObservations());
        treatment.setOdontologist(odontologist);
        treatment.setTurn(turn);
        treatment.setPatient(patient);

        // Guarda la prestación
        treatmentRepository.save(treatment);

        return "La prestación se guardó con éxito";
    }
}
