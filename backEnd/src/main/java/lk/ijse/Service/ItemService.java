package lk.ijse.Service;

import lk.ijse.dto.ItemDTO;

public interface ItemService {
    void saveItem(ItemDTO itemDTO);

    void updateItem(ItemDTO itemDTO);
}
