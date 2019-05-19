package pl.tim.medicalclinic.office;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface OfficeRepository extends JpaRepository<Office, Long> {

}
