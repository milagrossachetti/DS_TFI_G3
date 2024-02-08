package com.disenio.TFI.controller;

import com.disenio.TFI.model.Patient;
import com.disenio.TFI.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("patient")
@RequiredArgsConstructor
public class PatientController {
    @Autowired
    PatientService patientService;
    @PostMapping
    public String createPatient(@RequestBody Patient patient){
        patientService.createPatient(patient);
        return "El paciente se guardó con éxito";
    }
}
