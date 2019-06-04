package pl.tim.medicalclinic.patient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.tim.medicalclinic.exception.AlreadyExistsException;
import pl.tim.medicalclinic.exception.CustomEntityNotFoundException;
import pl.tim.medicalclinic.exception.CustomIncorrectEntityException;

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

    @GetMapping("/{patientId}")
    public PatientDto findPatient(@PathVariable Long patientId) throws CustomEntityNotFoundException {
        return patientService.findPatient(patientId);
    }

    @PostMapping
    public PatientDto addNewPatient(@RequestBody @Valid Patient patient) throws AlreadyExistsException, CustomIncorrectEntityException {
        return patientService.addNewPatient(patient);
    }

    @DeleteMapping("/{patientId}")
    public void deletePatient(@PathVariable Long patientId) throws CustomEntityNotFoundException {
        patientService.deletePatient(patientId);
    }

    @PutMapping("/{patientId}")
    public void updatePatient(@PathVariable Long patientId, @RequestBody Patient patient) throws CustomEntityNotFoundException {
        patientService.updatePatient(patientId, patient);
    }

}
