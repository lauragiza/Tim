package pl.tim.medicalclinic.visit;

import org.modelmapper.ModelMapper;
import org.springframework.expression.ParseException;
import org.springframework.stereotype.Service;
import pl.tim.medicalclinic.doctor.Doctor;
import pl.tim.medicalclinic.doctor.DoctorRepository;
import pl.tim.medicalclinic.exception.CustomEntityNotFoundException;
import pl.tim.medicalclinic.exception.DoctorAbsentException;
import pl.tim.medicalclinic.vacation.Vacation;
import pl.tim.medicalclinic.vacation.VacationRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VisitService {

    private final VacationRepository vacationRepository;
    private final VisitRepository visitRepository;
    private final DoctorRepository doctorRepository;
    private final ModelMapper modelMapper;

    public VisitService(VacationRepository vacationRepository, VisitRepository visitRepository, DoctorRepository doctorRepository, ModelMapper modelMapper) {
        this.vacationRepository = vacationRepository;
        this.visitRepository = visitRepository;
        this.doctorRepository = doctorRepository;
        this.modelMapper = modelMapper;
    }

    VisitDto findVisit(Long id) throws CustomEntityNotFoundException {
        Visit visit = visitRepository.findById(id).orElseThrow(() -> new CustomEntityNotFoundException(Visit.class, "id", id.toString()));
        return convertToDto(visit);
    }

    List<VisitDto> findVisits() {
        return visitRepository.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
    }

    VisitDto createVisit(VisitDto visitDto) throws DoctorAbsentException, CustomEntityNotFoundException {
        Doctor doctor = doctorRepository.findById(visitDto.getDoctorId())
                .orElseThrow(() -> new CustomEntityNotFoundException(Doctor.class, "id", visitDto.getDoctorId().toString()));
        List<Vacation> doctorAbsentDays = doctor.getVacations();
        for (Vacation vacation : doctorAbsentDays){
            if(vacation.getVacationDay().equals(visitDto.getDate().toLocalDate())){
                throw new DoctorAbsentException(Doctor.class , vacation.getVacationDay());
            }
        }

        Visit visit = convertToEntity(visitDto);
            Visit saved = visitRepository.save(visit);
        return convertToDto(saved);
    }

    void deleteVisit(Long id) throws CustomEntityNotFoundException {
        Visit visit = visitRepository.findById(id).orElseThrow(() -> new CustomEntityNotFoundException(Visit.class, "id", id.toString()));
        visitRepository.delete(visit);
    }


    private VisitDto convertToDto(Visit visit) {
        return modelMapper.map(visit, VisitDto.class);
    }

    private Visit convertToEntity(VisitDto patientDto) throws ParseException {
        return modelMapper.map(patientDto, Visit.class);
    }

}
