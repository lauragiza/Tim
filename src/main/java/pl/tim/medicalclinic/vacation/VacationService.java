package pl.tim.medicalclinic.vacation;

import org.modelmapper.ModelMapper;
import org.springframework.expression.ParseException;
import org.springframework.stereotype.Service;
import pl.tim.medicalclinic.exception.CustomEntityNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VacationService {

    private final VacationRepository vacationRepository;
    private final ModelMapper modelMapper;

    public VacationService(VacationRepository vacationRepository, ModelMapper modelMapper) {
        this.vacationRepository = vacationRepository;
        this.modelMapper = modelMapper;
    }


    List<VacationListDto> findVacations() {
        return vacationRepository.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
    }

    List<VacationListDto> findVacation(Long doctorId) {
        return vacationRepository.findAll().stream().filter(x -> x.getDoctor().getId().equals(doctorId))
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    VacationListDto addVacation(NewVacationDto newVacationDto) {
        return convertToDto(vacationRepository.save(convertToEntity(newVacationDto)));
    }

    void deleteVacation(Long id) throws CustomEntityNotFoundException {
        Vacation vacation = vacationRepository.findById(id).orElseThrow(() -> new CustomEntityNotFoundException(Vacation.class, "id", id.toString()));
        vacationRepository.delete(vacation);
    }

    private VacationListDto convertToDto(Vacation vacation) {
        return modelMapper.map(vacation, VacationListDto.class);
    }

    private Vacation convertToEntity(NewVacationDto newVacationDto) throws ParseException {
        return modelMapper.map(newVacationDto, Vacation.class);
    }


}
