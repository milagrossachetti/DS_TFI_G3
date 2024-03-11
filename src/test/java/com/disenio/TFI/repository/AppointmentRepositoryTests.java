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
    Date current, tomorrow, yesterday;
    Patient patient;
    Appointment appointment;

    @BeforeEach
    void setUp() {
        // Obtengo la fecha actual
        Calendar calendar = Calendar.getInstance();
        current = calendar.getTime();
        // Obtengo la fecha de mañana
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        tomorrow = calendar.getTime();
        // Obtengo la fecha de ayer
        calendar.add(Calendar.DAY_OF_YEAR, -2);
        yesterday = calendar.getTime();
        // Creo un paciente
        patient = new Patient();
        patient.setId(1L);
        // Creo un turno
        appointment = new Appointment();
        appointment.setDate(current);
        appointment.setDescription("Consulta");
        appointment.setStatus(AppointmentStatus.PENDING);
        appointment.setPatient(patient);
        // Guardo el turno
        appointmentRepository.save(appointment);
    }

    @AfterEach
    void tearDown() {
        current = null;
        tomorrow = null;
        yesterday = null;
        patient = null;
        appointment = null;
        appointmentRepository.deleteAll();
    }

    // Test case SUCCESS
    @Test
    public void testFindByDateGreaterThanEqualCurrentDate() {
        // Obtengo los turnos a partir de la fecha actual
        List<Appointment> appointmentList = appointmentRepository.findByDateGreaterThanEqual(current);
        // Verifico que la lista tenga un elemento
        assertThat(appointmentList).hasSize(1);
        // Verifico que el turno sea el correcto
        assertThat(appointmentList.get(0)).isEqualTo(appointment);
        // Verifico que el paciente sea el correcto
        assertThat(appointmentList.get(0).getPatient()).isEqualTo(patient);
    }

    @Test
    public void testFindByDateGreaterThanEqualYesterday() {
        // Obtengo los turnos a partir de la fecha de mañana
        List<Appointment> appointmentList = appointmentRepository.findByDateGreaterThanEqual(yesterday);
        // Verifico que la lista tenga un elemento
        assertThat(appointmentList).hasSize(1);
        // Verifico que el turno sea el correcto
        assertThat(appointmentList.get(0)).isEqualTo(appointment);
        // Verifico que el paciente sea el correcto
        assertThat(appointmentList.get(0).getPatient()).isEqualTo(patient);
    }

    // Test case FAILURE
    @Test
    public void testFindByDateGreaterThanEqualTomorrow() {
        // Obtengo los turnos a partir de la fecha de ayer
        List<Appointment> appointmentList = appointmentRepository.findByDateGreaterThanEqual(tomorrow);
        // Verifico que la lista esté vacía
        assertThat(appointmentList).isEmpty();
    }
}
