package pl.tim.medicalclinic.visit;

import lombok.Data;

import java.time.LocalDateTime;


@Data
public class NewVisitDto {
    LocalDateTime date;
    Long doctorId;
    Long patientId;
    Long officeId;
}
