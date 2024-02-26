package com.disenio.TFI.service.impl;

import com.disenio.TFI.exception.OdontologistNotFoundException;
import com.disenio.TFI.exception.PatientNotFoundException;
import com.disenio.TFI.exception.TurnNotFoundException;
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
    public Treatment createTreatment(TreatmentData treatmentData) throws OdontologistNotFoundException, TurnNotFoundException, PatientNotFoundException {
        // Busca a la odontóloga y si no existe devuelve un mensaje de error
        Optional<Odontologist> odontologistOptional = odontologistRepository.findById(treatmentData.getOdontologistId());
        if (!odontologistOptional.isPresent()) throw new OdontologistNotFoundException("La odontóloga no existe");
        // Busca el turno
        Optional<Turn> turnOptional = turnRepository.findById(treatmentData.getTurnId());
        if (!turnOptional.isPresent()) throw new TurnNotFoundException("El turno no existe");
        // Busca al paciente
        Optional<Patient> patientOptional = patientRepository.findById(treatmentData.getPatientId());
        if (!patientOptional.isPresent()) throw new PatientNotFoundException("El paciente no existe");

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
        return treatmentRepository.save(treatment);
    }
}
