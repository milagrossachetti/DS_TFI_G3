package com.disenio.TFI.service.impl;

import com.disenio.TFI.config.TwilioConfig;
import com.disenio.TFI.model.Patient;
import com.disenio.TFI.model.request.SmsRequest;
import com.disenio.TFI.repository.PatientRepository;
import com.disenio.TFI.service.TwilioService;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TwilioServiceImpl implements TwilioService {
    private final TwilioConfig twilioConfig;
    private final PatientRepository patientRepository;
    @Override
    public void sendSms(SmsRequest smsRequest) throws IllegalAccessException {
        if (isPhoneNumberValid(smsRequest.getPhoneNumber())){
            PhoneNumber to = new PhoneNumber("whatsapp:" + smsRequest.getPhoneNumber());
            PhoneNumber from = new PhoneNumber("whatsapp:" + twilioConfig.getTrialNumber());
            MessageCreator creator = Message.creator(to, from, smsRequest.getMessage());
            creator.create();
        } else {
            throw new IllegalAccessException("El número ingresado no está asociado a ningún paciente");
        }
    }

    private boolean isPhoneNumberValid(String phoneNumber) {
        boolean isValid = false;
        List<Patient> patients = patientRepository.findAll();
        for (Patient p : patients){
            if (phoneNumber.equals(p.getPhone())){
                isValid = true;
            }
        }
        return isValid;
    }
}
