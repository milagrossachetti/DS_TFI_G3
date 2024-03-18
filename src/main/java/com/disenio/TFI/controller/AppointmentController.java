package com.disenio.TFI.controller;

import com.disenio.TFI.exception.InvalidAppointmentDurationException;
import com.disenio.TFI.exception.InvalidDateException;
import com.disenio.TFI.exception.InvalidNumberOfDaysException;
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
@RequestMapping("appointments")
@RequiredArgsConstructor
public class AppointmentController {
    @Autowired
    AppointmentService appointmentService;

    @GetMapping("available-dates")
    public List<Date> getAvailableDates(@RequestParam(name = "duration") int duration,
                                        @RequestParam(name = "days") int days) throws InvalidNumberOfDaysException, InvalidAppointmentDurationException {
        return appointmentService.getAvailableDates(duration, days);
    }


    @PostMapping
    public Appointment submitAppointmentDetails(@RequestBody AppointmentData appointmentData) throws PatientNotFoundException, InvalidDateException, InvalidAppointmentDurationException {
        return appointmentService.submitAppointmentDetails(appointmentData);
    }
}
