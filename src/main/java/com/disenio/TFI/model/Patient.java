package com.disenio.TFI.model;

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
    @OneToMany(mappedBy = "patient", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Answer> answers;

    //patrón creador para la lista de respuestas, y poblar esa lista con los valores correspondientes.
    public List<Answer> createListAnswer(List<Answer> answer) {
        answers = new ArrayList<Answer>();
        answers.addAll(answer);
        return answers;
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
