package pl.tim.medicalclinic.vacation;

import lombok.Data;

import java.time.LocalDate;

@Data
public class NewVacationDto {
    public Long doctorId;
    public LocalDate vacationDay;
}
