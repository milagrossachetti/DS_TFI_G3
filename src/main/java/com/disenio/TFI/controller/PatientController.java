package com.disenio.TFI.controller;

import com.disenio.TFI.exception.PatientAlreadyExistsException;
import com.disenio.TFI.exception.PatientIsEmptyException;
import com.disenio.TFI.exception.PatientIsNullException;
import com.disenio.TFI.model.Patient;
import com.disenio.TFI.model.dto.PatientDTO;
import com.disenio.TFI.model.response.PatientResponse;
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
    public PatientDTO createPatient(@RequestBody PatientDTO patientDTO) throws PatientIsNullException, PatientIsEmptyException, PatientAlreadyExistsException {
        return patientService.createPatient(patientDTO);
    }

    @PutMapping("/{id}")
    public void updatePatient(@PathVariable("id") Long id,@RequestBody PatientDTO patientDTO) throws Exception {
        patientService.updatePatient(id, patientDTO);
    }
}
