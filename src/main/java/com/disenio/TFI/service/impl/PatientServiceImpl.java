package com.disenio.TFI.service.impl;

import com.disenio.TFI.model.Answer;
import com.disenio.TFI.model.Form;
import com.disenio.TFI.model.Patient;
import com.disenio.TFI.model.Question;
import com.disenio.TFI.repository.AnswerRepository;
import com.disenio.TFI.repository.FormRepository;
import com.disenio.TFI.repository.PatientRepository;
import com.disenio.TFI.repository.QuestionRepository;
import com.disenio.TFI.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PatientServiceImpl implements PatientService {
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private FormRepository formRepository;

    @Override
    public Patient createPatient(Patient patient) {
        try{
            Form form = new Form();
            formRepository.save(form);
            patient.setForm(form);
            return patientRepository.save(patient);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
   }

    @Override
    public void updatePatiente(Long id, Patient patient) throws Exception {
        try{
            Patient patient1 = patientRepository.getById(id);
            patient1.setName(patient.getName());
            patient1.setMail(patient.getMail());
            patient1.setAge(patient.getAge());
            patient1.setBirthday(patient.getBirthday());
            patient1.setAddress(patient.getAddress());
            patient1.setLocation(patient.getLocation());
            patient1.setPhone(patient.getPhone());
            patientRepository.save(patient1);
        }catch (Exception e){
            throw new Exception("No se encontró un paciente con ese id");
        }
    }

    @Override
    public boolean answerQuestion(Question question) {
        boolean isExists = false;
        Form form = question.getForm();
        if (formRepository.existsById(form.getId())){
            question.setForm(form);
            questionRepository.save(question);
            for (Answer a: question.getAnswers()) {
                a.setQuestion(question);
                a.setId(a.getId());
                answerRepository.save(a);
            }
            isExists=true;
        }
        return isExists;
    }

}
