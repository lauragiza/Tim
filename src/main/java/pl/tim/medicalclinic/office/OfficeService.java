package pl.tim.medicalclinic.office;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
import org.springframework.stereotype.Service;
import pl.tim.medicalclinic.exception.CustomEntityNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OfficeService {
    private OfficeRepository officiesRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public OfficeService(OfficeRepository officiesRepository, ModelMapper modelMapper) {
        this.officiesRepository = officiesRepository;
        this.modelMapper = modelMapper;
    }

    List<OfficeDto> findOffices() {
        return officiesRepository.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
    }

    OfficeDto findOffice(Long id) throws CustomEntityNotFoundException {
        Office office = officiesRepository.findById(id).orElseThrow(() -> new CustomEntityNotFoundException(OfficeDto.class, "id", id.toString()));
            return convertToDto(office);
    }

    Office createOffice(OfficeDto officeDto) {
        Office office = convertToEntity(officeDto);
        officiesRepository.save(convertToEntity(officeDto));
        return office;
    }

    void deleteOffice(Long id) throws CustomEntityNotFoundException {
        Office office = officiesRepository.findById(id).orElseThrow(() -> new CustomEntityNotFoundException(OfficeDto.class, "id", id.toString()));
        officiesRepository.delete(office);
    }


    private OfficeDto convertToDto(Office office) {
        return modelMapper.map(office, OfficeDto.class);
    }

    private Office convertToEntity(OfficeDto officeDto) throws ParseException {
        return modelMapper.map(officeDto, Office.class);
    }

}


