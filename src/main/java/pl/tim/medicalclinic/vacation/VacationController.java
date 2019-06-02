package pl.tim.medicalclinic.vacation;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.tim.medicalclinic.exception.CustomEntityNotFoundException;

import java.util.List;

@RestController
@RequestMapping("/vacations")
public class VacationController {

    private VacationService vacationService;

    @Autowired
    public VacationController(VacationService vacationService) {
        this.vacationService = vacationService;
    }

    @GetMapping
    public List<VacationListDto> findVacations() {
        return vacationService.findVacations();
    }

    @GetMapping("/{vacationId}")
    public List<VacationListDto> findVacation(@PathVariable Long vacationId) {
        return vacationService.findVacation(vacationId);
    }

    @PostMapping
    public VacationListDto addVacation(@RequestBody NewVacationDto newVacationDto) {
        return vacationService.addVacation(newVacationDto);
    }

    @DeleteMapping("/id")
    public void deleteVacation(@PathVariable Long vacationId) throws CustomEntityNotFoundException {
        vacationService.deleteVacation(vacationId);
    }
}
