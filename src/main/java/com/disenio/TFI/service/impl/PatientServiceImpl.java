package com.disenio.TFI.service.impl;

import com.disenio.TFI.exception.PatientIsNullException;
import com.disenio.TFI.exception.PatientNotFoundException;
import com.disenio.TFI.model.Answer;
import com.disenio.TFI.model.Patient;
import com.disenio.TFI.repository.AnswerRepository;
import com.disenio.TFI.repository.PatientRepository;
import com.disenio.TFI.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PatientServiceImpl implements PatientService {
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private AnswerRepository answerRepository;
    public PatientServiceImpl(PatientRepository patientRepository, AnswerRepository answerRepository) {
        this.patientRepository = patientRepository;
        this.answerRepository = answerRepository;
    }

    @Override
    public Patient createPatient(Patient patient) throws PatientIsNullException {
        patient.isNull(patient);
        List<Answer> answers = patient.getAnswers();
        for (Answer a: answers) {
            a.setPatient(patient);
        }
        return patientRepository.save(patient);
    }

    @Override
    public void updatePatient(Long id, Patient patient) throws Exception {
        if (patientRepository.getById(id) == null){
            throw new PatientNotFoundException("El paciente no existe");
        }
        patient.isNull(patient);
        Patient updatedPatient = setValues(patient, id);
        List<Answer> answers = updatedPatient.updateListAnswer(answerRepository.findByPatientId(id), patient.getAnswers());
        for (Answer a: answers) {
            a.setPatient(updatedPatient);
        }
        patientRepository.save(updatedPatient);
    }

    public Patient setValues(Patient patient, Long id){
        Patient existingPatient = patientRepository.getById(id);
        existingPatient.setName(patient.getName());
        existingPatient.setMail(patient.getMail());
        existingPatient.setSex(patient.getSex());
        existingPatient.setAge(patient.getAge());
        existingPatient.setBirthday(patient.getBirthday());
        existingPatient.setAddress(patient.getAddress());
        existingPatient.setLocation(patient.getLocation());
        existingPatient.setPhone(patient.getPhone());
        existingPatient.setAnswers(patient.getAnswers());
        return existingPatient;
    }
}
