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
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;
    final private ModelMapper modelMapper;

    @Autowired // <- for better understand, from new wersion it is deprecated
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
        // set field which can be updated eg.
        baseDoctor.setPhone(doctorDto.getPhone());

        //etc..
        return convertToDto(doctorRepository.save(baseDoctor));
    }

    public List<DoctorDto> findDoctors() {
        return doctorRepository.findAll().stream().map(new Function<Doctor, DoctorDto>() {
            @Override
            public DoctorDto apply(Doctor doctor) {
                return DoctorService.this.convertToDto(doctor);
            }
        }).collect(Collectors.toList());
    }

    public DoctorDto findDoctor(Long id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(new Supplier<EntityNotFoundException>() {
                    @Override
                    public EntityNotFoundException get() {
                        return new EntityNotFoundException("Can not find Doctor with ID: " + id);
                    }
                }); // or throw any custom runtime exception
        return convertToDto(doctor);
    }

    private DoctorDto convertToDto(Doctor doctor) {
        return modelMapper.map(doctor, DoctorDto.class);
    }

    private Doctor convertToEntity(DoctorDto doctorDto) throws ParseException {
        return modelMapper.map(doctorDto, Doctor.class);
    }
}
