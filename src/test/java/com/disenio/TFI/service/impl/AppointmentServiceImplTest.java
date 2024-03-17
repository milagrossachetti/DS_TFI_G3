package com.disenio.TFI.service.impl;

import com.disenio.TFI.exception.InvalidAppointmentDurationException;
import com.disenio.TFI.exception.InvalidDateException;
import com.disenio.TFI.exception.InvalidNumberOfDaysException;
import com.disenio.TFI.exception.PatientNotFoundException;
import com.disenio.TFI.model.Appointment;
import com.disenio.TFI.model.AppointmentData;
import com.disenio.TFI.model.AppointmentStatus;
import com.disenio.TFI.model.Patient;
import com.disenio.TFI.repository.AppointmentRepository;
import com.disenio.TFI.repository.PatientRepository;
import com.disenio.TFI.service.AppointmentService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AppointmentServiceImplTest {

    @Mock
    private AppointmentRepository appointmentRepository;
    @InjectMocks
    private AppointmentServiceImpl appointmentService;

    /*private Date currentDate, appointmentDate, endDate;
    private Appointment appointment;
    @BeforeEach
    void setUp() {
        // Establezco la fecha actual como 11/03/2024 a las 09:00
        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.MARCH, 11, 9, 0, 0);
        currentDate = calendar.getTime();

        // Obtengo la primera fecha del día siguiente a la fecha actual
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 9);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        appointmentDate = calendar.getTime();

        // Obtengo la fecha dentro de 2 días
        calendar.setTime(currentDate);
        calendar.add(Calendar.DAY_OF_YEAR, 2);
        endDate = calendar.getTime();

        // Creo un paciente
        Patient patient = new Patient();
        patient.setId(1L);

        // Creo un turno
        appointment = new Appointment(1L, appointmentDate, "Consulta", 30, AppointmentStatus.PENDING, patient);
    }

    @AfterEach
    void tearDown() {
        appointment = null;
    }*/

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
    /*
    @Mock
    private AppointmentRepository appointmentRepository;
    @Mock
    private PatientRepository patientRepository;
    private AppointmentService appointmentService;
    AutoCloseable autoCloseable;
    Appointment appointment;
    Patient patient;
    Calendar calendar;
    Date date;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        appointmentService = new AppointmentServiceImpl(appointmentRepository, patientRepository);
        // Creo un paciente
        patient = new Patient();
        patient.setId(1L);
        patient.setAppointmentRepository(appointmentRepository);
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
    void testSubmitAppointmentDetailsWhenPatientDoesNotExist() {
        mock(Appointment.class);
        mock(AppointmentRepository.class);
        mock(Patient.class);
        mock(PatientRepository.class);

        // Creo un objeto AppointmentData con el id de un paciente que no existe
        AppointmentData appointmentData = new AppointmentData();
        appointmentData.setPatientId(2L);

        // Simula el comportamiento de patientRepository.findById para que devuelva un Optional vacío
        when(patientRepository.findById(2L)).thenReturn(Optional.empty());

        // Verifica que se lance la excepción PatientNotFoundException
        PatientNotFoundException exception =  assertThrows(PatientNotFoundException.class, () -> {
            appointmentService.submitAppointmentDetails(appointmentData);
        });

        // Verifico el mensaje de la excepción
        assertThat(exception.getMessage()).isEqualTo("El paciente no existe");
    }

    @Test
    void submitAppointmentDetailsWhenDateIsInvalid() {
        mock(Appointment.class);
        mock(AppointmentRepository.class);
        mock(Patient.class);
        mock(PatientRepository.class);

        // Creo un objeto AppointmentData con el id de un paciente que existe pero una fecha inválida
        AppointmentData appointmentData = new AppointmentData();
        appointmentData.setPatientId(1L);
        appointmentData.setDate(date);

        // Simula el comportamiento de patientRepository.findById para que devuelva un Optional con el paciente
        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));

        // Simula el comportamiento de appointmentService.getAvailableDates()
        // para que devuelva una lista vacía ya que la fecha del turno ya está ocupada
        when(appointmentService.getAvailableDates()).thenReturn(Collections.emptyList());

        // Verifico que se lance la excepción InvalidDateException
        InvalidDateException exception = assertThrows(InvalidDateException.class, () -> appointmentService.submitAppointmentDetails(appointmentData));

        // Verifico que el mensaje de la excepción sea el esperado
        assertThat(exception.getMessage()).isEqualTo("La fecha ingresada no es válida");
    }

     */
}