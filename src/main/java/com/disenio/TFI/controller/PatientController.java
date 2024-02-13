package com.disenio.TFI.controller;

import com.disenio.TFI.model.Answer;
import com.disenio.TFI.model.Patient;
import com.disenio.TFI.model.Question;
import com.disenio.TFI.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("patient")
@RequiredArgsConstructor
public class PatientController {
    @Autowired
    PatientService patientService;

    @PostMapping
    public Patient createPatient(@RequestBody Patient patient){
        return patientService.createPatient(patient);
    }

    @PutMapping("/update/{id}")
    public void updatePatient(@PathVariable("id") Long id,@RequestBody Patient patient) throws Exception {
        patientService.updatePatiente(id, patient);
    }
    @PutMapping("/answerQuestion")
    public boolean answerQuestion(@RequestBody Question question){
        return patientService.answerQuestion(question);
    }

}
