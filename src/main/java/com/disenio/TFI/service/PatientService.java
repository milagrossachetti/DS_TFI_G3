package com.disenio.TFI.service;

import com.disenio.TFI.exception.PatientIsNullException;
import com.disenio.TFI.model.Patient;
import org.springframework.stereotype.Service;

@Service
public interface PatientService {
    Patient createPatient(Patient patient) throws PatientIsNullException;
    void updatePatient(Long id, Patient patient) throws Exception;
}
