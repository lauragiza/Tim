package pl.tim.medicalclinic.doctor;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
import org.springframework.stereotype.Service;
import pl.tim.medicalclinic.exception.CustomEntityNotFoundException;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;
    final private ModelMapper modelMapper;

    @Autowired
    public DoctorService(DoctorRepository doctorRepository, ModelMapper modelMapper) {
        this.doctorRepository = doctorRepository;
        this.modelMapper = modelMapper;
    }

    DoctorDto save(DoctorDto doctorDto) {
        Doctor doctor = convertToEntity(doctorDto);
        doctorRepository.save(doctor);
        return convertToDto(doctor);
    }

    void delete(Long id) throws CustomEntityNotFoundException {
        Doctor doctor = doctorRepository.findById(id).orElseThrow(() -> new CustomEntityNotFoundException(Doctor.class, "id", id.toString()));
        doctorRepository.delete(doctor);
    }

    DoctorDto updateDoctor(DoctorDto doctorDto, Long doctor_id) throws CustomEntityNotFoundException {
        Doctor baseDoctor;
        try {
            baseDoctor = doctorRepository.getOne(doctor_id);
        } catch (EntityNotFoundException e) {
            throw new CustomEntityNotFoundException(Doctor.class, "id", doctor_id.toString());
        }
        if (!baseDoctor.getPhone().equals(doctorDto.getPhone()))
            baseDoctor.setPhone(doctorDto.getPhone());
        if (!baseDoctor.getStartWorkingTime().equals(doctorDto.getStartWorkingTime()))
            baseDoctor.setStartWorkingTime(doctorDto.getStartWorkingTime());
        if (!baseDoctor.getEndWorkingTime().equals(doctorDto.getEndWorkingTime()))
            baseDoctor.setEndWorkingTime(doctorDto.getEndWorkingTime());

        return convertToDto(doctorRepository.save(baseDoctor));
    }

    List<DoctorDto> findDoctors() {
        return doctorRepository.findAll().stream().map(DoctorService.this::convertToDto).collect(Collectors.toList());
    }

    DoctorDto findDoctor(Long id) throws CustomEntityNotFoundException {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new CustomEntityNotFoundException(Doctor.class, "id", id.toString()));
        return convertToDto(doctor);
    }

    private DoctorDto convertToDto(Doctor doctor) {
        return modelMapper.map(doctor, DoctorDto.class);
    }

    private Doctor convertToEntity(DoctorDto doctorDto) throws ParseException {
        return modelMapper.map(doctorDto, Doctor.class);
    }
}
