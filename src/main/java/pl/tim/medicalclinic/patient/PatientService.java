package pl.tim.medicalclinic.patient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class PatientService {
    PatientRepository repository;
    @Autowired
    public PatientService(PatientRepository repository){
        this.repository=repository;

    }
    public List<Patient> findPatients(){
        return repository.findAll();

    }
    public Patient findPatient(Long id){
        return repository.findById(id).get();
    }

    public Patient addNewPatient(Patient patient){

        return repository.save(patient);
    }
}
