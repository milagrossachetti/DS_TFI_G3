package com.disenio.TFI.controller;

import com.disenio.TFI.exception.InvalidDateException;
import com.disenio.TFI.exception.PatientNotFoundException;
import com.disenio.TFI.model.Appointment;
import com.disenio.TFI.model.AppointmentData;
import com.disenio.TFI.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("appointment")
@RequiredArgsConstructor
public class AppointmentController {
    @Autowired
    AppointmentService appointmentService;

    @PostMapping
    public Appointment createAppointment(@RequestBody AppointmentData appointmentData) throws PatientNotFoundException {
        return appointmentService.createAppointment(appointmentData);
    }

    @GetMapping("getAvailableDates")
    public List<Date> getAvailableDates() {
        return appointmentService.getAvailableDates();
    }

    @PostMapping("postAppointment")
    public Appointment submitAppointmentDetails(@RequestBody AppointmentData appointmentData) throws PatientNotFoundException, InvalidDateException {
        return appointmentService.submitAppointmentDetails(appointmentData);
    }
}
