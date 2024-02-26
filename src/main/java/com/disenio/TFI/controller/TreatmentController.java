package com.disenio.TFI.controller;

import com.disenio.TFI.exception.OdontologistNotFoundException;
import com.disenio.TFI.exception.PatientNotFoundException;
import com.disenio.TFI.exception.TurnNotFoundException;
import com.disenio.TFI.model.Treatment;
import com.disenio.TFI.model.TreatmentData;
import com.disenio.TFI.service.TreatmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("treatment")
@RequiredArgsConstructor
public class TreatmentController {
    @Autowired
    TreatmentService treatmentService;

    @PostMapping
    public Treatment createTreatment(@RequestBody TreatmentData treatmentData) throws OdontologistNotFoundException, TurnNotFoundException, PatientNotFoundException {
        return treatmentService.createTreatment(treatmentData);
    }
}
