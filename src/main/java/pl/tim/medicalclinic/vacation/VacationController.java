package pl.tim.medicalclinic.vacation;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.tim.medicalclinic.exception.CustomEntityNotFoundException;

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
    public List<VacationDto> findVacations() {
        return vacationService.findVacations();
    }

    @GetMapping("/{id}")
    public VacationDto findVacation(@PathVariable Long id) throws CustomEntityNotFoundException {
        return vacationService.findVacation(id);
    }

    @PostMapping
    public VacationDto addVacation(@RequestBody @Valid VacationDto vacationDto) {
        return vacationService.addVacation(vacationDto);
    }

    @DeleteMapping("/id")
    public void deleteVacation(@PathVariable Long id) throws CustomEntityNotFoundException {
        vacationService.deleteVacation(id);
    }
}
