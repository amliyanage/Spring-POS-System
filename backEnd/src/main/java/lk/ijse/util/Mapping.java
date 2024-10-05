package lk.ijse.util;

import lk.ijse.dto.CustomerDTO;
import lk.ijse.entity.CustomerEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Mapping {

    @Autowired
    private ModelMapper modelMapper;

    public CustomerEntity convertToEntity(CustomerDTO dto) {
        return modelMapper.map(dto, CustomerEntity.class);
    }

    public CustomerDTO convertToDTO(CustomerEntity entity) {
        return modelMapper.map(entity, CustomerDTO.class);
    }

}
