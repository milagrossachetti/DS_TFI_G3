package com.disenio.TFI;

import com.disenio.TFI.model.Answer;
import com.disenio.TFI.model.Patient;
import com.disenio.TFI.model.Question;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Repository {
    public static final Patient[] patient = new Patient[2];
    public static final Question[] questions = new Question[2];
    public static final Answer[] answer = new Answer[2];
    public static List<Answer> answers;

    public static void iniciar(){
        createQuestion();
        createPatient();
        createAnswer();
        createListAnswer();
    }

    public static List<Answer> getAnswers() {
        return answers;
    }

    public static Patient[] getPatient(){
        return patient;
    }

    public static void createQuestion(){
        questions[0] = new Question(1L, "¿Como estas?", false, answers);
        questions[1] = new Question(2L, "¿Que haces?", false, answers);
    }
    public static void createPatient(){
        patient[0] = new Patient(1L, "Milagros Sachetti", "milagros@example.com", "Femenino", 21, new Date(2002, 8, 28), "San Lorenzo 598", "SMT", "381654879", answers);
        patient[1] = new Patient(2L, "Leandro Perez", "leandroperez@example.com", "Masculino", 25, new Date(1998, 10, 5), "Mendoza 845", "SMT", "381897436", answers);
    }
    public static void createAnswer(){
        answer[0] = new Answer(1L, "Bien", questions[0], patient[0]);
        answer[1] = new Answer(2L, "Descansando", questions[1], patient[1]);
    }

    public static void createListAnswer(){
        answers = new ArrayList<Answer>();
        answers.add(answer[0]);
        answers.add(answer[1]);
    }
}
