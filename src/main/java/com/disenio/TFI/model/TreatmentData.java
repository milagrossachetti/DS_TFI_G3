package com.disenio.TFI.model;

import lombok.*;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
// TreatmentData es solo una clase auxiliar para mantener un solo parametro
// en el metodo createTreatment de TreatmentService
public class TreatmentData {
    private Date date;
    private String code;
    private Long fee;
    private Boolean finished;
    private String observations;
    private Long odontologistId;
    private Long turnId;
    private Long patientId;
}
