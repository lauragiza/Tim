package pl.tim.medicalclinic.doctor.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name = "DOCTOR")
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private String lastname;

    private String specialization;

    private String phone;

    private String startHour;

    private String endHour;

}
