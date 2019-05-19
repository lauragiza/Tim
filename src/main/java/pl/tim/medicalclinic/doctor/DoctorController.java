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

    @DeleteMapping(value = "/{doctor_id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("doctor_id") Long doctor_id) throws CustomEntityNotFoundException {
        doctorService.delete(doctor_id);
    }

    @PutMapping(value = "/{doctor_id}/update")
    public DoctorDto updateDoctor(@RequestBody @Valid DoctorDto doctorDto, @PathVariable Long doctor_id) {
        return doctorService.updateDoctor(doctorDto, doctor_id);
    }

    @GetMapping()
    public List<DoctorDto> findDoctors() {
        return doctorService.findDoctors();
    }

    @GetMapping(value = "/{doctor_id}")
    public DoctorDto findDoctor(@PathVariable Long doctor_id) throws CustomEntityNotFoundException {
        return doctorService.findDoctor(doctor_id);
    }


}
