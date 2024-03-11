package com.disenio.TFI.model;

import com.disenio.TFI.repository.AppointmentRepository;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;
import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter @Builder
public class Patient {
    @Id
    // @GeneratedValue(strategy = GenerationType.AUTO)
    // Comente la linea porque el id es el dni
    private Long id;
    private String name;
    private String mail;
    private String sex;
    private int age;
    private Date birthday;
    private String address;
    private String location;
    private String phone;

    @Transient
    private AppointmentRepository appointmentRepository;

    public Appointment scheduleAppointment(AppointmentData appointmentData) {
        // Creo el turno
        Appointment appointment = new Appointment();
        appointment.setDate(appointmentData.getDate());
        appointment.setDescription(appointmentData.getDescription());
        appointment.setStatus(AppointmentStatus.PENDING);
        appointment.setPatient(this);
        // Agendo el turno
        return appointmentRepository.save(appointment);
    }
}
