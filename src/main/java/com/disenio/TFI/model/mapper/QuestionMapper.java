package com.disenio.TFI.model.mapper;

import com.disenio.TFI.model.Question;
import com.disenio.TFI.model.request.QuestionRequest;

public interface QuestionMapper {
    Question requestToQuestion(QuestionRequest questionRequest);
}
