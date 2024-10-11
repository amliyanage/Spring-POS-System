package lk.ijse.Service.impl;

import lk.ijse.Repository.ItemRepository;
import lk.ijse.Service.ItemService;
import lk.ijse.dto.ItemDTO;
import lk.ijse.entity.CustomerEntity;
import lk.ijse.entity.ItemEntity;
import lk.ijse.exception.DataPersistFailedException;
import lk.ijse.util.Mapping;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

}
