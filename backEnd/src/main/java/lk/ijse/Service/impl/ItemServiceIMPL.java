package lk.ijse.Service.impl;

import lk.ijse.Repository.ItemRepository;
import lk.ijse.Service.ItemService;
import lk.ijse.customObj.ItemErrorResponse;
import lk.ijse.customObj.response.ItemResponse;
import lk.ijse.dto.ItemDTO;
import lk.ijse.entity.ItemEntity;
import lk.ijse.exception.DataPersistFailedException;
import lk.ijse.exception.ItemNotFountException;
import lk.ijse.util.Mapping;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemServiceIMPL implements ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private Mapping mapping;

    @Override
    public void saveItem(ItemDTO itemDTO) {
        if (!itemRepository.existsByItemCode(itemDTO.getItemCode())){
            ItemEntity save = mapping.convertToEntity(itemDTO);
            if (save != null && save.getItemCode() != null){
                itemRepository.save(save);
            }else {
                throw new DataPersistFailedException("Item save failed..!");
            }
        }else {
            throw new DataPersistFailedException("Item already exist..!");
        }
    }

    @Override
    public void updateItem(ItemDTO itemDTO) {
        Optional<ItemEntity> itemEntity = itemRepository.findById(itemDTO.getItemCode());
        if (itemEntity.isPresent()) {
            itemEntity.get().setItemName(itemDTO.getItemName());
            itemEntity.get().setItemPrice(itemDTO.getItemPrice());
            itemEntity.get().setItemQty(itemDTO.getItemQty());
        } else {
            throw new ItemNotFountException("item update failed..!");
        }
    }

    @Override
    public ItemResponse getItem(String itemCode) {
        Optional<ItemEntity> itemEntity = itemRepository.findById(itemCode);
        return (itemEntity.isPresent())
                ? mapping.convertToDTO(itemEntity.get())
                : new ItemErrorResponse(0, "Item not found");
    }

    @Override
    public void deleteItem(String itemCode) {
        if (itemRepository.existsById(itemCode)) {
            itemRepository.deleteById(itemCode);
        } else {
            throw new ItemNotFountException("Item not found..!");
        }
    }

    @Override
    public List<ItemDTO> getAllItems() {
        return mapping.convertItemToDTOList(itemRepository.findAll());
    }

}
