package pl.tim.medicalclinic.patient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.tim.medicalclinic.exception.CustomEntityNotFoundException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/patients")
public class PatientController {
    private PatientService patientService;

    @Autowired
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping
    public List<PatientDto> findPatients() {
        return patientService.findPatients();
    }

    @GetMapping("/{id}")
    public PatientDto findPatient(@PathVariable Long id) throws CustomEntityNotFoundException {
        return patientService.findPatient(id);
    }

    @PostMapping
    public Patient addNewPatient(@RequestBody @Valid PatientDto patient) {
        return patientService.addNewPatient(patient);
    }

    @DeleteMapping("/id")
    public void deletePatient(@PathVariable Long id) throws CustomEntityNotFoundException {
        patientService.deletePatient(id);
    }

}
