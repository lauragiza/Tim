package pl.tim.medicalclinic.vacation;

import lombok.Data;

import java.time.LocalDate;

@Data
public class VacationDto {
    public Long doctorId;
    public LocalDate vacationDay;
    public Long vacationId;
}
