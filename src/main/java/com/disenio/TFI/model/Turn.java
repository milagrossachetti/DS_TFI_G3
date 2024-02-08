package com.disenio.TFI.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter @Builder
public class Turn {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Date date;
    private String description;

    private Enum status{
        Confirmado,
        Cancelado,
        Sin_Respuesta
    };

    @ManyToOne
    @JoinColumn(name = "id_patient")
    private Patient patient;
}