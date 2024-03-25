package com.disenio.TFI.model.response;

import com.disenio.TFI.model.dto.AnswerDTO;
import lombok.*;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter @Builder
public class PatientResponse {
    private Long id;
    private String name;
    private String mail;
    private String sex;
    private int age;
    private Date birthday;
    private String address;
    private String location;
    private String phone;
    private List<AnswerDTO> answers;
}
