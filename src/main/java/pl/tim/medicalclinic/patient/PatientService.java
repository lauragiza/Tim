package pl.tim.medicalclinic.patient;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service

public class PatientService {
    private PatientRepository repository;
    private final ModelMapper modelMapper;

    @Autowired
    public PatientService(PatientRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    List<PatientDto> findPatients() {
        return repository.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
    }

    PatientDto findPatient(Long id) {
        return convertToDto(repository.getOne(id));
    }

    Patient addNewPatient(PatientDto patientDto) {
        return repository.save(convertToEntity(patientDto));
    }

    private PatientDto convertToDto(Patient patient) {
        return modelMapper.map(patient, PatientDto.class);
    }

    private Patient convertToEntity(PatientDto patientDto) throws ParseException {
        return modelMapper.map(patientDto, Patient.class);
    }
}
