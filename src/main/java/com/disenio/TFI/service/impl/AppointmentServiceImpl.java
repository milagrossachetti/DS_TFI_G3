package com.disenio.TFI.service.impl;

import com.disenio.TFI.exception.InvalidDateException;
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
    @Autowired
    private PatientRepository patientRepository;

    @Override
    public Appointment createAppointment(AppointmentData appointmentData) throws PatientNotFoundException {
        // Busca el paciente por su id
        Optional<Patient> patientOptional = patientRepository.findById(appointmentData.getPatientId());

        // Si el paciente no existe, retornar un mensaje de error
        if (!patientOptional.isPresent()) throw new PatientNotFoundException("El paciente no existe");

        Patient patient = patientOptional.get();

        Appointment appointment = new Appointment();
        appointment.setDate(appointmentData.getDate());
        appointment.setDescription(appointmentData.getDescription());
        appointment.setStatus(AppointmentStatus.PENDING);
        appointment.setPatient(patient);
        return appointmentRepository.save(appointment);
    }

    @Override
    public List<Date> getAvailableDates() {
        // Obtengo los turnos proximos
        Date currentDate = new Date();
        List<Appointment> appointmentsList = appointmentRepository.findByDateGreaterThanEqual(currentDate);
        // Calculo y retorno las fechas disponibles
        return calculateAvailableDates(appointmentsList);
    }

    @Override
    public Appointment submitAppointmentDetails(AppointmentData appointmentData) throws PatientNotFoundException, InvalidDateException {
        // Busca el paciente por su id
        Optional<Patient> patientOptional = patientRepository.findById(appointmentData.getPatientId());

        // Si el paciente no existe, retornar un mensaje de error
        if (!patientOptional.isPresent()) throw new PatientNotFoundException("El paciente no existe");

        // Si la fecha ingresada no es válida, retornar un mensaje de error
        if(!getAvailableDates().contains(appointmentData.getDate())) throw new InvalidDateException("La fecha ingresada no es válida");

        // Obtengo el paciente
        Patient patient = patientOptional.get();
        patient.setAppointmentRepository(appointmentRepository);

        // Agendo el turno
        return patient.scheduleAppointment(appointmentData);
    }

    public List<Date> calculateAvailableDates(List<Appointment> appointmentsList) {
        Calendar calendar = Calendar.getInstance();
        // Obtengo la fecha actual y pongo la hora en 00:00:00
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date currentDate = calendar.getTime();
        // Sumar 14 días a la fecha actual
        calendar.setTime(currentDate);
        calendar.add(Calendar.DAY_OF_YEAR, 14);
        Date endDate = calendar.getTime();

        List<Date> availableDates = new ArrayList<>();
        // Se agregan todos las fechas a la lista de fechas disponibles
        while(currentDate.compareTo(endDate) <= 0) {
            calendar.setTime(currentDate);
            // Obtengo día de la semana
            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
            // Obtengo la hora
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            // Si es lunes, martes, miércoles, jueves o viernes agrega la fecha a la lista de fechas disponibles
            if(dayOfWeek != Calendar.SATURDAY && dayOfWeek != Calendar.SUNDAY) {
                // Si la hora es entre las 9 y las 12 o las 14 y las 17, agrega la fecha a la lista de fechas disponibles
                if((hour >= 9 && hour <= 12) || (hour >= 14 && hour <= 17)) {
                    availableDates.add(currentDate);
                }
            }
            calendar.add(Calendar.HOUR, 1);
            currentDate = calendar.getTime();
        }
        // Se crea una lista de todas las fechas ocupadas
        List<Date> occupiedDates = new ArrayList<>();
        for (Appointment appointment : appointmentsList) {
            occupiedDates.add(appointment.getDate());
        }
        // Se eliminan las fechas ocupadas de la lista de fechas disponibles
        availableDates.removeAll(occupiedDates);

        return availableDates;
    }
}
