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
    @Override
    public Patient createPatient(Patient patient) throws PatientIsNullException {
        //crear método para crear respuesta y asignarlo al paciente
        if(isNull(patient)){
            throw new PatientIsNullException("Los atributos no pueden ser nulos");
        }

        ///ya recibo los objetos, no es necesario que los cree
        Patient entityPatient = new Patient(
                patient.getId(),
                patient.getName(),
                patient.getMail(),
                patient.getSex(),
                patient.getAge(),
                patient.getBirthday(),
                patient.getAddress(),
                patient.getLocation(),
                patient.getPhone(),
                patient.getAnswers()
        );
        List<Answer> listAnswers = entityPatient.createListAnswer(patient.getAnswers());
        for (Answer a: listAnswers) {
            a.setPatient(entityPatient);
        }
        return patientRepository.save(entityPatient);
    }

   //hacerlo en Patient y lanzar la excepcion allí
    public boolean isNull(Patient patient){
        boolean isNull = false;
        if (patient.getName().isEmpty() || patient.getMail().isEmpty() || patient.getSex().isEmpty() || patient.getBirthday()== null || patient.getAddress().isEmpty() || patient.getLocation().isEmpty() || patient.getPhone().isEmpty() || patient.getAnswers().isEmpty()){
            isNull = true;
        }
        return isNull;
   }
    @Override
    public void updatePatient(Long id, Patient patient) throws Exception {
        if (patientRepository.getById(id) == null){
            throw new PatientNotFoundException("El paciente no existe");
        }
        if(isNull(patient)){
            throw new PatientIsNullException("Los atributos no pueden ser nulos");
        }
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
