package com.disenio.TFI.service;

import com.disenio.TFI.exception.OdontologistNotFoundException;
import com.disenio.TFI.exception.PatientNotFoundException;
import com.disenio.TFI.exception.TurnNotFoundException;
import com.disenio.TFI.model.Treatment;
import com.disenio.TFI.model.TreatmentData;

public interface TreatmentService {
    Treatment createTreatment(TreatmentData treatmentData) throws OdontologistNotFoundException, TurnNotFoundException, PatientNotFoundException;
}
