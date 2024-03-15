package com.disenio.TFI.model;

import lombok.*;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
// AppointmentData es solo una clase auxiliar para mantener un solo parametro en el metodo createAppointment de AppointmentService
public class AppointmentData {
    private Date date;
    private String description;
    private Long patientId;
    private Long duration;
}
