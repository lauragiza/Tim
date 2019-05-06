package pl.tim.medicalclinic.patient;

import lombok.Data;

@Data
class PatientDto {
    Long id;
    String name;
    String surname;
}
