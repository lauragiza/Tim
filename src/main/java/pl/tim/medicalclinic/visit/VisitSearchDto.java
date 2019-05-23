package pl.tim.medicalclinic.visit;

import lombok.Getter;

@Getter
class VisitSearchDto {
    private String patientId;
    private String officeId;
    private String doctorId;

    VisitSearchDto(String patientId, String officeId, String doctorId) {
        this.patientId = patientId;
        this.officeId = officeId;
        this.doctorId = doctorId;
    }

}
