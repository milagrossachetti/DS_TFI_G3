package com.disenio.TFI.service.impl;

import com.disenio.TFI.exception.InvalidAppointmentDurationException;
import com.disenio.TFI.exception.InvalidDateException;
import com.disenio.TFI.exception.InvalidNumberOfDaysException;
import com.disenio.TFI.exception.PatientNotFoundException;
import com.disenio.TFI.model.Appointment;
import com.disenio.TFI.model.AppointmentData;
import com.disenio.TFI.model.Patient;
import com.disenio.TFI.repository.AppointmentRepository;
import com.disenio.TFI.repository.PatientRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AppointmentServiceImplTest {

    @Mock
    private AppointmentRepository appointmentRepository;
    @Mock
    private PatientRepository patientRepository;
    @InjectMocks
    private AppointmentServiceImpl appointmentService;

    private Date date;
    private AppointmentData appointmentData;

    @BeforeEach
    void setUp() {
        // Establezco la fecha como 11/03/2024 a las 09:00
        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.MARCH, 11, 9, 0, 0);
        date = calendar.getTime();

        // Creo los datos de un turno
        appointmentData = new AppointmentData();
    }

    @AfterEach
    void tearDown() {
        date = null;
        appointmentData = null;
    }

    @Test
    void testGetAvailableDatesWhenDaysIsLowerThan1() {
        int days = 0;
        int duration = 30;

        // Verifica que se lance la excepción InvalidNumberOfDaysException
        InvalidNumberOfDaysException exception =  assertThrows(InvalidNumberOfDaysException.class, () -> {
            appointmentService.getAvailableDates(duration, days);
        });

        // Verifica que el mensaje de la excepción sea el esperado
        assertThat(exception.getMessage()).isEqualTo("El rango de días debe ser entre 1 y 180");
    }

    @Test
    void testGetAvailableDatesWhenDaysIsGreaterThan180() {
        int days = 181;
        int duration = 30;

        // Verifica que se lance la excepción InvalidNumberOfDaysException
        InvalidNumberOfDaysException exception =  assertThrows(InvalidNumberOfDaysException.class, () -> {
            appointmentService.getAvailableDates(duration, days);
        });

        // Verifica que el mensaje de la excepción sea el esperado
        assertThat(exception.getMessage()).isEqualTo("El rango de días debe ser entre 1 y 180");
    }

    @Test
    void testGetAvailableDatesWhenDurationIsInvalid() {
        int days = 30;
        int duration = 29;

        // Verifica que se lance la excepción InvalidAppointmentDurationException
        InvalidAppointmentDurationException exception =  assertThrows(InvalidAppointmentDurationException.class, () -> {
            appointmentService.getAvailableDates(duration, days);
        });

        // Verifica que el mensaje de la excepción sea el esperado
        assertThat(exception.getMessage()).isEqualTo("La duración debe ser múltiplo de 15 e igual o mayor a 15");
    }

    @Test
    void testSubmitAppointmentDetailsWhenPatientDoesNotExist() {
        // Defino los datos de un turno con un paciente que no existe
        appointmentData.setPatientId(1L);
        appointmentData.setDate(date);
        appointmentData.setDescription("Consulta");
        appointmentData.setDuration(30);

        // Simula el comportamiento de patientRepository.findById para que devuelva un Optional vacío
        when(patientRepository.findById(1L)).thenReturn(Optional.empty());

        // Verifica que se lance la excepción PatientNotFoundException
        PatientNotFoundException exception =  assertThrows(PatientNotFoundException.class, () -> {
            appointmentService.submitAppointmentDetails(appointmentData);
        });

        // Verifica que el mensaje de la excepción sea el esperado
        assertThat(exception.getMessage()).isEqualTo("El paciente no existe");
    }

    @Test
    void testSubmitAppointmentDetailsWhenDurationIsInvalid() {
        // Defino los datos de un turno con una duración inválida
        appointmentData.setPatientId(1L);
        appointmentData.setDate(date);
        appointmentData.setDescription("Consulta");
        appointmentData.setDuration(29);

        // Simula el comportamiento de patientRepository.findById para que devuelva un Optional con el paciente
        when(patientRepository.findById(1L)).thenReturn(Optional.of(new Patient()));

        // Verifica que se lance la excepción InvalidAppointmentDurationException
        InvalidAppointmentDurationException exception =  assertThrows(InvalidAppointmentDurationException.class, () -> {
            appointmentService.submitAppointmentDetails(appointmentData);
        });

        // Verifica que el mensaje de la excepción sea el esperado
        assertThat(exception.getMessage()).isEqualTo("La duración debe ser múltiplo de 15 e igual o mayor a 15");
    }

    @Test
    void testSubmitAppointmentDetailsWhenDateIsInvalid() {
        // Creo una fecha que 30 minutos después de la fecha
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, 30);
        Date endDate = calendar.getTime();

        // Defino los datos de un turno con una fecha inválida
        appointmentData.setPatientId(1L);
        appointmentData.setDate(date);
        appointmentData.setDescription("Consulta");
        appointmentData.setDuration(30);

        // Simula el comportamiento de patientRepository.findById para que devuelva un Optional con el paciente
        when(patientRepository.findById(1L)).thenReturn(Optional.of(new Patient()));

        // Simula el comportamiento de appointmentService.findAppointmentsWithinDateRange
        // para que devuelva una lista no vacía ya que la fecha del turno ya está ocupada
        when(appointmentRepository.findAppointmentsWithinDateRange(date, endDate)).thenReturn(Collections.singletonList(new Appointment()));

        // Verifica que se lance la excepción InvalidDateException
        InvalidDateException exception =  assertThrows(InvalidDateException.class, () -> {
            appointmentService.submitAppointmentDetails(appointmentData);
        });

        // Verifica que el mensaje de la excepción sea el esperado
        assertThat(exception.getMessage()).isEqualTo("La fecha ingresada no es válida");
    }
}