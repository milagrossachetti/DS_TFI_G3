package com.disenio.TFI.model.mapper;

import com.disenio.TFI.model.Answer;
import com.disenio.TFI.model.request.AnswerRequest;
import org.springframework.stereotype.Component;

@Component
public class AnswerMapperImpl implements AnswerMapper{
    @Override
    public Answer requestToAnswer(AnswerRequest answerRequest) {
        return Answer.builder()
                .text(answerRequest.getText())
                .chosen(answerRequest.isChosen())
                .build();
    }
}
