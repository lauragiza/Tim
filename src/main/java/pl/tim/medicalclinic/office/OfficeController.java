package pl.tim.medicalclinic.office;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.tim.medicalclinic.exception.CustomEntityNotFoundException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/offices")
public class OfficeController {

    private OfficeService officeService;

    @Autowired
    public OfficeController(OfficeService officeService) {
        this.officeService = officeService;
    }

    @GetMapping(value = "/{officeId}")
    public OfficeDto findOffice(@PathVariable("officeId") Long officeId) throws CustomEntityNotFoundException {
        return officeService.findOffice(officeId);
    }

    @GetMapping
    public List<OfficeDto> findOffices() {
        return officeService.findOffices();
    }

    @PostMapping
    public Office createOffice(@RequestBody @Valid Office office) {
        return officeService.createOffice(office);
    }

    @DeleteMapping(value = "/{officeId}")
    public void deleteOffice(@PathVariable("officeId") Long officeId) throws CustomEntityNotFoundException {
        officeService.deleteOffice(officeId);
    }
    @PutMapping (value = "/officeId")
    public void updateOffice(@PathVariable Long officeId, @RequestBody Office office) throws CustomEntityNotFoundException {
        officeService.updateoOffice(officeId,office);
    }
}
