package pl.tim.medicalclinic.patient;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
import org.springframework.stereotype.Service;
import pl.tim.medicalclinic.exception.AlreadyExistsException;
import pl.tim.medicalclinic.exception.CustomEntityNotFoundException;
import pl.tim.medicalclinic.exception.CustomIncorrectEntityException;

import javax.persistence.EntityNotFoundException;
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

    PatientDto findPatient(Long id) throws CustomEntityNotFoundException {
        Patient patient = repository.findById(id).orElseThrow(() -> new CustomEntityNotFoundException(Patient.class, "id", id.toString()));
        return convertToDto(patient);
    }

    PatientDto addNewPatient(Patient patient) throws AlreadyExistsException, CustomIncorrectEntityException {
        if (repository.existsByMail(patient.getName()))
            throw new AlreadyExistsException(Patient.class, patient.getName());
        else if (repository.existsByPesel(patient.getPesel()))
            throw new AlreadyExistsException(Patient.class, patient.getPesel());
        else if(!PeselValidator.isValid(patient.pesel))
            throw new CustomIncorrectEntityException("PESEL");
        else
            return convertToDto(repository.save(patient));
    }

    void deletePatient(Long id) throws CustomEntityNotFoundException {
        Patient patient = repository.findById(id).orElseThrow(() -> new CustomEntityNotFoundException(Patient.class, "id", id.toString()));
        repository.delete(patient);
    }

    private PatientDto convertToDto(Patient patient) {
        return modelMapper.map(patient, PatientDto.class);
    }

    private Patient convertToEntity(PatientDto patientDto) throws ParseException {
        return modelMapper.map(patientDto, Patient.class);
    }

    void updatePatient(Long id, Patient patient) throws CustomEntityNotFoundException {
        Patient patientDb;
        try {
            patientDb = repository.getOne(id);
        } catch (EntityNotFoundException e) {
            throw new CustomEntityNotFoundException(Patient.class, "id", patient.id.toString());
        }
        if (!patient.email.equals(patientDb.email)) {
            patientDb.setEmail(patient.email);
        }
        if (!patient.street.equals(patientDb.street)) {
            patientDb.setStreet(patient.street);
        }
        if (!patient.town.equals(patientDb.town)) {
            patientDb.setTown(patient.town);
        }
        if (!patient.code.equals(patientDb.code)) {
            patientDb.setCode(patient.code);
        }

        repository.save(patientDb);
    }
}
