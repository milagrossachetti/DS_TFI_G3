package com.disenio.TFI.model.mapper;

import com.disenio.TFI.model.Form;
import com.disenio.TFI.model.Question;
import com.disenio.TFI.model.request.QuestionRequest;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class QuestionMapperImpl implements QuestionMapper{
    @Override
    public Question requestToQuestion(QuestionRequest questionRequest) {
        return Question.builder()
                .id(questionRequest.getId())
                .form(Form.builder()
                        .id(questionRequest.getForm_id())
                        .build())
                .build();
    }
}
