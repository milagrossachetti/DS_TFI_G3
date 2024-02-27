package com.disenio.TFI.service;

import com.disenio.TFI.model.request.SmsRequest;

public interface TwilioService {
    void sendSms(SmsRequest smsRequest) throws IllegalAccessException;
}
