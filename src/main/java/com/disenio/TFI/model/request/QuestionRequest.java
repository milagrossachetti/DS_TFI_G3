package com.disenio.TFI.model.request;

import com.disenio.TFI.model.Answer;
import com.disenio.TFI.model.Form;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class QuestionRequest {
    @NotNull private Long id;
    @NotNull private Long form_id;
    @NotNull private AnswerRequest answer;
}