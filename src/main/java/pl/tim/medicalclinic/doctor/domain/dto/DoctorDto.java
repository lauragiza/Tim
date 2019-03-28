package pl.tim.medicalclinic.doctor.domain.dto;

import lombok.Data;

@Data
public class DoctorDto {
    private String name;
    private String lastname;
    private String specialization;
    private String phone;
    private String startHour;
    private String endHour;
}
