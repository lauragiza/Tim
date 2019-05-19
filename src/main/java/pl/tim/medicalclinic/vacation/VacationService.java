package pl.tim.medicalclinic.vacation;

import org.modelmapper.ModelMapper;
import org.springframework.expression.ParseException;
import org.springframework.stereotype.Service;

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


    List<VacationDto> findVacations() {
        return vacationRepository.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
    }

    VacationDto findVacation(Long id) {
        return convertToDto(vacationRepository.getOne(id));
    }

    VacationDto addVacation(VacationDto vacationDto) {
        return convertToDto(vacationRepository.save(convertToEntity(vacationDto)));
    }

    void deleteVacation(Long id) {
        Vacation vacationToRemove = vacationRepository.findById(id).get();
        vacationRepository.delete(vacationToRemove);
    }

    private VacationDto convertToDto(Vacation vacation) {
        return modelMapper.map(vacation, VacationDto.class);
    }

    private Vacation convertToEntity(VacationDto vacationDto) throws ParseException {
        return modelMapper.map(vacationDto, Vacation.class);
    }


}
