package com.disenio.TFI.model.mapper;

import com.disenio.TFI.model.Answer;
import com.disenio.TFI.model.request.AnswerRequest;

public interface AnswerMapper {
    Answer requestToAnswer(AnswerRequest answerRequest);
}
