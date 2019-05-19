package pl.tim.medicalclinic.vacation;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/vacation")
public class VacationController {

    private VacationService vacationService;

    @Autowired
    public VacationController(VacationService vacationService) {
        this.vacationService = vacationService;
    }

    @GetMapping
    public List<VacationDto> findPatients() {
        return vacationService.findVacations();
    }

    @GetMapping("/{id}")
    public VacationDto findPatient(@PathVariable Long id) {
        return vacationService.findVacation(id);
    }

    @PostMapping
    public VacationDto addNewPatient(@RequestBody @Valid VacationDto vacationDto) {
        return vacationService.addVacation(vacationDto);
    }

    @DeleteMapping("/id")
    public void deletePatient(@PathVariable Long id) {
        vacationService.deleteVacation(id);
    }
}
