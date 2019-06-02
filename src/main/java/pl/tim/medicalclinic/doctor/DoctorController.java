package pl.tim.medicalclinic.doctor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.tim.medicalclinic.exception.CustomEntityNotFoundException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/doctors")
public class DoctorController {

    private final DoctorService doctorService;

    @Autowired
    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DoctorDto create(@RequestBody @Valid DoctorDto doctorDto) {
        return doctorService.save(doctorDto);
    }

    @DeleteMapping(value = "/{doctorId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("doctorId") Long doctorId) throws CustomEntityNotFoundException {
        doctorService.delete(doctorId);
    }

    @PutMapping(value = "/{doctorId}")
    public DoctorDto updateDoctor(@RequestBody @Valid DoctorDto doctorDto, @PathVariable Long doctorId) throws CustomEntityNotFoundException {
        return doctorService.updateDoctor(doctorDto, doctorId);
    }

    @GetMapping()
    public List<DoctorDto> findDoctors() {
        return doctorService.findDoctors();
    }

    @GetMapping(value = "/{doctorId}")
    public DoctorDto findDoctor(@PathVariable Long doctorId) throws CustomEntityNotFoundException {
        return doctorService.findDoctor(doctorId);
    }


}
