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
import pl.tim.medicalclinic.office.OfficeRepository;
import pl.tim.medicalclinic.patient.Patient;
import pl.tim.medicalclinic.patient.PatientRepository;
import pl.tim.medicalclinic.vacation.Vacation;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VisitService {

    private final VisitRepository visitRepository;
    private final OfficeRepository officeRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final ModelMapper modelMapper;

    public VisitService(VisitRepository visitRepository, OfficeRepository officeRepository, PatientRepository patientRepository, DoctorRepository doctorRepository, ModelMapper modelMapper) {
        this.visitRepository = visitRepository;
        this.officeRepository = officeRepository;
        this.patientRepository = patientRepository;
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

    VisitDto createVisit(NewVisitDto newVisitDto) throws DoctorAbsentException, CustomEntityNotFoundException {
        Doctor doctor = doctorRepository.findById(newVisitDto.getDoctorId())
                .orElseThrow(() -> new CustomEntityNotFoundException(Doctor.class, "id", newVisitDto.getDoctorId().toString()));
        officeRepository.findById(newVisitDto.getOfficeId())
                .orElseThrow(() -> new CustomEntityNotFoundException(Office.class, "id", newVisitDto.getOfficeId().toString()));
        patientRepository.findById(newVisitDto.getPatientId())
                .orElseThrow(() -> new CustomEntityNotFoundException(Patient.class, "id", newVisitDto.getPatientId().toString()));
        List<Vacation> doctorAbsentDays = doctor.getVacations();
        for (Vacation vacation : doctorAbsentDays) {
            if (vacation.getVacationDay().equals(newVisitDto.getDate().toLocalDate())) {
                throw new DoctorAbsentException(Doctor.class, vacation.getVacationDay());
            }
        }
        Visit saved = visitRepository.save(convertToEntity(newVisitDto));
        return convertToDto(saved);
    }

    void deleteVisit(Long id) throws CustomEntityNotFoundException {
        Visit visit = visitRepository.findById(id).orElseThrow(() -> new CustomEntityNotFoundException(Visit.class, "id", id.toString()));
        visitRepository.delete(visit);
    }


    private VisitDto convertToDto(Visit visit) {
        return modelMapper.map(visit, VisitDto.class);
    }

    private Visit convertToEntity(NewVisitDto newVisitDto) throws ParseException {
        return modelMapper.map(newVisitDto, Visit.class);
    }

}
