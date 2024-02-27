package com.disenio.TFI.config;

import com.twilio.Twilio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TwilioInitializer {
    @Autowired
    private TwilioConfig twilioConfig;

    public TwilioInitializer(TwilioConfig twilioConfig){
        this.twilioConfig = twilioConfig;
        Twilio.init(twilioConfig.getAccountSid(), twilioConfig.getAuthToken());
    }
}
