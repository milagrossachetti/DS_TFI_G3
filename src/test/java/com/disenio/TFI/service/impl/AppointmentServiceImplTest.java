package com.disenio.TFI.service.impl;

import com.disenio.TFI.model.Appointment;
import com.disenio.TFI.model.AppointmentStatus;
import com.disenio.TFI.model.Patient;
import com.disenio.TFI.repository.AppointmentRepository;
import com.disenio.TFI.service.AppointmentService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AppointmentServiceImplTest {
    @Mock
    private AppointmentRepository appointmentRepository;
    private AppointmentService appointmentService;
    AutoCloseable autoCloseable;
    Appointment appointment;
    Patient patient;
    Calendar calendar;
    Date date;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        appointmentService = new AppointmentServiceImpl(appointmentRepository);
        // Creo un paciente
        patient = new Patient();
        patient.setId(1L);
        // Creo una fecha que sea 11/03/2024 a las 09:00
        calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.MARCH, 11, 9, 0, 0);
        date = calendar.getTime();
        // Creo un turno
        appointment = new Appointment();
        appointment.setDate(date);
        appointment.setDescription("Consulta");
        appointment.setStatus(AppointmentStatus.PENDING);
        appointment.setPatient(patient);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void testGetAvailableDates() {
        mock(Appointment.class);
        mock(AppointmentRepository.class);

        // Creo una lista de turnos con el unico turno definido
        List<Appointment> appointmentsList = new ArrayList<>();
        appointmentsList.add(appointment);

        // Simula el comportamiento de appointmentRepository.findByDateGreaterThanEqual
        when(appointmentRepository.findByDateGreaterThanEqual(date)).thenReturn(appointmentsList);

        // Llamo a availableDates()
        List<Date> availableDates = appointmentService.getAvailableDates();

        // Verifico que la lista no sea vacía
        assertThat(availableDates).isNotEmpty();
        // Verifico que la lista no contenga la fecha del turno
        assertThat(availableDates).doesNotContain(date);
        // Verifico que la lista contenga la fecha del turno siguiente y sea el primer elemento
        calendar.add(Calendar.HOUR, 1);
        Date nextDate = calendar.getTime();
        assertThat(nextDate.equals(availableDates.get(0)));
    }

    @Test
    void submitAppointmentDetails() {
    }
}