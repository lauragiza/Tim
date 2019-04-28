package pl.tim.medicalclinic.offices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/offices")
public class OfficeController {

    OfficeService officeService;

    @Autowired
    public OfficeController(OfficeService officeService) {
        this.officeService = officeService;
    }

    @GetMapping(value = "/{office_id}")
    public OfficeDto findOffice(@PathVariable("office_id") Long office_id) {
        return officeService.findOffice(office_id);
    }

    @GetMapping
    public List<OfficeDto> findOffices() {
        return officeService.findOffices();
    }

    @PostMapping
    public Office createOffice(@RequestBody @Valid OfficeDto officeDto) {
        return officeService.createOffice(officeDto);
    }

    @DeleteMapping
    public void deleteOffice(@PathVariable("office_id") Long office_id) {
        officeService.deleteOffice(office_id);
    }
}
