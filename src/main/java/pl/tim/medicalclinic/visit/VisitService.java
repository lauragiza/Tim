package pl.tim.medicalclinic.visit;

import org.modelmapper.ModelMapper;
import org.springframework.expression.ParseException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VisitService {

    private final VisitRepository visitRepository;
    private final ModelMapper modelMapper;

    public VisitService(VisitRepository visitRepository, ModelMapper modelMapper) {
        this.visitRepository = visitRepository;
        this.modelMapper = modelMapper;
    }

    VisitDto findVisit(Long id) {
        return convertToDto(visitRepository.getOne(id));
    }

    List<VisitDto> findVisits(){
        return visitRepository.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
    }

    VisitDto createVisit(Visit visit){
        Visit saved = visitRepository.save(visit);
        return convertToDto(saved);
    }

    void deleteVisit(Long id){
        Visit visit =visitRepository.getOne(id);
        visitRepository.delete(visit);
    }


    private VisitDto convertToDto(Visit visit) {
        return modelMapper.map(visit, VisitDto.class);
    }

    private Visit convertToEntity(VisitDto patientDto) throws ParseException {
        return modelMapper.map(patientDto, Visit.class);
    }

}
