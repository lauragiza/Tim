package pl.tim.medicalclinic.doctor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.tim.medicalclinic.doctor.domain.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
}
