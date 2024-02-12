package com.disenio.TFI.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Turn {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Date date;
    private String description;

    @Enumerated(EnumType.STRING)
    private TurnStatus status;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_secretary_id")
    private Secretary secretary;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_patient_id")
    private Patient patient;
}
