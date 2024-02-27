package com.disenio.TFI.model.request;

import com.disenio.TFI.model.Question;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class AnswerRequest {
    private String text;
    private boolean chosen;
}
