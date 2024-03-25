package com.disenio.TFI.model.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter @Builder
public class AnswerDTO {
    private Long id;
    private String text;
    private Long question_id;
}
