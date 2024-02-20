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
public class Treatment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Date date;
    private String code;
    private Long fee;
    private Long outstandingBalance;
    private Boolean finished;
    private String observations;
}
