package pl.tim.medicalclinic.visit;

import lombok.Data;

import java.time.LocalDateTime;

@Data
class VisitDto {
    Long id;
    LocalDateTime date;
    Long doctorId;
    Long patientId;
    Long officeId;
}
