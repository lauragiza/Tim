package pl.tim.medicalclinic.office;

import lombok.Data;
import pl.tim.medicalclinic.visit.Visit;

import java.util.List;

@Data
class OfficeDto {
    Long id;
    int number;
    String name;
    List<Visit> visits;
}
