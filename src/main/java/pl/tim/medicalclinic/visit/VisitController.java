package pl.tim.medicalclinic.visit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.tim.medicalclinic.exception.CustomEntityNotFoundException;
import pl.tim.medicalclinic.exception.DoctorAbsentException;
import pl.tim.medicalclinic.exception.IllegalEntryGoogleCalendarException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/visits")
public class VisitController {

    @Autowired
    public VisitController(VisitService visitService) {
        this.visitService = visitService;
    }

    private final VisitService visitService;

    @GetMapping
    public List<VisitDto> findVisits(@RequestParam(name = "patientId", required = false, defaultValue = "") String patientId,
                                  @RequestParam(name = "doctorId", required = false, defaultValue = "") String doctorId,
                                  @RequestParam(name = "officeId", required = false, defaultValue = "") String officeId) {

        VisitSearchDto searchDto = new VisitSearchDto(patientId, officeId, doctorId);
        return visitService.findVisits(searchDto);
    }

    @GetMapping("/{visitId}")
    public VisitDto findVisit(@PathVariable Long visitId) throws CustomEntityNotFoundException {
        return visitService.findVisit(visitId);
    }

    @PostMapping
    public VisitDto createVisit(@RequestBody @Valid NewVisitDto visitDto) throws DoctorAbsentException, CustomEntityNotFoundException, IllegalEntryGoogleCalendarException {
        return visitService.createVisit(visitDto);
    }

    @DeleteMapping("/visitId")
    public void deleteVisit(@PathVariable Long visitId) throws CustomEntityNotFoundException {
        visitService.deleteVisit(visitId);
    }
}
