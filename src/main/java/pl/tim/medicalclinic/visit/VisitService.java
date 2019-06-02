package pl.tim.medicalclinic.visit;

import com.google.common.base.Strings;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.expression.ParseException;
import org.springframework.stereotype.Service;
import pl.tim.medicalclinic.doctor.Doctor;
import pl.tim.medicalclinic.doctor.DoctorRepository;
import pl.tim.medicalclinic.exception.CustomEntityNotFoundException;
import pl.tim.medicalclinic.exception.DoctorAbsentException;
import pl.tim.medicalclinic.office.Office;
import pl.tim.medicalclinic.patient.Patient;
import pl.tim.medicalclinic.vacation.Vacation;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VisitService {

    private final VisitRepository visitRepository;
    private final DoctorRepository doctorRepository;
    private final ModelMapper modelMapper;

    public VisitService(VisitRepository visitRepository, DoctorRepository doctorRepository, ModelMapper modelMapper) {
        this.visitRepository = visitRepository;
        this.doctorRepository = doctorRepository;
        this.modelMapper = modelMapper;
    }

    VisitDto findVisit(Long id) throws CustomEntityNotFoundException {
        Visit visit = visitRepository.findById(id).orElseThrow(() -> new CustomEntityNotFoundException(Visit.class, "id", id.toString()));
        return convertToDto(visit);
    }

    private Specification<Visit> completeSpecification(VisitSearchDto dto) {
        boolean patientIsPresent = !Strings.isNullOrEmpty(dto.getPatientId());
        boolean officeIsPresent = !Strings.isNullOrEmpty(dto.getOfficeId());
        boolean doctorIsPresent = !Strings.isNullOrEmpty(dto.getDoctorId());

        Specification<Visit> specification = null;
        if (patientIsPresent)
            specification = VisitSpecifications.hasPatient(Patient.builder().id(Long.valueOf(dto.getPatientId())).build());
        if (officeIsPresent)
            specification = Specifications.where(specification).and(VisitSpecifications.hasOffice(Office.builder().id(Long.valueOf(dto.getOfficeId())).build()));
        if (doctorIsPresent)
            specification = Specification.where(specification).and(VisitSpecifications.hasDoctor(Doctor.builder().id(Long.valueOf(dto.getDoctorId())).build()));

        return specification;
    }

    List<VisitDto> findVisits(VisitSearchDto searchDto) {
        List<Visit> visits = visitRepository.findAll(completeSpecification(searchDto));
        return visits.stream().map(x -> convertToDto(x)).collect(Collectors.toList());

    }

    Visit createVisit(Visit visit) throws DoctorAbsentException, CustomEntityNotFoundException {
        Doctor doctor = doctorRepository.findById(visit.getDoctor().getId())
                .orElseThrow(() -> new CustomEntityNotFoundException(Doctor.class, "id", visit.getDoctor().getId().toString()));
        List<Vacation> doctorAbsentDays = doctor.getVacations();
        for (Vacation vacation : doctorAbsentDays) {
            if (vacation.getVacationDay().equals(visit.getDate().toLocalDate())) {
                throw new DoctorAbsentException(Doctor.class, vacation.getVacationDay());
            }
        }
        Visit saved = visitRepository.save(visit);
        return saved;
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
