package pl.tim.medicalclinic.office;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OfficeRepository extends JpaRepository<Office, Long> {

    boolean existsByName(String name);

}
