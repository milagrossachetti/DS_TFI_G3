package com.disenio.TFI.service;

import com.disenio.TFI.exception.InvalidDateException;
import com.disenio.TFI.exception.PatientNotFoundException;
import com.disenio.TFI.exception.SecretaryNotFoundException;
import com.disenio.TFI.model.Appointment;
import com.disenio.TFI.model.AppointmentData;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public interface AppointmentService {
    Appointment createAppointment(AppointmentData appointmentData) throws PatientNotFoundException;

    List<Date> getAvailableDates();

    Appointment submitAppointmentDetails(AppointmentData appointmentData) throws PatientNotFoundException, InvalidDateException;
}
