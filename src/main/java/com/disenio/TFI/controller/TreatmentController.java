package com.disenio.TFI.controller;

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
    public String createTreatment(@RequestBody TreatmentData treatmentData){
        return treatmentService.createTreatment(treatmentData);
    }
}
