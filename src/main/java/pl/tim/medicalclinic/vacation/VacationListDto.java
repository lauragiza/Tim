package pl.tim.medicalclinic.vacation;

import lombok.Data;

import java.time.LocalDate;

@Data
public class VacationListDto {
    public Long doctorId;
    public Long vacationId;
    public LocalDate vacationDay;
}
