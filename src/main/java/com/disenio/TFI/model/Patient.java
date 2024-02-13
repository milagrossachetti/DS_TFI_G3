package com.disenio.TFI.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter @Builder
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String mail;
    private String sex;
    private int age;
    private Date birthday;
    private String address;
    private String location;
    private String phone;
    @OneToOne
    @JoinColumn(name = "form_id")
    private Form form;
}
