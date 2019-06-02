package pl.tim.medicalclinic.office;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
import org.springframework.stereotype.Service;
import pl.tim.medicalclinic.exception.CustomEntityNotFoundException;

import javax.persistence.EntityNotFoundException;
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

    Office createOffice(Office office) {
        return officiesRepository.save(office);
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

    void updateoOffice(Long id, Office office) throws CustomEntityNotFoundException {
        Office officeDb;
        try {
            officeDb = officiesRepository.getOne(id);
        } catch (EntityNotFoundException e) {
            throw new CustomEntityNotFoundException(Office.class, "id", office.id.toString());
        }
        if (!office.name.equals(officeDb.name)) {
            officeDb.setName(office.name);
        }
    }
}


