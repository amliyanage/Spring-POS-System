package lk.ijse.util;

import lk.ijse.dto.CustomerDTO;
import lk.ijse.dto.ItemDTO;
import lk.ijse.dto.OrderDTO;
import lk.ijse.entity.CustomerEntity;
import lk.ijse.entity.ItemEntity;
import lk.ijse.entity.OrderEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

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

    public List<CustomerDTO> convertToDTOList(List<CustomerEntity> entities) {
        return entities.stream().map(entity -> modelMapper.map(entity, CustomerDTO.class)).collect(Collectors.toList());
    }

    public ItemEntity convertToEntity(ItemDTO dto) {
        return modelMapper.map(dto, ItemEntity.class);
    }

    public ItemDTO convertToDTO(ItemEntity entity) {
        return modelMapper.map(entity, ItemDTO.class);
    }

    public List<ItemDTO> convertItemToDTOList(List<ItemEntity> entities) {
        return entities.stream().map(entity -> modelMapper.map(entity, ItemDTO.class)).collect(Collectors.toList());
    }

    public OrderEntity convertToEntity(OrderDTO dto) {
        return modelMapper.map(dto, OrderEntity.class);
    }

    public OrderDTO convertToDTO(OrderEntity entity) {
        return modelMapper.map(entity, OrderDTO.class);
    }

    public List<OrderDTO> convertOrderToDTOList(List<OrderEntity> entities) {
        return entities.stream().map(entity -> modelMapper.map(entity, OrderDTO.class)).collect(Collectors.toList());
    }

}
