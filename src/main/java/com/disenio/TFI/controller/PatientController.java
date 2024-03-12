package com.disenio.TFI.controller;

import com.disenio.TFI.exception.PatientIsNullException;
import com.disenio.TFI.model.Patient;
import com.disenio.TFI.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("patient")
@RequiredArgsConstructor
public class PatientController {
    @Autowired
    PatientService patientService;

    @PostMapping
    public Patient createPatient(@RequestBody Patient patient) throws PatientIsNullException {
        return patientService.createPatient(patient);
    }

    @PutMapping("/{id}")
    public void updatePatient(@PathVariable("id") Long id,@RequestBody Patient patient) throws Exception {
        patientService.updatePatient(id, patient);
    }
}
