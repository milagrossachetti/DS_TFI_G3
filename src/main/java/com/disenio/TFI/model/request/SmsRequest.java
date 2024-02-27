package com.disenio.TFI.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class SmsRequest {
    private final String phoneNumber; //destination
    private final String message;

}
