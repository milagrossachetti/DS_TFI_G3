package com.disenio.TFI.service;

import com.disenio.TFI.exception.PatientAlreadyExistsException;
import com.disenio.TFI.exception.PatientIsEmptyException;
import com.disenio.TFI.exception.PatientIsNullException;
import com.disenio.TFI.model.Patient;
import com.disenio.TFI.model.dto.PatientDTO;
import com.disenio.TFI.model.response.PatientResponse;
import org.springframework.stereotype.Service;

@Service
public interface PatientService {
    PatientDTO createPatient(PatientDTO patientDTO) throws PatientIsNullException, PatientIsEmptyException, PatientAlreadyExistsException;
    void updatePatient(Long id, PatientDTO patientDTO) throws Exception;
}
