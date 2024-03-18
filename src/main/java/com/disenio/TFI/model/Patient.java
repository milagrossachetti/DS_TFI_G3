package com.disenio.TFI.model;

import com.disenio.TFI.repository.AppointmentRepository;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import lombok.*;

import java.util.Date;

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

    @Transient
    @JsonIgnore
    private AppointmentRepository appointmentRepository;


    public Appointment scheduleAppointment(AppointmentData appointmentData) {
        this.setId(appointmentData.getPatientId());

        // Creo el turno
        Appointment appointment = new Appointment();
        appointment.setDate(appointmentData.getDate());
        appointment.setDescription(appointmentData.getDescription());
        appointment.setDuration(appointmentData.getDuration());
        appointment.setStatus(AppointmentStatus.PENDING);
        appointment.setPatient(this);

        // Agendo el turno
        return appointmentRepository.save(appointment);
    }
}
