package com.disenio.TFI.controller;

import com.disenio.TFI.model.request.SmsRequest;
import com.disenio.TFI.service.TwilioService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("sendSms")
@RequiredArgsConstructor
public class TwilioController {
    @Autowired
    private final TwilioService twilioService;

    @PostMapping
    public void sendSms(@RequestBody SmsRequest smsRequest) throws IllegalAccessException {
        twilioService.sendSms(smsRequest);
    }
}
