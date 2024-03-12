package com.disenio.TFI.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String text;
    private boolean chosen; //determina si la pregunta es booleana
    @OneToMany(mappedBy = "question")
    private List<Answer> answers;
}
