package pl.tim.medicalclinic.doctor.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
import org.springframework.stereotype.Service;
import pl.tim.medicalclinic.doctor.domain.Doctor;
import pl.tim.medicalclinic.doctor.domain.dto.DoctorDto;
import pl.tim.medicalclinic.doctor.repository.DoctorRepository;

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

    public DoctorDto save(DoctorDto doctorDto) {
        Doctor doctor = convertToEntity(doctorDto);
        doctorRepository.save(doctor);
        return convertToDto(doctor);
    }

    public void delete(Long id) {
        doctorRepository.deleteById(id);
    }

    public DoctorDto updateDoctor(DoctorDto doctorDto, Long doctor_id) {
        Doctor baseDoctor = doctorRepository.getOne(doctor_id);
        baseDoctor.setPhone(doctorDto.getPhone());
        return convertToDto(doctorRepository.save(baseDoctor));
    }

    public List<DoctorDto> findDoctors() {
        return doctorRepository.findAll().stream().map(DoctorService.this::convertToDto).collect(Collectors.toList());
    }

    public DoctorDto findDoctor(Long id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Can not find Doctor with ID: " + id));
        return convertToDto(doctor);
    }

    private DoctorDto convertToDto(Doctor doctor) {
        return modelMapper.map(doctor, DoctorDto.class);
    }

    private Doctor convertToEntity(DoctorDto doctorDto) throws ParseException {
        return modelMapper.map(doctorDto, Doctor.class);
    }
}
