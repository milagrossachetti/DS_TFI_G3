package com.disenio.TFI.model;

import com.disenio.TFI.exception.InvalidAppointmentDurationException;
import com.disenio.TFI.exception.InvalidDateException;
import com.disenio.TFI.exception.InvalidNumberOfDaysException;
import com.disenio.TFI.exception.PatientNotFoundException;
import com.disenio.TFI.repository.AppointmentRepository;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Date date;
    private String description;
    private Integer duration;

    @Enumerated(EnumType.STRING)
    private AppointmentStatus status;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_patient_id")
    private Patient patient;

    @Transient
    @JsonIgnore
    private AppointmentRepository appointmentRepository;

    public void validateDays(int days) throws InvalidNumberOfDaysException {
        if (days < 1 || days > 180) {
            throw new InvalidNumberOfDaysException("El rango de días debe ser entre 1 y 180");
        }
    }

    public void validateDuration(int duration) throws InvalidAppointmentDurationException {
        if (duration % 15 != 0 || duration < 15) throw new InvalidAppointmentDurationException("La duración debe ser múltiplo de 15 e igual o mayor a 15");
    }

    public void validatePatient(Optional<Patient> patientOptional) throws PatientNotFoundException {
        if (patientOptional.isEmpty()) throw new PatientNotFoundException("El paciente no existe");
    }

    public void validateDate(Date date, int duration) throws InvalidDateException {
        // Calculo la feche de fin del turno
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, duration);
        Date endDate = calendar.getTime();

        // Obtengo la lista de turnos entre la fecha y la fecha de fin
        List<Appointment> appointmentsList = appointmentRepository.findAppointmentsWithinDateRange(date, endDate);

        // Si la lista no está vacía, significa que hay turnos en ese rango horario
        if (!appointmentsList.isEmpty()) {
            throw new InvalidDateException("La fecha ingresada no es válida");
        }

        // Busco el último turno antes de la fecha ingresada y verifico que no se superponga con el turno a agendar
        Appointment lastAppointment = appointmentRepository.findClosestAppointmentBeforeDate(date);
        if (lastAppointment != null) {
            calendar.setTime(lastAppointment.getDate());
            calendar.add(Calendar.MINUTE, lastAppointment.getDuration());
            if (calendar.getTime().compareTo(date) > 0) {
                throw new InvalidDateException("La fecha ingresada no es válida");
            }
        }
    }

    public List<Date> calculateAvailableDates(List<Appointment> appointmentList, long duration, int days, Date currentDate) {
        // "Redondeo la hora de la fecha actual" // Ejemplos: 10:12 -> 10:15, 10:32 -> 10:45, 10:47 -> 11:00
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        int minutes = calendar.get(Calendar.MINUTE);
        switch (minutes / 15) {
            case 0 -> calendar.set(Calendar.MINUTE, 15);
            case 1 -> calendar.set(Calendar.MINUTE, 30);
            case 2 -> calendar.set(Calendar.MINUTE, 45);
            default -> {
                calendar.set(Calendar.MINUTE, 0);
                calendar.add(Calendar.HOUR_OF_DAY, 1);
            }
        }
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        currentDate = calendar.getTime();

        // Obtengo la fecha limite
        calendar.add(Calendar.DAY_OF_YEAR, days);
        Date endDate = calendar.getTime();

        // Creo una lista con las fechas disponibles y la lleno con las fechas entre la fecha actual y la fecha limite
        List<Date> availableDates = new ArrayList<>();
        while (currentDate.compareTo(endDate) <= 0) {
            calendar.setTime(currentDate);
            // Obtengo día de la semana, hora y minutos
            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);
            // Si es lunes, martes, miércoles, jueves o viernes agrega la fecha a la lista de fechas disponibles
            if (dayOfWeek != Calendar.SATURDAY && dayOfWeek != Calendar.SUNDAY) {
                // La fecha tiene que estar entre las 09:00 y las 13:00 o entre las 14:00 y las 18:00
                if ((hour >= 9 && hour <= 12) || (hour >= 14 && hour <= 17) || (hour == 13 && minute == 0) || (hour == 18 && minute == 0)) {
                    availableDates.add(calendar.getTime());
                }
            }
            calendar.add(Calendar.MINUTE, 15);
            currentDate = calendar.getTime();
        }

        // Creo una lista de las fechas ocupadas
        List<Date> occupiedDates = new ArrayList<>();
        for (Appointment appointment : appointmentList) {
            int numberDates = appointment.getDuration() / 15;
            calendar.setTime(appointment.getDate());
            for (int i = 0; i < numberDates; i++) {
                occupiedDates.add(calendar.getTime());
                calendar.add(Calendar.MINUTE, 15);
            }
        }

        // Se eliminan las fechas ocupadas de la lista de fechas disponibles
        availableDates.removeAll(occupiedDates);

        // Se quitan los turnos que no entran en el rango de duración
        List<Date> availableDatesAux = new ArrayList<>(availableDates);
        Calendar calendarAux = Calendar.getInstance();
        for (Date date : availableDatesAux) {
            calendar.setTime(date);
            for(int i = 0; i < duration / 15; i++) {
                if (!availableDates.contains(calendar.getTime())) {
                    availableDates.remove(date);
                    break;
                }
                calendar.add(Calendar.MINUTE, 15);
            }
            calendarAux.setTime(date);
            calendarAux.add(Calendar.MINUTE, (int) duration);
            if ((calendarAux.get(Calendar.HOUR_OF_DAY) == 13 && calendarAux.get(Calendar.MINUTE) != 0) ||
                (calendarAux.get(Calendar.HOUR_OF_DAY) == 18 && calendarAux.get(Calendar.MINUTE) != 0)) {
                availableDates.remove(date);
            }
        }
        // Se retorna la lista de fechas disponibles
        return availableDates;
    }
}
