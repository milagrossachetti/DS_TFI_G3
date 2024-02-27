package com.disenio.TFI.config;

import com.twilio.Twilio;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TwilioConfig {
    @Value("${twilio.account_sid}")
    private String accountSid;
    @Value("${twilio.auth_token}")
    private String authToken;
    @Value("${twilio.trial_number}")
    private String trialNumber;
    public TwilioConfig(){}
    public String getAccountSid() {
        return accountSid;
    }

    public void setAccountSid(String accountSid) {
        this.accountSid = accountSid;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getTrialNumber() {
        return trialNumber;
    }

    public void setTrialNumber(String trialNumber) {
        this.trialNumber = trialNumber;
    }
}
