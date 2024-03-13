package com.disenio.TFI.model;

import com.disenio.TFI.exception.PatientIsNullException;
import com.disenio.TFI.repository.AppointmentRepository;
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
    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<Answer> answers;
    @Transient
    private AppointmentRepository appointmentRepository;
    public void isNull(Patient patient) throws PatientIsNullException {
        if (patient.getName().isEmpty() || patient.getMail().isEmpty() || patient.getSex().isEmpty() || patient.getBirthday()== null || patient.getAddress().isEmpty() || patient.getLocation().isEmpty() || patient.getPhone().isEmpty() || patient.getAnswers().isEmpty()){
            throw new PatientIsNullException("Los atributos no pueden ser nulos");
        }
    }
    public List<Answer> updateListAnswer(List<Answer> oldAnswers, List<Answer> updatedAnswers) {
        answers = oldAnswers;
        for (Answer a : updatedAnswers) {
            Answer answer = answers.stream()
                    .filter(answer1 -> answer1.getId().equals(a.getId()))
                    .findFirst().orElse(null);
            if (answer != null) {
                answer.setText(a.getText());
            }
        }
        return answers;
    }
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

    public Patient(Long id, String name, String mail, String sex, int age, Date birthday, String address, String location, String phone, List<Answer> answers) {
        this.id = id;
        this.name = name;
        this.mail = mail;
        this.sex = sex;
        this.age = age;
        this.birthday = birthday;
        this.address = address;
        this.location = location;
        this.phone = phone;
        this.answers = answers;
    }
}
