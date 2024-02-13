package com.disenio.TFI.service;

import com.disenio.TFI.model.Answer;
import com.disenio.TFI.model.Patient;
import com.disenio.TFI.model.Question;
import org.springframework.stereotype.Service;

@Service
public interface PatientService {
    Patient createPatient(Patient patient);
    void updatePatiente(Long id, Patient patient) throws Exception;
    boolean answerQuestion(Question question);
}
