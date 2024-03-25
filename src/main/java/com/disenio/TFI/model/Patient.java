package com.disenio.TFI.model;

import com.disenio.TFI.exception.PatientIsEmptyException;
import com.disenio.TFI.exception.PatientIsNullException;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter @Builder
public class Patient {
    @Id
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
    public List<Answer> updateListAnswer(List<Answer> oldAnswers, List<Answer> updatedAnswers) {
        answers = oldAnswers;
        for (Answer a : updatedAnswers) {
            Answer answer = answers.stream()
                    .filter(answer1 -> answer1.getId().equals(a.getId()))
                    .findFirst().orElse(null);
            if (answer != null) {
                answer.setText(a.getText());
            }
        }
        return answers;
    }

}
