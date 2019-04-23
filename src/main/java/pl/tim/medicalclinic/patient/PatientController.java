package pl.tim.medicalclinic.patient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/patients")
public class PatientController {
    private PatientService patientService;
    @Autowired
    public PatientController(PatientService patientService){
        this.patientService=patientService;
    }
    @GetMapping
    public List<Patient> findPatients(){
        return patientService.findPatients();



    }
    @GetMapping("/{id}")
    public Patient findPatient(@PathVariable Long id){

        return patientService.findPatient(id);
    }
    @PostMapping
    public Patient addNewPatient(@RequestBody @Valid Patient patient){
        return patientService.addNewPatient(patient);
}

}
