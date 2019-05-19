package pl.tim.medicalclinic.doctor;

import lombok.Data;
import pl.tim.medicalclinic.visit.Visit;

import java.util.List;

@Data
class DoctorDto {
    private Long id;
    private String name;
    private String lastname;
    private String specialization;
    private String phone;
    private String startWorkingTime;
    private String endWorkingTime;
    private List<Visit> visits;
}
