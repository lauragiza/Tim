package pl.tim.medicalclinic.visit;

import org.springframework.data.jpa.domain.Specification;
import pl.tim.medicalclinic.doctor.Doctor;
import pl.tim.medicalclinic.office.Office;
import pl.tim.medicalclinic.patient.Patient;

class VisitSpecifications {


    static Specification<Visit> hasDoctor(Doctor doctor) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("doctor"), doctor);
    }

    static Specification<Visit> hasOffice(Office office) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("office"), office);
    }

    static Specification<Visit> hasPatient(Patient patient) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("patient"), patient);
    }
}
