package com.disenio.TFI.model.dto;

import com.disenio.TFI.exception.PatientIsEmptyException;
import com.disenio.TFI.exception.PatientIsNullException;
import com.disenio.TFI.model.Patient;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter @Builder
public class PatientDTO {
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
    public void isEmpty() throws PatientIsEmptyException {
        if (getId() == null || getName().isEmpty() || getMail().isEmpty() || getSex().isEmpty() || getBirthday()== null || getAddress().isEmpty() || getLocation().isEmpty() || getPhone().isEmpty() || getAnswers().isEmpty()){
            throw new PatientIsEmptyException("Los atributos no pueden estar vacios");
        }
    }
    public void isNull() throws PatientIsNullException {
        if (Objects.isNull(getName()) || Objects.isNull(getMail()) || Objects.isNull(getSex()) || getBirthday()== null || Objects.isNull(getAddress()) || Objects.isNull(getLocation()) || Objects.isNull(getPhone()) || Objects.isNull(getAnswers())){
            throw new PatientIsNullException("Los atributos no pueden ser nulos");
        }
    }
}
