package com.disenio.TFI.service.impl;

import com.disenio.TFI.exception.InvalidAppointmentDurationException;
import com.disenio.TFI.exception.InvalidDateException;
import com.disenio.TFI.exception.InvalidNumberOfDaysException;
import com.disenio.TFI.exception.PatientNotFoundException;
import com.disenio.TFI.model.*;
import com.disenio.TFI.repository.PatientRepository;
import com.disenio.TFI.repository.AppointmentRepository;
import com.disenio.TFI.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@RequiredArgsConstructor
@Service
public class AppointmentServiceImpl implements AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private PatientRepository patientRepository;

    @Autowired
    public AppointmentServiceImpl(AppointmentRepository appointmentRepository, PatientRepository patientRepository) {
        this.appointmentRepository = appointmentRepository;
        this.patientRepository = patientRepository;
    }

    @Override
    public List<Date> getAvailableDates(int duration, int days) throws InvalidNumberOfDaysException, InvalidAppointmentDurationException {
        // Valido que el número de días sea válido
        Appointment appointment = new Appointment();
        appointment.validateDays(days);

        // Valido que la duración sea válida
        appointment.validateDuration(duration);

        // Obtengo la fecha actual
        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();

        // Obtengo la fecha limite
        calendar.setTime(currentDate);
        calendar.add(Calendar.DAY_OF_YEAR, days);
        Date endDate = calendar.getTime();
        // Obtengo la lista de turnos entre la fecha actual y la fecha limite

        List<Appointment> appointmentsList = appointmentRepository.findAppointmentsWithinDateRange(currentDate, endDate);

        // Calculo y retorno las fechas disponibles
        return appointment.calculateAvailableDates(appointmentsList, duration, days, currentDate);
    }

    @Override
    public Appointment submitAppointmentDetails(AppointmentData appointmentData) throws PatientNotFoundException, InvalidDateException, InvalidAppointmentDurationException {
        // Creo un nuevo turno
        Appointment appointment = new Appointment();
        appointment.setAppointmentRepository(appointmentRepository);

        // Busca el paciente por su id
        Optional<Patient> patientOptional = patientRepository.findById(appointmentData.getPatientId());

        // Valido que el paciente exista
        appointment.validatePatient(patientOptional);

        // Valido la duracion del turno
        appointment.validateDuration(appointmentData.getDuration());

        // Valido la fecha del turno
        appointment.validateDate(appointmentData.getDate(), appointmentData.getDuration());

        // Obtengo el paciente
        Patient patient = patientOptional.get();
        patient.setAppointmentRepository(appointmentRepository);

        //Agendo el turno
        return patient.scheduleAppointment(appointmentData);
    }
}
