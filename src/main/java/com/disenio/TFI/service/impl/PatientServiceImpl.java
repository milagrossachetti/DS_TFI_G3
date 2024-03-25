package com.disenio.TFI.service.impl;

import com.disenio.TFI.exception.PatientAlreadyExistsException;
import com.disenio.TFI.exception.PatientIsEmptyException;
import com.disenio.TFI.exception.PatientIsNullException;
import com.disenio.TFI.exception.PatientNotFoundException;
import com.disenio.TFI.model.Answer;
import com.disenio.TFI.model.Patient;
import com.disenio.TFI.model.Question;
import com.disenio.TFI.model.dto.AnswerDTO;
import com.disenio.TFI.model.dto.PatientDTO;
import com.disenio.TFI.model.response.PatientResponse;
import com.disenio.TFI.repository.AnswerRepository;
import com.disenio.TFI.repository.PatientRepository;
import com.disenio.TFI.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public PatientDTO createPatient(PatientDTO patientDTO) throws PatientIsNullException, PatientIsEmptyException, PatientAlreadyExistsException {
        patientDTO.isNull();
        patientDTO.isEmpty();
        if (patientRepository.existsById(patientDTO.getId())){
            throw new PatientAlreadyExistsException("El id del paciente ya existe");
        }
        List<Answer> answers = patientDTO.getAnswers().stream()
                .map(answer -> Answer.builder()
                        .id(answer.getId())
                        .text(answer.getText())
                        .question(Question.builder()
                                .id(answer.getQuestion_id())
                                .build())
                        .build())
                .collect(Collectors.toList());
        Patient patient = new Patient(
                patientDTO.getId(),
                patientDTO.getName(),
                patientDTO.getMail(),
                patientDTO.getSex(),
                patientDTO.getAge(),
                patientDTO.getBirthday(),
                patientDTO.getAddress(),
                patientDTO.getLocation(),
                patientDTO.getPhone(),
                answers
        );
        for (Answer a: answers) {
            a.setPatient(patient);
        }
        patientRepository.save(patient);
        return patientDTO;
    }

    @Override
    public void updatePatient(Long id, PatientDTO patientDTO) throws Exception {
        if (patientRepository.getById(id) == null){
            throw new PatientNotFoundException("El paciente no existe");
        }
        patientDTO.isNull();
        patientDTO.isEmpty();
        Patient updatedPatient = setValues(patientDTO, id);
        patientRepository.save(updatedPatient);
    }

    public Patient setValues(PatientDTO patientDTO, Long id){
        Patient existingPatient = patientRepository.getById(id);
        List<Answer> oldAnswers = answerRepository.findByPatientId(id);
        List<Answer> updatedAnswers = patientDTO.getAnswers().stream()
                .map(answerDTO -> {
                    Answer existingAnswer = oldAnswers.stream()
                            .filter(answer -> answer.getId().equals(answerDTO.getId()))
                            .findFirst()
                            .orElse(null);
                    if (existingAnswer != null) {
                        existingAnswer.setText(answerDTO.getText());
                        return existingAnswer;
                    } else {
                        // Si no se encuentra una respuesta existente, crea una nueva
                        return Answer.builder()
                                .id(answerDTO.getId())
                                .text(answerDTO.getText())
                                .question(Question.builder()
                                        .id(answerDTO.getQuestion_id())
                                        .build())
                                .build();
                    }
                })
                .collect(Collectors.toList());
        for (Answer a: updatedAnswers) {
            a.setPatient(existingPatient);
        }
        existingPatient.setName(patientDTO.getName());
        existingPatient.setMail(patientDTO.getMail());
        existingPatient.setSex(patientDTO.getSex());
        existingPatient.setAge(patientDTO.getAge());
        existingPatient.setBirthday(patientDTO.getBirthday());
        existingPatient.setAddress(patientDTO.getAddress());
        existingPatient.setLocation(patientDTO.getLocation());
        existingPatient.setPhone(patientDTO.getPhone());
        existingPatient.setAnswers(updatedAnswers);
        return existingPatient;
    }
}
