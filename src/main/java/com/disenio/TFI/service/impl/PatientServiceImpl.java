package com.disenio.TFI.service.impl;

import com.disenio.TFI.model.Answer;
import com.disenio.TFI.model.Form;
import com.disenio.TFI.model.Patient;
import com.disenio.TFI.model.Question;
import com.disenio.TFI.model.mapper.AnswerMapper;
import com.disenio.TFI.model.mapper.QuestionMapper;
import com.disenio.TFI.model.request.QuestionRequest;
import com.disenio.TFI.repository.AnswerRepository;
import com.disenio.TFI.repository.FormRepository;
import com.disenio.TFI.repository.PatientRepository;
import com.disenio.TFI.repository.QuestionRepository;
import com.disenio.TFI.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

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
    @Autowired
    private AnswerMapper answerMapper;
    @Autowired
    private QuestionMapper questionMapper;

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
    public void answerQuestion(QuestionRequest questionRequest) {
        Question questionEntity = questionMapper.requestToQuestion(questionRequest);
        Form form = formRepository.getById(questionRequest.getForm_id());
        if (isFormValid(form, questionEntity)){
            Question existingQuestion  = questionRepository.getById(questionEntity.getId());
            existingQuestion.setForm(form);
            questionRepository.save(existingQuestion);
            Answer answer = answerMapper.requestToAnswer(questionRequest.getAnswer());
            answer.setQuestion(questionEntity);
            answerRepository.save(answer);
        }
    }

    private boolean isFormValid(Form form, Question questionEntity) {
        boolean isValid = false;
        if (formRepository.existsById(form.getId()) && questionRepository.existsById(questionEntity.getId())){
            System.out.println("entró al if");
            isValid=true;
        }
        return isValid;
    }
}
