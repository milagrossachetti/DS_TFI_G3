package com.disenio.TFI.service.impl;

import com.disenio.TFI.exception.PatientIsNullException;
import com.disenio.TFI.model.Answer;
import com.disenio.TFI.model.Patient;
import com.disenio.TFI.model.Question;
import com.disenio.TFI.repository.AnswerRepository;
import com.disenio.TFI.repository.PatientRepository;
import com.disenio.TFI.service.PatientService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


public class PatientServiceImplTest {
    //objeto simulado
    @Mock
    private PatientRepository patientRepository;
    @Mock
    private PatientService patientService;
    @Mock
    private AnswerRepository answerRepository;
    AutoCloseable autoCloseable;
    Patient patient;
    Question question;
    List<Answer> answers = new ArrayList<>();

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        patientService = new PatientServiceImpl(patientRepository,answerRepository);
        question = new Question(1L, "¿Cómo estás?", false, answers);
        Answer answer = new Answer(1L, "Bien", question, patient);
        answers.add(answer);
        patient = new Patient(1L, "Milagros Sachetti", "milagros@example", "Femenino", 21, new Date(2002,8,28), "San Juan 567", "SMT", "381654879", answers);
    }

    @AfterEach
    void tearDown() throws Exception{
        autoCloseable.close();
    }

    @Test
    public void givenPatientToCreateWeExpectPatientCreated() throws PatientIsNullException {
        mock(PatientRepository.class);
        when(patientRepository.save(patient)).thenReturn(patient);
        assertThat(patientService.createPatient(patient)).isEqualTo(patient);
    }
}
