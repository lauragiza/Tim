package pl.tim.medicalclinic.visit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public List<VisitDto> findVisits() {
        return visitService.findVisits();
    }

    @GetMapping("/{id}")
    public VisitDto findPatient(@PathVariable Long id) {
        return visitService.findVisit(id);
    }

    @PostMapping
    public VisitDto createVisit(@RequestBody @Valid Visit visit) {
        return visitService.createVisit(visit);
    }

    @DeleteMapping("/id")
    public void deletePatient(@PathVariable Long id) {
        visitService.deleteVisit(id);
    }
}
