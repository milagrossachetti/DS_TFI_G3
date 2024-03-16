package com.disenio.TFI.repository;

import com.disenio.TFI.model.Appointment;
import com.disenio.TFI.model.AppointmentStatus;
import com.disenio.TFI.model.Patient;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class AppointmentRepositoryTests {
    @Autowired
    private AppointmentRepository appointmentRepository;
    Appointment appointment;
    Date currentDate;


    @BeforeEach
    void setUp() {
        // Obtengo la fecha actual
        Calendar calendar = Calendar.getInstance();
        currentDate = calendar.getTime();

        // Obtengo la primera fecha dos días después de la fecha actual
        calendar.add(Calendar.DAY_OF_YEAR, 2);
        calendar.set(Calendar.HOUR_OF_DAY, 9);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date appointmentDate = calendar.getTime();

        // Creo un paciente
        Patient patient = new Patient();
        patient.setId(1L);

        // Creo un turno
        appointment = new Appointment(1L, appointmentDate, "Consulta", 30, AppointmentStatus.PENDING, patient);

        // Guardo el turno
        appointmentRepository.save(appointment);
    }

    @AfterEach
    void tearDown() {
        appointment = null;
        appointmentRepository.deleteAll();
    }

    @Test
    void testFindAppointmentsWithinDateRangeWithNoAppointments() {
        // Obtengo la fecha del día de mañana
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date endDate = calendar.getTime();

        // Obtengo los turnos entre la fecha actual y la fecha de mañana
        List<Appointment> appointmentList = appointmentRepository.findAppointmentsWithinDateRange(currentDate, endDate);

        // Verifico que la lista esté vacía
        assertThat(appointmentList).isEmpty();
    }

    @Test
    void testFindAppointmentsWithinDateRangeWithOneAppointment() {
        // Obtengo la fecha dentro de 3 días
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.DAY_OF_YEAR, 3);
        Date endDate = calendar.getTime();

        // Obtengo los turnos entre la fecha actual y la fecha dentro de 3 días
        List<Appointment> appointmentList = appointmentRepository.findAppointmentsWithinDateRange(currentDate, endDate);

        // Verifico que la lista tenga un elemento
        assertThat(appointmentList).hasSize(1);

        // Verifico que el turno sea el correcto
        assertThat(appointmentList.get(0).getId()).isEqualTo(appointment.getId());
        assertThat(appointmentList.get(0).getDate().getTime()).isEqualTo(appointment.getDate().getTime());
        assertThat(appointmentList.get(0).getDescription()).isEqualTo(appointment.getDescription());
        assertThat(appointmentList.get(0).getDuration()).isEqualTo(appointment.getDuration());
        assertThat(appointmentList.get(0).getStatus()).isEqualTo(appointment.getStatus());
        assertThat(appointmentList.get(0).getPatient().getId()).isEqualTo(appointment.getPatient().getId());
    }
}
