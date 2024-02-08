package com.disenio.TFI.service.impl;

import com.disenio.TFI.model.Patient;
import com.disenio.TFI.repository.PatientRepository;
import com.disenio.TFI.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PatientServiceImpl implements PatientService {
    @Autowired
    private PatientRepository patientRepository;
    @Override
    public String createPatient(Patient patient) {
        patientRepository.save(patient);
        return "El paciente se guardó con éxito";
    }
}
