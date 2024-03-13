package com.disenio.TFI.model;

import com.disenio.TFI.exception.PatientIsNullException;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter @Builder
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String mail;
    private String sex;
    private int age;
    private Date birthday;
    private String address;
    private String location;
    private String phone;
    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<Answer> answers;
    public void isNull(Patient patient) throws PatientIsNullException {
        if (patient.getName().isEmpty() || patient.getMail().isEmpty() || patient.getSex().isEmpty() || patient.getBirthday()== null || patient.getAddress().isEmpty() || patient.getLocation().isEmpty() || patient.getPhone().isEmpty() || patient.getAnswers().isEmpty()){
            throw new PatientIsNullException("Los atributos no pueden ser nulos");
        }
    }
    public List<Answer> updateListAnswer(List<Answer> oldAnswers, List<Answer> updatedAnswers){
        answers = oldAnswers;
        for (Answer a: updatedAnswers) {
            Answer answer = answers.stream()
                    .filter(answer1 -> answer1.getId().equals(a.getId()))
                    .findFirst().orElse(null);
            if (answer != null){
                answer.setText(a.getText());
            }
        }
        return answers;
    }
}
