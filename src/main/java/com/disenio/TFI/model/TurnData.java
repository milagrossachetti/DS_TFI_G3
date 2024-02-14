package com.disenio.TFI.model;

import lombok.*;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
// TurnData es solo una clase auxiliar para mantener un solo parametro en el metodo createTurn de TurnService
public class TurnData {
    private Date date;
    private String description;
    private Long secretaryId;
    private Long patientId;
}
