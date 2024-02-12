package com.disenio.TFI.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;
import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter @Builder
public class Patient {
    @Id
    // @GeneratedValue(strategy = GenerationType.AUTO)
    // Comente la linea porque el id es el dni
    private Long id;
    private String name;
    private String mail;
    private String sex;
    private int age;
    private Date birthday;
    private String address;
    private String location;
    private String phone;
}
